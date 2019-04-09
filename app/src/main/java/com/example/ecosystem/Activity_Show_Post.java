package com.example.ecosystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Activity_Show_Post extends AppCompatActivity {

    TextView tv_titulo;
    ImageView iv_foto;
    EditText et_descripcion;
    EditText et_procedimiento;

    private String webservice_url = "http://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=getget&id_cliente=";
    private String images_url = "http://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=getget&id_cliente=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        setContentView(R.layout.activity__show__post);
        tv_titulo = findViewById(R.id.tv_titulo);
        iv_foto = findViewById(R.id.iv_foto);
        et_descripcion = findViewById(R.id.et_descripcion);
        et_procedimiento = findViewById(R.id.et_procedimiento);
        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_post enviado
        String id_post = intent.getStringExtra(MainActivity.ID_POST);
        //Se cocnatena la url con el id_post para obtener los datos
        webservice_url+=id_post;
        webServiceRest(webservice_url);
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_post;
        String titulo;
        String descripcion;
        String procedimiento;
        String link_video;
        String imagen;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                id_post = jsonObject.getString("id_post");
                titulo = jsonObject.getString("titulo");
                descripcion = jsonObject.getString("descripcion");
                procedimiento = jsonObject.getString("procedimiento");
                link_video = jsonObject.getString("link_video");
                imagen = jsonObject.getString("imagen");

                //Se muestran los datos del cliente en su respectivo EditText
                tv_titulo.setText(titulo);
                et_descripcion.setText(descripcion);
                et_procedimiento.setText(procedimiento);
                URL newurl = new URL(images_url+imagen);
                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                iv_foto.setImageBitmap(mIcon_val);

            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            } catch (MalformedURLException e) {
                Log.e("Error 103",e.getMessage());
            } catch (IOException e) {
                Log.e("Error 104",e.getMessage());
            }
        }
    }

}

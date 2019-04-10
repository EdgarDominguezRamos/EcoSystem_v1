package com.example.ecosystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_New_Post extends AppCompatActivity {

    EditText et_titulo;
    EditText et_link;
    EditText iv_foto;
    EditText et_procedimiento;
    EditText et_descripcion;

    private String url = "http://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new__post);

        et_titulo = findViewById(R.id.et_titulo);
        et_link = findViewById(R.id.et_link);
        //iv_foto = findViewById(R.id.iv_foto);
        et_procedimiento = findViewById(R.id.et_procedimiento);
        et_descripcion = findViewById(R.id.et_descripcion);
    }
    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("procedimiento="+et_procedimiento.getText());
        sb.append("&");
        sb.append("titulo="+et_titulo.getText());
        sb.append("&");
        sb.append("link_video="+et_link.getText());
        sb.append("&");
        sb.append("descripcion="+et_descripcion.getText());
        sb.append("&");
        sb.append("id_comentario="+et_procedimiento.getText());
        sb.append("&");
        webServicePut(sb.toString());
        Log.e("URL",sb.toString());
    }
    private void webServicePut(String requestURL){
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
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }
}

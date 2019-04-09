package com.example.ecosystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Show_Post extends AppCompatActivity {

    TextView tv_titulo;
    ImageView iv_foto;
    EditText et_descripcion;
    EditText et_procedimiento;

    private String webservice_url = "http://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=getget&id_cliente=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__show__post);
    }
}

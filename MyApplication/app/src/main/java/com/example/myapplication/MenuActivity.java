package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Clases.Config;

public class MenuActivity extends AppCompatActivity {

    TextView txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtUser=findViewById(R.id.txtUser);
        txtUser.setText(Config.usuario);
    }
}
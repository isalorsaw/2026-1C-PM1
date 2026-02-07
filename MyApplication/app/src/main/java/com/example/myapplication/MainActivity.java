package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Clases.ApiService;
import com.example.myapplication.Clases.Config;
import com.example.myapplication.Clases.Dialog;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText txtUser,txtClave;
    private TextView txtCrear;
    private Button btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser=findViewById(R.id.etUsuario);
        txtClave=findViewById(R.id.etClave);
        txtCrear=findViewById(R.id.tvOlvidoClave);

        btnEntrar=findViewById(R.id.btnIniciarSesion);

        btnEntrar.setOnClickListener(view -> validarCampos());
        txtCrear.setOnClickListener(view -> llamarCrearUser());
    }
    public void validarCampos()
    {
        if(txtUser.getText().toString().trim().equals(""))
        {
            //Dialog.msgbox(MainActivity.this,"Inválido","Ingrese el Usuario",R.drawable.error);
            Dialog.toast(MainActivity.this,"Favor Ingrese el Usuario");
            txtUser.requestFocus();
        }
        else if(txtClave.getText().toString().trim().equals(""))
        {
            //Dialog.msgbox(MainActivity.this,"Inválido","Ingrese la Clave",R.drawable.error);
            Dialog.toast(MainActivity.this,"Favor Ingrese la Clave");
            txtClave.requestFocus();
        }
        else
        {
            String usuario=txtUser.getText().toString();
            String clave=txtClave.getText().toString();
            confirmarCredenciales(usuario,clave);
            //Dialog.msgbox(MainActivity.this,"Informacion","Entramos Bien",R.drawable.ok);
        }
    }
    private void confirmarCredenciales(String usuario, String clave)
    {
        ApiService.login(usuario, clave, new ApiService.LoginCallback() {
            @Override
            public void onSuccess(JSONObject userdata) {
                try
                {
                    boolean success=userdata.getBoolean("success");
                    if(success)
                    {
                        String username = userdata.getJSONObject("data").getString("user_nombre");
                        //txtestado.setText("✅ Bienvenido, " + username + "!");
                        //Dialog.msgbox(MainActivity.this,"Exito","Bienvenido "+username,R.drawable.ok);
                        Config.usuario=usuario;
                        //navegarPral();
                        Log.e("LOGIN EXITOSO",usuario);
                        Dialog.toast(MainActivity.this,"Exito");
                        llamarPral();
                    }
                    else
                    {
                        String errorMsg = userdata.optString("error", "Credenciales inválidas");
                        //txtestado.setText("❌ " + errorMsg);
                        Dialog.msgbox(MainActivity.this, "Error", errorMsg, R.drawable.error);
                        //btnentrar.setEnabled(true);
                        Dialog.toast(MainActivity.this,"Error "+errorMsg);
                    }
                }catch (Exception e)
                {
                    Log.e("LOGIN_PARSE", e.getMessage());
                    //txtestado.setText("⚠️ Error procesando respuesta");
                    //Dialog.msgbox(MainActivity.this, "Error", "Error en la respuesta", R.drawable.error);
                    //btnentrar.setEnabled(true);
                    Dialog.toast(MainActivity.this,"Error "+e.getMessage());
                }
            }
            public void onError(String errorMessage) {
                //btnentrar.setEnabled(true);
                //txtestado.setText("❌ " + errorMessage);
                //Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                //Dialog.msgbox(MainActivity.this, "Error", errorMessage, R.drawable.error);
                Dialog.toast(MainActivity.this,"Error "+errorMessage);
            }
        });
    }
    private void llamarPral()
    {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        finish(); // Elimina el splash del stack para que no se regrese a él
    }
    private void llamarCrearUser()
    {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        startActivity(intent);
        finish(); // Elimina el splash del stack para que no se regrese a él
    }
}
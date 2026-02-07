package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Clases.ApiService;
import com.example.myapplication.Clases.Dialog;

import org.json.JSONObject;

public class UserActivity extends AppCompatActivity
{
    private EditText txtUser,txtPass,txtCorreo,txtNombre,txtApellido;
    private Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtUser=findViewById(R.id.etUsuario);
        txtPass=findViewById(R.id.etClave);
        txtApellido=findViewById(R.id.etApellido);
        txtNombre=findViewById(R.id.etNombre);
        txtCorreo=findViewById(R.id.etEmail);

        btnRegistrar=findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(view -> validarCampos());

    }
    private void validarCampos()
    {
        if(txtUser.getText().toString().trim().equals("")||txtPass.getText().toString().trim().equals("")||
                txtApellido.getText().toString().trim().equals("")||txtNombre.getText().toString().trim().equals("")||
                txtCorreo.getText().toString().trim().equals(""))
        {
            Dialog.toast(UserActivity.this,"Debe de INgresar todos los Campos");
        }
        else
        {
            crearUser();
        }
    }
    private void crearUser()
    {
        String usuario = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        String apellido = txtApellido.getText().toString();
        String nombre = txtNombre.getText().toString();
        String correo = txtCorreo.getText().toString();
        String dni = "";//etDni.getText().toString().trim();
        boolean estado=true;

        ApiService.guardarUsuario(dni, nombre, apellido, correo, usuario, pass, estado, new ApiService.ApiCallback()
                {
                    @Override
                    public void onSuccess(String response)
                    {
                        //progressDialog.dismiss();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getBoolean("success"))
                            {
                                int idNuevo = res.getInt("id");
                                //Toast.makeText(FormUsuarioActivity.this,
                                //    "✅ Usuario creado exitosamente\nID: " + idNuevo,
                                  //      Toast.LENGTH_LONG).show();
                                //Dialog.msgbox(UserActivity.this,"Exito","Usuario Guardado Satisfactoriamente",R.drawable.ok);
                                Dialog.toast(UserActivity.this,"Exitoso");
                                setResult(RESULT_OK);
                                navegarALogin();
                                //finish();
                            }
                            else
                            {
                                String error = res.optString("error", "Error desconocido");
                                //Toast.makeText(FormUsuarioActivity.this,
                                //        "❌ " + error,
                                //        Toast.LENGTH_LONG).show();
                                //Dialog.msgbox(UserActivity.this,"Error","Error al Ingresar Usuario "+error,R.drawable.error);
                                Dialog.toast(UserActivity.this,"Error "+error);
                            }
                        }
                        catch (Exception e)
                        {
                            //Toast.makeText(FormUsuarioActivity.this, "Error procesando respuesta", Toast.LENGTH_SHORT).show();
                            //Dialog.msgbox(UserActivity.this,"Error","Error Procesando respuesta ",R.drawable.error);
                            Dialog.toast(UserActivity.this,"Error "+e.getMessage());
                        }
                    }
                    @Override
                    public void onError(String error)
                    {
                        //progressDialog.dismiss();
                        Toast.makeText(UserActivity.this,
                                "❌ Error: " + error,
                                Toast.LENGTH_LONG).show();
                        //Dialog.msgbox(UserActivity.this,"Error","Error "+error,R.drawable.error);
                    }
                }
        );
        //Dialog.msgbox(UserActivity.this,"Información","Usuario "+nombreu+" creado Satisfactoriamente",R.drawable.ok);
    }
    private void navegarALogin() {
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Cierra SplashActivity
    }
}
package com.example.a202601pm1.Clases;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils
{
    public static String usuario="";
    public static int iduser=0;

    public static String error="";

    //static ArrayList<Acceso> lstaccesos;
    /*public static void actualizarAccesos()
    {
        lstaccesos=new ArrayList<Acceso>();

        try
        {
            Connection con= BDAdmin.conn.getConexion();
            Statement s1=con.createStatement();
            ResultSet rs=s1.executeQuery("SELECT * FROM tbl_acceso WHERE user_id="+iduser+" AND acceso_estado=1");
            while(rs.next())
            {
                lstaccesos.add(new Acceso(rs));
            }
            rs.close();
            con.close();
        }catch(Exception exp)
        {
            //Insertar en un Archivo los Errores.
            Log.e("Error", exp.getMessage());
        }
    }
    public static String printAccesos()
    {
        actualizarAccesos();
        String salida="";
        for(int i=0;i<lstaccesos.size();i++)
        {
            Log.d("Acceso",lstaccesos.get(i).toString());
            salida+=lstaccesos.get(i).toString()+"\n";
        }
        return salida;
    }
    public static Acceso retAcceso(String modulo_codigo)
    {
        actualizarAccesos();
        for(int i=0;i<lstaccesos.size();i++)
        {
            Acceso ac=lstaccesos.get(i);
            if(ac.equals(modulo_codigo))return ac;
        }
        return null;
    }*/
    public static String preparar(String inputString)
    {
        final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%","\\;"};
        for (int i = 0 ; i < metaCharacters.length ; i++)
        {
            if(inputString.contains(metaCharacters[i]))
                inputString = inputString.replace(metaCharacters[i],"\\"+metaCharacters[i]);
        }
        return inputString;
    }
    public static void mostrar(Context c, String msg)
    {
        Toast.makeText(c,msg,Toast.LENGTH_LONG).show();
    }
    public static int convertInt(String s)
    {
        return Integer.parseInt(s);
    }
    /*public static void getUserData()
    {
        try
        {
            Connection con=new Conexion().getConexion();
            Statement s1=con.createStatement();
            ResultSet rs=s1.executeQuery("select * from tbl_user where user_usuario='"+usuario+"'");
            while(rs.next())
            {
                iduser=rs.getInt(1);
            }
            rs.close();
            con.close();
        }catch(Exception exp)
        {
            //Insertar en un Archivo los Errores.
            Log.e("Error", exp.getMessage());
        }
    }*/
    public static String getLocalTime()
    {
        Calendar calendar = Calendar.getInstance(); // Automatically uses device's time zone
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
    public static int getZona()
    {
        TimeZone tz = TimeZone.getDefault();
        String timeZoneName = tz.getID(); // Ej: "America/Argentina/Buenos_Aires"
        int offset = tz.getOffset(System.currentTimeMillis()) / 1000 / 3600; // Ej: -3
        return offset*-1;
    }
    /*public static String hoy()
    {
        return BDAdmin.getData(1,"select now()");
    }*/
}
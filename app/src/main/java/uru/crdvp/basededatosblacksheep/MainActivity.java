package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    EditText tvUsuarioLog,tvContraseñaLog,tvNombreLog,tvFechaNacimiento,tvPaisLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsuarioLog = (EditText) findViewById(R.id.tvUsuarioLog);
        tvContraseñaLog = (EditText) findViewById(R.id.tvContraseñaLog);
        tvNombreLog = (EditText) findViewById(R.id.tvNombreLog);
        //tvFechaNacimiento = (EditText) findViewById(R.id.tvFechaNacimiento);
        tvPaisLog = (EditText) findViewById(R.id.tvPaisLog);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);

        // Defino botones
        Button IngresarUsuario  = (Button) findViewById(R.id.BtnIngresoUsu);
        Button VerificarUsuario = (Button) findViewById(R.id.BtnVerificoUsu);
        Button IngresarPerfiles = (Button) findViewById(R.id.BtnPerfiles);
        Button AgregarPerfiles = (Button) findViewById(R.id.BtnPerfilesAdd);
        Button verLogin = (Button) findViewById(R.id.btnLogin);
        Button verPerfiles = (Button) findViewById(R.id.BtnVerPerfiles);

        verPerfiles.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent perfiles = new Intent(MainActivity.this, VerPerfiles.class);
                startActivity(perfiles);
            }
        });

        IngresarUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ingresoUsuario = new Intent(MainActivity.this, IngresoUsuario.class);
                startActivity(ingresoUsuario);
            }
        });

        verLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ingresoLogin = new Intent(MainActivity.this, Login.class);
                startActivity(ingresoLogin);
            }
        });

        VerificarUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent consultarUsuario = new Intent(MainActivity.this, ConsultaUsuario.class);
                startActivity(consultarUsuario);
            }
        });

        IngresarPerfiles.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent perfiles = new Intent(MainActivity.this, Perfiles.class);
                startActivity(perfiles);
            }
        });

        AgregarPerfiles.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent agregarPerfiles = new Intent(MainActivity.this, RegistroPerfiles.class);
                startActivity(agregarPerfiles);
            }
        });
    }

    public void onClick(View view) {

    }

    /*
    private void registrarUsuarioSql() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Insert into usuario (idUsuario,contraseña,nombre,fechaNacimiento,pais) values ('UsuarioADM','1234','Administrador','01/01/2021','Uruguay')

        String insert = "INSERT INTO " + Utilidades.TABLA_USUARIO + " ("
                +Utilidades.CAMPO_IDUSUARIO + ","
                +Utilidades.CAMPO_CONTRASEÑA + ", "
                +Utilidades.CAMPO_NOMBRE + ", "
                +Utilidades.CAMPO_FECHANACIMIENTO + ", "
                +Utilidades.CAMPO_PAIS + ")"
                +" VALUES ('" +tvUsuarioLog.getText().toString()+"', '" + tvContraseñaLog.getText().toString()+"', '"+ tvNombreLog.getText().toString()+"', "+null+", '"+ tvPaisLog.getText().toString()+"')";

        db.close();
    }

    public void onClick(View view){

        //Toast.makeText(getApplicationContext(),"Funciona el boton",Toast.LENGTH_SHORT).show();
        //registrarUsuario();
    }

    private void registrarUsuario() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_IDUSUARIO,tvUsuarioLog.getText().toString());
        values.put(Utilidades.CAMPO_CONTRASEÑA,tvContraseñaLog.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,tvNombreLog.getText().toString());
        //values.put(Utilidades.CAMPO_FECHANACIMIENTO,tvFechaNacimiento.getText().toString());
        values.put(Utilidades.CAMPO_PAIS,tvPaisLog.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_IDUSUARIO,values);

        Toast.makeText(getApplicationContext(),"Id Registro" + idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }
     */
}
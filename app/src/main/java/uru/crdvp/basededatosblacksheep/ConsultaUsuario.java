package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class ConsultaUsuario extends AppCompatActivity {

    EditText tvUsuarioLog,tvContraseñaLog,tvNombreLog;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_usuario);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_BlackSheep",null,1);

        tvUsuarioLog = (EditText) findViewById(R.id.tvUsuarioLog);
        tvContraseñaLog = (EditText) findViewById(R.id.tvContraseñaLog);
        tvNombreLog = (EditText) findViewById(R.id.tvNombreLog);

        Button BtnConsultar  = (Button) findViewById(R.id.BtnConsultar);
        Button BtnActualizar = (Button) findViewById(R.id.BtnActualizar);
        Button BtnEliminar   = (Button) findViewById(R.id.BtnEliminar);

        BtnConsultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Funciona el boton",Toast.LENGTH_SHORT).show();
                consultar();
            }
        });

        BtnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Funciona el boton",Toast.LENGTH_SHORT).show();
            }
        });

        BtnEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Funciona el boton",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.BtnConsultar:
                consultar();
                break;
            case R.id.BtnActualizar:
                break;
            case R.id.BtnEliminar:
                break;
        }
    }

    private void consultar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] parametros = {tvUsuarioLog.getText().toString()}; //--> Para consulta con mas parametros agrego la coma y el resto de los parametros
        String [] campos = {Utilidades.CAMPO_CONTRASEÑA,Utilidades.CAMPO_NOMBRE};

        try {
            Toast.makeText(getApplicationContext(),"Ingreso al try",Toast.LENGTH_SHORT).show();
            // dentro de la consulta: 1 Tabla, 2 Campos a devolver, 3 Parametro de consulta, 4 Dato a filtrar, siguiente
            Cursor cursor = db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_IDUSUARIO+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            tvContraseñaLog.setText(cursor.getString(0));
            tvNombreLog.setText(cursor.getString(1));
            cursor.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    private void limpiar() {
        tvContraseñaLog.setText("");
        tvNombreLog.setText("");
    }
}
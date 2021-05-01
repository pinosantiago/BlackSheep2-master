package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class IngresoUsuario extends AppCompatActivity {

    EditText tvUsuarioLog,tvContraseñaLog,tvNombreLog,tvPaisLog;
    private TextView mDisplayDate,tvFechaNacimiento;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button Confirmar, Cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_usuario);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        mDisplayDate      = (TextView) findViewById(R.id.tvDate);
        tvUsuarioLog      = (EditText) findViewById(R.id.tvUsuarioLog);
        tvContraseñaLog   = (EditText) findViewById(R.id.tvContraseñaLog);
        tvNombreLog       = (EditText) findViewById(R.id.tvNombreLog);
        tvFechaNacimiento = (TextView) findViewById(R.id.tvDate);
        tvPaisLog         = (EditText) findViewById(R.id.tvPaisLog);
        Confirmar         = (Button) findViewById(R.id.BtnConfirmar);
        Cancelar          = (Button) findViewById(R.id.btnCancelarIngresoUsuario);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year     = cal.get(Calendar.YEAR);
                int month    = cal.get(Calendar.MONTH);
                int day      = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(IngresoUsuario.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        Confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Usar registrarUsuario() y no registrarUsuarioSql() --> Falta insert
                registrarUsuario();
                registrarPerfiles();
                finish();
            }
        });
        Cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registrarPerfiles() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //--> Cargo Perfil por Defecto 1
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_PERFIL_NOMBRE,"Perfil por defecto");
        Long idResultante = db.insert(Utilidades.TABLA_PERFILES, Utilidades.CAMPO_IDPERFIL,values);
        //Toast.makeText(getApplicationContext(),"Id Registro1: " + idResultante,Toast.LENGTH_SHORT).show();

        //--> Cargo Relacion Perfil-Usuario
        ContentValues valuesRel1 = new ContentValues();
        valuesRel1.put(Utilidades.CAMPO_IDPERFILU,idResultante);
        valuesRel1.put(Utilidades.CAMPO_IDPERFIL_USUARIO,tvUsuarioLog.getText().toString());
        Long idResultanteRel1 = db.insert(Utilidades.TABLA_USUARIO_PERFILES, Utilidades.CAMPO_IDPERFIL,valuesRel1);
        //Toast.makeText(getApplicationContext(),"Id Registro Relacion1: " + idResultanteRel1,Toast.LENGTH_SHORT).show();

        db.close();
        Toast.makeText(getApplicationContext(),"El registro fue exitoso",Toast.LENGTH_SHORT).show();
    }

    private void registrarUsuarioSql() {
        //--> Falta la ejecucion del inser!!!
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Insert into usuario (idUsuario,contraseña,nombre,fechaNacimiento,pais) values ('UsuarioADM','1234','Administrador','01/01/2021','Uruguay')
        String insert = "INSERT INTO " + Utilidades.TABLA_USUARIO + " ("
                +Utilidades.CAMPO_IDUSUARIO + ","
                +Utilidades.CAMPO_CONTRASEÑA + ", "
                +Utilidades.CAMPO_NOMBRE + ", "
                +Utilidades.CAMPO_FECHANACIMIENTO + ", "
                +Utilidades.CAMPO_PAIS + ")"
                +" VALUES ('" +tvUsuarioLog.getText().toString()+"', '" + tvContraseñaLog.getText().toString()+"', '"+ tvNombreLog.getText().toString()+"', "+tvFechaNacimiento.getText().toString()+", '"+ tvPaisLog.getText().toString()+"')";
        db.close();

        //tvUsuarioLog.setText("");
        tvContraseñaLog.setText("");
        tvNombreLog.setText("");
        tvPaisLog.setText("");
        //Toast.makeText(getApplicationContext(),"El registro fue exitoso",Toast.LENGTH_SHORT).show();
    }

    private void registrarUsuario() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_IDUSUARIO,tvUsuarioLog.getText().toString());
        values.put(Utilidades.CAMPO_CONTRASEÑA,tvContraseñaLog.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,tvNombreLog.getText().toString());
        values.put(Utilidades.CAMPO_FECHANACIMIENTO,tvFechaNacimiento.getText().toString());
        values.put(Utilidades.CAMPO_PAIS,tvPaisLog.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_IDUSUARIO,values);

        //Toast.makeText(getApplicationContext(),"Id Registro" + idResultante,Toast.LENGTH_SHORT).show();
        db.close();

        //tvUsuarioLog.setText("");
        tvContraseñaLog.setText("");
        tvNombreLog.setText("");
        tvFechaNacimiento.setText("");
        tvPaisLog.setText("");
        //Toast.makeText(getApplicationContext(),"El registro fue exitoso",Toast.LENGTH_SHORT).show();
    }
}
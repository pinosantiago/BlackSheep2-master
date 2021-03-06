package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class ActualizarCaja extends AppCompatActivity {
    Caja caja;
    int funcionalidad; //--> 1 - Agregar, 2 - Actualizar
    ConexionSQLiteHelper conn;
    EditText etNombre,etPorcentaje,etDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_caja);
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        recibirDatos();
        etNombre      = (EditText) findViewById(R.id.etNombre);
        etPorcentaje  = (EditText) findViewById(R.id.etPorcentaje);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);

        if (funcionalidad == 2){
            etNombre.setText(caja.getNombre());
            etPorcentaje.setText(caja.getPorcentaje().toString());
            etDescripcion.setText(caja.getDescripcion());
        }

    }

    private void redefinirPorcentaje() {
        Caja caja1 = caja;
        Intent detalleCaja = new Intent(ActualizarCaja.this, RedefinirPorcentajeCaja.class);
        Bundle bundleDetalleCaja1  = new Bundle();
        bundleDetalleCaja1.putSerializable("caja",caja1);
        detalleCaja.putExtras(bundleDetalleCaja1);
        startActivity(detalleCaja);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAceptar:
                if(etNombre.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresa un nombre para la caja",Toast.LENGTH_SHORT).show();
                }else{
                    //si el nombre no esta vacio se fija si el porcentaje no se modifico
                    //Toast.makeText(getApplicationContext(),"el %%%% "+caja.getPorcentaje().toString(),Toast.LENGTH_SHORT).show();
                    if (funcionalidad != 1){
                        if(caja.getPorcentaje() != Integer.parseInt(etPorcentaje.getText().toString())){
                            modificarCaja();
                            redefinirPorcentaje();
                            finish();
                        } else{
                            modificarCaja();
                            finish();
                        }
                    } else {
                        creoCaja();
                        redefinirPorcentaje();
                        finish();
                    }
                }
                break;
            case R.id.btnCancelar:
                finish();
                break;
        }
    }

    private void creoCaja() {
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //--> Cargo Cajas por Defecto
        ContentValues values = new ContentValues();
        values.clear();
        values.put(Utilidades.CAMPO_CAJA_IDPERFIL,caja.getIdPerfil());
        values.put(Utilidades.CAMPO_PERFIL_NOMBRE,etNombre.getText().toString());
        values.put(Utilidades.CAMPO_CAJA_PORCENTAJE,etPorcentaje.getText().toString());
        values.put(Utilidades.CAMPO_CAJA_MONTO,0);
        values.put(Utilidades.CAMPO_CAJA_DESCRIPCION,etDescripcion.getText().toString());
        Long idResultante = db.insert(Utilidades.TABLA_CAJAS, Utilidades.CAMPO_IDCAJA,values);
        //Toast.makeText(getApplicationContext(),"Id Registro: " + idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void modificarCaja() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parametro = {caja.getIdCaja().toString()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_CAJA_NOMBRE,etNombre.getText().toString());
        values.put(Utilidades.CAMPO_CAJA_PORCENTAJE,etPorcentaje.getText().toString());
        values.put(Utilidades.CAMPO_CAJA_DESCRIPCION,etDescripcion.getText().toString());
        db.update(Utilidades.TABLA_CAJAS,values,Utilidades.CAMPO_IDCAJA+"=?",parametro);
        //Toast.makeText(getApplicationContext(),"Ya se actualizo la caja!",Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        caja = (Caja) objetoEnviado.getSerializable("caja");
        if(caja.getIdCaja() == 0){
            //--> 1 - Creo nueva Caja
            funcionalidad = 1;
        } else {
            //--> 1 - Actualizo Caja
            funcionalidad = 2;
        }
    }
}
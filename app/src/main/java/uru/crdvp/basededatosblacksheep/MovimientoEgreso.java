package uru.crdvp.basededatosblacksheep;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class MovimientoEgreso extends AppCompatActivity {
    EditText monto,desc,fecha,idcaja;
    Spinner spinnerCaja;
    ConexionSQLiteHelper conn;
    Perfil perfil;
    ArrayList<Caja> listaCajas;
    ArrayList<Integer> listaCajaAux;
    ArrayList<String> listaPerfiles;
    int contador;
    @Override
    public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movimiento_egreso);
            recibirDatos();
            fecha           =  (EditText) findViewById(R.id.fechaegreso);
            monto           =  (EditText) findViewById(R.id.montoegreso);
            desc            =  (EditText) findViewById(R.id.descegreso);
            spinnerCaja     =   (Spinner) findViewById(R.id.SpnCaja);
            conn            =   new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);

            obtenerCajas();
            ArrayAdapter<CharSequence> adaptador = new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaPerfiles);
            spinnerCaja.setAdapter(adaptador);

            spinnerCaja.getSelectedItemId();
        }

    private void obtenerLista() {
        listaPerfiles = new ArrayList<String>();
        for (int i = 0; i< listaCajas.size();i++){
            listaPerfiles.add(listaCajas.get(i).getNombre());
        }
    }
    private void obtenerCajas() {
        SQLiteDatabase db    = conn.getReadableDatabase();
        listaCajas           = new ArrayList<Caja>();
        try {
            //--> Obtengo datos de relacion Perfil/Usuario!
            Cursor cursor  = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CAJAS + " WHERE idPerfil = "+perfil.getIdPerfil().toString(),null);
            while (cursor.moveToNext()){
                    Caja cajaAux = new Caja(null,null,null,null,null,null);
                    cajaAux.setIdCaja(cursor.getInt(0));
                    cajaAux.setIdPerfil(cursor.getString(1));
                    cajaAux.setNombre(cursor.getString(2));
                    cajaAux.setPorcentaje(cursor.getInt(3));
                    cajaAux.setMonto(cursor.getInt(4));
                    cajaAux.setDescripcion(cursor.getString(5));
                    listaCajas.add(cajaAux);
            }
            obtenerLista();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error en la carga de Tabla_Cajas",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view){
            switch (view.getId()){
                case R.id.BtnConfirmaregreso :
                    ingresarMontoBD();
                    finish();
                    break;
            }

        }
    public void ingresarMontoBD(){
        ConexionSQLiteHelper conn =  new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        int idComboCaja = (int) spinnerCaja.getSelectedItemId();
        //--> Se genera movimiento de Egreso
        ContentValues valuesMovIng  = new ContentValues();
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_IDPERFIL, perfil.getIdPerfil());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_MONTO, "-"+monto.getText().toString());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_INGEGR,"E");
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_DESCRIPCION, desc.getText().toString());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_CAJA, listaCajas.get(idComboCaja).getIdCaja());
        Long var = db.insert(Utilidades.TABLA_MOVIMIENTOS,null,valuesMovIng);

        //--> Actualizo Tabla Caja
        String [] parametro = {listaCajas.get(idComboCaja).getIdCaja().toString()};
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CAJAS + " WHERE idCaja = "+listaCajas.get(idComboCaja).getIdCaja(),null);
        int montoAuxiliar =  cursor2.getInt(4);
        int nuevoMonto = montoAuxiliar - Integer.parseInt(monto.getText().toString());
        ContentValues valuesMovIngCaja  = new ContentValues();
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_IDPERFIL, perfil.getIdPerfil());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_MONTO, "-"+nuevoMonto);
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_INGEGR,"E");
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_DESCRIPCION, desc.getText().toString());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_CAJA, listaCajas.get(idComboCaja).getIdCaja());
        db.update(Utilidades.TABLA_CAJAS,valuesMovIngCaja,Utilidades.CAMPO_IDCAJA+"=?",parametro);

        db.close();

    }
    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }


}


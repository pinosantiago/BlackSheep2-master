package uru.crdvp.basededatosblacksheep;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.R;
import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class MovimientoIngreso extends AppCompatActivity {
    EditText monto,desc,fecha;
    Perfil perfil;
    ConexionSQLiteHelper conn;
    Spinner spinnerCaja;
    ArrayList<String> listaPerfiles;
    ArrayList<Caja> listaCajas;
    ArrayAdapter<CharSequence> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_ingreso);
        recibirDatos();


        fecha       =  (EditText) findViewById(R.id.fechaingreso);
        monto       =  (EditText) findViewById(R.id.montoingreso);
        desc        =  (EditText) findViewById(R.id.descingreso);
        spinnerCaja =  (Spinner)  findViewById(R.id.SpnCajaIngreso);

        conn        =   new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);

        obtenerCajas();
        adaptador = new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaPerfiles);
        spinnerCaja.setAdapter(adaptador);


    }

    private void obtenerLista() {
        listaPerfiles = new ArrayList<String>();
        listaPerfiles.add("Todas las cajas");
        for (int i = 0; i < listaCajas.size(); i++) {
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
            case R.id.BtnConfirmar1 :
            ingresarMontoBD();
            actualizaMontoCaja();
            finish();
            break;
            case R.id.btnCancelarIngreso:
                finish();
                break;
        }
    }
     public void ingresarMontoBD(){
        ConexionSQLiteHelper conn =  new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        int idComboCaja = (int) spinnerCaja.getSelectedItemId() - 1;
         String idAux = null;
        if (idComboCaja != -1){
            idAux = listaCajas.get(idComboCaja).getIdCaja().toString();
        }

        ContentValues valuesMovIng  = new ContentValues();
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_IDPERFIL, perfil.getIdPerfil());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_MONTO, monto.getText().toString());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_INGEGR,"I");
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_DESCRIPCION, desc.getText().toString());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_CAJA,idAux);
        Long var = db.insert(Utilidades.TABLA_MOVIMIENTOS,null,valuesMovIng);
        db.close();
     }

     void actualizaMontoCaja(){
        SQLiteDatabase db = conn.getWritableDatabase();
        int idComboCaja = (int) spinnerCaja.getSelectedItemId() - 1;
        Toast.makeText(getApplicationContext(), "idComboCaja: " + idComboCaja,Toast.LENGTH_SHORT).show();
        if(idComboCaja==-1){
             int x = 0;
             while(x<listaCajas.size()){
                 double montoAuxiliar  = 0;
                 double montoCalculado = 0;
                 double nuevoMonto     = 0;
                 String [] parametro = {listaCajas.get(x).getIdCaja().toString()};
                 montoAuxiliar  =  listaCajas.get(x).getMonto();
                 montoCalculado =  calculoPorcentaje(Integer.parseInt(monto.getText().toString()),listaCajas.get(x).getPorcentaje());
                 nuevoMonto     = montoAuxiliar + montoCalculado;
                 ContentValues valuesMovIngCaja  = new ContentValues();
                 valuesMovIngCaja.put(Utilidades.CAMPO_CAJA_MONTO, nuevoMonto);
                 db.update(Utilidades.TABLA_CAJAS,valuesMovIngCaja,Utilidades.CAMPO_IDCAJA+"=?",parametro);
                 valuesMovIngCaja.clear();
                 x++;
             }
         }
         else{
             String [] parametro = {listaCajas.get(idComboCaja).getIdCaja().toString()};
             double   montoAuxiliar  =  listaCajas.get(idComboCaja).getMonto();
             double nuevoMonto = montoAuxiliar + Integer.parseInt(monto.getText().toString());
             ContentValues valuesMovIngCaja  = new ContentValues();
             valuesMovIngCaja.put(Utilidades.CAMPO_CAJA_MONTO, nuevoMonto);
             db.update(Utilidades.TABLA_CAJAS,valuesMovIngCaja,Utilidades.CAMPO_IDCAJA+"=?",parametro);
             valuesMovIngCaja.clear();
         }
         db.close();
     }
    private double calculoPorcentaje(double montoAuxiliar, int porcentaje) {return (montoAuxiliar * porcentaje)/100;}

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }
}

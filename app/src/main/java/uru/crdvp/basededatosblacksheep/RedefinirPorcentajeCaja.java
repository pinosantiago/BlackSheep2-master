package uru.crdvp.basededatosblacksheep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Movimientos;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class RedefinirPorcentajeCaja extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    Caja caja;
    ArrayList<Caja> listaCajas = new ArrayList<Caja>();
    RecyclerView recycler;
    AdaptadorPorcentajeCajas adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_porcentaje_caja);
        conn =  new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        recibirDatos();

        //--> Cargo el RecyclerView
        recycler = findViewById(R.id.rvRedefinoPorcentaje);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdaptadorPorcentajeCajas(listaCajas);
        recycler.setAdapter(adapter);
        llenarlista(listaCajas, adapter);
        Toast.makeText(getApplicationContext(),"Redefine los porcentajes de las cajas porfavor!",Toast.LENGTH_SHORT).show();
    }

    protected void onResume() {
        super.onResume();
        listaCajas.clear();
        listaCajas = adapter.devuelvoPorcentajes();
        //--> Cargo el RecyclerView
        recycler = findViewById(R.id.rvRedefinoPorcentaje);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdaptadorPorcentajeCajas(listaCajas);
        recycler.setAdapter(adapter);
        llenarlista(listaCajas, adapter);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresoPorcentaje:
                boolean terminoCarga =  recargoPorcentajes();
                if(terminoCarga){
                    finish();
                }
                break;
            case R.id.btnCancelarPorcentaje:
                finish();
                break;
        }
    }

    private boolean recargoPorcentajes() {
        boolean terminoCargaAux = false;
        SQLiteDatabase db    = conn.getWritableDatabase();
        listaCajas           = adapter.devuelvoPorcentajes();
        ContentValues values = new ContentValues();
        //Toast.makeText(getApplicationContext(),"idPerfil " + caja.getIdPerfil(),Toast.LENGTH_SHORT).show();
        boolean valido_Porcentaje_Caja = validoPorcentaje(listaCajas);
        if (valido_Porcentaje_Caja){
            for(int i = 0; i<listaCajas.size();i++){
                //--> Cargo Cajas por Defecto
                String [] parametro  = {listaCajas.get(i).getIdCaja().toString()};
                values.put(Utilidades.CAMPO_CAJA_PORCENTAJE,listaCajas.get(i).getPorcentaje());
                db.update(Utilidades.TABLA_CAJAS,values,Utilidades.CAMPO_IDCAJA+"=?",parametro);
                values.clear();
            }
            terminoCargaAux = true;
        } else {
            Toast.makeText(getApplicationContext(),"Los porcentajes ingresado no cumplen el 100%",Toast.LENGTH_SHORT).show();
        }
        db.close();
        return terminoCargaAux;
    }

    private boolean validoPorcentaje(ArrayList<Caja> listaCajas) {
        int contador = 0;
        boolean valido = false;
        for(int i = 0; i<listaCajas.size();i++){
            contador+= listaCajas.get(i).getPorcentaje();
        }
        //Toast.makeText(getApplicationContext(),"Total Porcentaje: " + contador,Toast.LENGTH_SHORT).show();
        if(contador == 100){
            valido = true;
        }
        return valido;
    }

    public void llenarlista(ArrayList<Caja> listaCajas, AdaptadorPorcentajeCajas adapter){
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor cursor     = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CAJAS + " WHERE idPerfil = "+caja.getIdPerfil(),null);
        while (cursor.moveToNext()){
            // Cargo las Cajas del perfil
            int idCaja      = cursor.getInt(0);
            String idPerfil = cursor.getString(1);
            String Nombre   = cursor.getString(2);
            int porcentaje  = cursor.getInt(3);
            int monto       = cursor.getInt(4);
            String desc     = cursor.getString(5);
            listaCajas.add(new Caja(idCaja,idPerfil,Nombre,porcentaje,monto,desc));
            adapter.notifyItemInserted(listaCajas.size() - 1);
        }

        /*  --> Calcular el Total de los porcentajes!!!
        String totalStr = "Total: $" + totalcuenta;
        total = (TextView) findViewById(R.id.tvTotalBalance);
        total.setText(totalStr);
         */
        db.close();
    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        caja = (Caja) objetoEnviado.getSerializable("caja");
    }
}
package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.entidades.UsuariosPerfiles;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class Cajas extends AppCompatActivity {

    Perfil perfil;
    FloatingActionButton fabAgregarCaja;
    ConexionSQLiteHelper conn;
    Caja caja,cajaAux;
    ArrayList<Integer> listaIdCajas;
    ArrayList<Caja> listaCajas = new ArrayList<>();
    RecyclerView recyclerCaja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajas);
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        recibirDatos();
        int cantidadCajas = verificoPerfil();

        //Toast.makeText(getApplicationContext(), "Ingrese con " + cantidadCajas + " Cajas!",Toast.LENGTH_SHORT).show();

        if(cantidadCajas == 0){
            cajasPorDefecto();
        }

        recyclerCaja = findViewById(R.id.rvcajas);
        GridLayoutManager linearmanager = new GridLayoutManager(this,2);
        recyclerCaja.setLayoutManager(linearmanager);
        AdaptadorCaja cajaAdaptador = new AdaptadorCaja(listaCajas);

        cajaAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Caja caja = listaCajas.get(recyclerCaja.getChildAdapterPosition(v));
                  Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                  Bundle bundleDetalleCaja1  = new Bundle();
                  bundleDetalleCaja1.putSerializable("caja",caja);
                  detalleCaja.putExtras(bundleDetalleCaja1);
                  startActivity(detalleCaja);
            }
        });

        recyclerCaja.setAdapter(cajaAdaptador);
        fabAgregarCaja = (FloatingActionButton) findViewById(R.id.fabAgregarCaja);
        fabAgregarCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cajaAux       = new Caja(0,perfil.getIdPerfil().toString(),null,null,null,null);
                Intent intent = new Intent(Cajas.this,ActualizarCaja.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("caja",cajaAux);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        listaCajas.clear();
        int cantidadCajas = verificoPerfil();
        if(cantidadCajas == 0){
            cajasPorDefecto();
        }
        recyclerCaja = findViewById(R.id.rvcajas);
        GridLayoutManager linearmanager = new GridLayoutManager(this,2);
        recyclerCaja.setLayoutManager(linearmanager);
        AdaptadorCaja cajaAdaptador = new AdaptadorCaja(listaCajas);
        cajaAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Caja caja = listaCajas.get(recyclerCaja.getChildAdapterPosition(v));
                Intent detalleCaja = new Intent(Cajas.this, CajaDetalle.class);
                Bundle bundleDetalleCaja1  = new Bundle();
                bundleDetalleCaja1.putSerializable("caja",caja);
                detalleCaja.putExtras(bundleDetalleCaja1);
                startActivity(detalleCaja);
            }
        });
        recyclerCaja.setAdapter(cajaAdaptador);
    }

    private void cajasPorDefecto() {
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //--> Cargo Cajas por Defecto
        int defectoCaja = 1;
        ContentValues values = new ContentValues();
        while (defectoCaja < 6){
            values.clear();
            values.put(Utilidades.CAMPO_CAJA_IDPERFIL,perfil.getIdPerfil());
            values.put(Utilidades.CAMPO_PERFIL_NOMBRE,"Caja " + defectoCaja);
            values.put(Utilidades.CAMPO_CAJA_PORCENTAJE,20);
            values.put(Utilidades.CAMPO_CAJA_MONTO,0);
            values.put(Utilidades.CAMPO_CAJA_DESCRIPCION,"");
            Long idResultante = db.insert(Utilidades.TABLA_CAJAS, Utilidades.CAMPO_IDCAJA,values);
            defectoCaja++;
        }
        db.close();
    }

    private int verificoPerfil() {
        SQLiteDatabase db       = conn.getReadableDatabase();
        int cantidadCajasPerfil = 0;
        int idCaja;
        String idPerfil;
        //listaIdCajas = new ArrayList<Integer>();
        listaCajas = new ArrayList<Caja>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CAJAS,null);
            while (cursor.moveToNext()){
                // Cargo el IdPerfil de la relacion
                idCaja   = cursor.getInt(0);
                idPerfil = cursor.getString(1);
                if(perfil.getIdPerfil() == Integer.parseInt(idPerfil.trim())){
                    cantidadCajasPerfil++;
                    caja = new Caja(idCaja,idPerfil,cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
                    listaCajas.add(caja);
                }
            }
            db.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en la carga de Cajas!",Toast.LENGTH_SHORT).show();
        }
        return cantidadCajasPerfil;
    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.imgBalance:
                // Viajo al Panel Balance
                Intent balance = new Intent(Cajas.this, Balance.class);
                Bundle bundleBalance  = new Bundle();
                bundleBalance.putSerializable("perfil",perfil);
                balance.putExtras(bundleBalance);
                startActivity(balance);
                break;
            case R.id.imgCajas:
                // Viajo al Panel de Cajas
                Toast.makeText(getApplicationContext(), "Panel de Cajas!",Toast.LENGTH_SHORT).show();
                Intent cajas  = new Intent(Cajas.this, Cajas.class);
                Bundle bundleCajas = new Bundle();
                bundleCajas.putSerializable("perfil",perfil);
                cajas.putExtras(bundleCajas);
                startActivity(cajas);
                break;
            case R.id.imgNotificaciones:
                // Viajo al Panel de Notificaciones
                Toast.makeText(getApplicationContext(), "Panel de Notificaciones Proximamente!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }
}


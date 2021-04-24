package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class CajaDetalle extends AppCompatActivity {
    Caja caja;
    TextView tvNombreCaja, tvDescripcionCaja, tvPorcentajeCaja, tvMontoCaja;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja_detalle);
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        tvNombreCaja      = (TextView) findViewById(R.id.tvNombreCaja);
        tvDescripcionCaja = (TextView) findViewById(R.id.tvDescripcionCaja);
        tvPorcentajeCaja  = (TextView) findViewById(R.id.tvPorcentajeCaja);
        tvMontoCaja       = (TextView) findViewById(R.id.tvMontoCaja);

        recibirDatos();
        verificoCaja();

    }
    private void verificoCaja() {
        SQLiteDatabase db = conn.getReadableDatabase();
        int idCaja;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CAJAS,null);
            while (cursor.moveToNext()){
                idCaja = cursor.getInt(0);
                if(caja.getIdCaja() == idCaja){
                    tvNombreCaja.setText(caja.getNombre());
                    tvDescripcionCaja.setText("Descripcion: " + (caja.getDescripcion()));
                    tvPorcentajeCaja.setText("Porcentanje: " + caja.getPorcentaje() + "%");
                    tvMontoCaja.setText("Total: $ " + caja.getMonto());
                }
            }
            db.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en la carga de Cajas!",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnEliminarCaja:
                eliminarCaja();
                finish();
                break;
            case R.id.btnEditarCaja:
                Caja caja1 = caja;
                Intent detalleCaja = new Intent(CajaDetalle.this, ActualizarCaja.class);
                Bundle bundleDetalleCaja1  = new Bundle();
                bundleDetalleCaja1.putSerializable("caja",caja1);
                detalleCaja.putExtras(bundleDetalleCaja1);
                startActivity(detalleCaja);
                finish();
                break;
        }
    }

    private void eliminarCaja() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parametro = {caja.getIdCaja().toString()};
        db.delete(Utilidades.TABLA_CAJAS,Utilidades.CAMPO_IDCAJA+"=?",parametro);
        //Toast.makeText(getApplicationContext(),"Ya se elimino la caja!",Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        caja = (Caja) objetoEnviado.getSerializable("caja");
    }
}
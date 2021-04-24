package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.entidades.UsuariosPerfiles;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class Balance extends AppCompatActivity {
    Perfil perfil;
    ArrayList<String> listDatos = new ArrayList<>();
    RecyclerView recycler;
    ConexionSQLiteHelper conn =  new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
    String monto;
    TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        recibirDatos();
        recycler = findViewById(R.id.rvBalance);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorBalance adapter = new AdaptadorBalance(listDatos);
        recycler.setAdapter(adapter);
        llenarlista(listDatos, adapter);

    }

    protected void onResume() {
        super.onResume();
        listDatos.clear();
        recibirDatos();
        recycler = findViewById(R.id.rvBalance);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorBalance adapter = new AdaptadorBalance(listDatos);
        recycler.setAdapter(adapter);
        llenarlista(listDatos, adapter);


    }


    public void llenarlista(ArrayList<String> listDatos,AdaptadorBalance adapter){
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_MOVIMIENTOS + " WHERE idPerfil = "+perfil.getIdPerfil().toString()+" ORDER BY idMovimiento DESC",null);
        int k = 1;
        int totalcuenta = 0;
        while (cursor.moveToNext()){
            // Cargo los movimientos del perfil
            monto  = cursor.getString(2);
            listDatos.add(monto);
            adapter.notifyItemInserted(listDatos.size() - 1);
                totalcuenta += Integer.parseInt(monto);
                k++;
            }

            String totalStr = "Total: $" + totalcuenta;
            total = (TextView) findViewById(R.id.tvTotalBalance);
            total.setText(totalStr);




        db.close();
    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresoMov:
                // Viajo al Panel de Ingresos de Movimiento
                Intent i = new Intent(Balance.this, MovimientoIngreso.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("perfil",perfil);
                i.putExtras(bundle);
                startActivity(i);
                onResume();



                break;
            case R.id.btnIngresoGasto:
                // Viajo al Panel de Egresos de Caja
                Intent i2 = new Intent(Balance.this, MovimientoEgreso.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("perfil",perfil);
                i2.putExtras(bundle2);
                startActivity(i2);
                break;
            case R.id.imgBalance:
                // Viajo al Panel Balance
                Intent balance = new Intent(Balance.this, Balance.class);
                Bundle bundleBalance2  = new Bundle();
                bundleBalance2.putSerializable("perfil",perfil);
                balance.putExtras(bundleBalance2);
                startActivity(balance);
                break;
            case R.id.imgCajas:
                // Viajo al Panel de Cajas
                Intent cajas  = new Intent(Balance.this, Cajas.class);
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
}
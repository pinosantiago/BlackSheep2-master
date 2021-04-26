package uru.crdvp.basededatosblacksheep;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Movimientos;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class DetalleMovimiento extends AppCompatActivity {

    String fecha, desc,monto;
    ConexionSQLiteHelper conn;
    TextView tvMontoMovimiento, tvFechaMovimiento, tvDescMovimiento;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_detalle);
        conn = new ConexionSQLiteHelper(this, "bd_BlackSheep", null, 1);
        tvMontoMovimiento = (TextView) findViewById(R.id.tvmontomovimientodetalle);
        tvFechaMovimiento = (TextView) findViewById(R.id.tvfechadetallemov);
        tvDescMovimiento = (TextView) findViewById(R.id.tvdescdetallemov);
        recibirDatos();


        tvMontoMovimiento.setText("Monto: $"+monto);
        tvDescMovimiento.setText("Descripción: "+desc);
        //tvFechaMovimiento.setText("Descripción: "+desc);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fecha2 = dateFormat.format(date);
        tvFechaMovimiento.setText("Fecha: "+fecha2);



    }


    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        monto = objetoEnviado.getString("montomovi");
        fecha = objetoEnviado.getString("fechamovi");
        desc = objetoEnviado.getString("descmovi");
    }

    public void atras(View view) {
        finish();
    }
}

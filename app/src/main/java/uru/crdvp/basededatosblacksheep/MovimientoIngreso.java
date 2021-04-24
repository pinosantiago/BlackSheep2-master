package uru.crdvp.basededatosblacksheep;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uru.crdvp.basededatosblacksheep.R;
import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class MovimientoIngreso extends AppCompatActivity {
    EditText monto,desc,fecha;
    Perfil perfil;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_ingreso);
        recibirDatos();

        fecha       =  (EditText) findViewById(R.id.fechaingreso);
        monto       =  (EditText) findViewById(R.id.montoingreso);
        desc        =  (EditText) findViewById(R.id.descingreso);
        conn        =   new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.BtnConfirmar1 :
            ingresarMontoBD();
            finish();
            break;
        }
    }
     public void ingresarMontoBD(){
        ConexionSQLiteHelper conn =  new ConexionSQLiteHelper(this, "bd_BlackSheep", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valuesMovIng  = new ContentValues();
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_IDPERFIL, perfil.getIdPerfil());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_MONTO, monto.getText().toString());
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_INGEGR,"I");
        valuesMovIng.put(Utilidades.CAMPO_MOVIMIENTO_DESCRIPCION, desc.getText().toString());
        Long var = db.insert(Utilidades.TABLA_MOVIMIENTOS,null,valuesMovIng);
        db.close();

     }
    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        perfil = (Perfil) objetoEnviado.getSerializable("perfil");
    }
}

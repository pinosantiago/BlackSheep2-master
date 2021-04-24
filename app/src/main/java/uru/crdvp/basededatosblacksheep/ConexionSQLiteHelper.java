package uru.crdvp.basededatosblacksheep;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper{

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidades.CREAR_TABLA_PERFILES);
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO_PERFILES);
        db.execSQL(Utilidades.CREAR_TABLA_CAJAS);
        db.execSQL(Utilidades.CREAR_TABLA_MOVIMIENTOS);
        db.execSQL(Utilidades.CREAR_TABLA_MOVIMIENTO_CAJAS_PERFILES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIO_PERFILES);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PERFILES);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CAJAS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_MOVIMIENTOS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_MOVIMIENTO_CAJAS_PERFILES);
        onCreate(db);
    }
}

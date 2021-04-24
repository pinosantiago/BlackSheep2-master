package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.entidades.Usuario;
import uru.crdvp.basededatosblacksheep.entidades.UsuariosPerfiles;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class VerPerfiles extends AppCompatActivity {

    ListView listViewPerfiles;
    ConexionSQLiteHelper conn;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuario;
    ArrayList<UsuariosPerfiles> listaUsuarioPerfiles;
    ArrayList<Perfil> listaPerfiles;
    Usuario usuarioIngreso;
    Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfiles);

        //recibirDatos();

        conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_BlackSheep",null,1);

        listViewPerfiles = (ListView) findViewById(R.id.listViewVerPerfiles);

        consultarListaPersonas();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listViewPerfiles.setAdapter(adaptador);

        listViewPerfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String informacion = "Usuario: " + listaUsuario.get(position).getIdUsuario()+"\n";
                //informacion += "Nombre: " + listaUsuario.get(position).getNombre()+"\n";
                //informacion += "Pais: " + listaUsuario.get(position).getPais()+"\n";
                //Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_SHORT).show();

                Usuario user = listaUsuario.get(position);
                Intent intent = new Intent(VerPerfiles.this,DetallePerfil.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        usuarioIngreso = (Usuario) objetoEnviado.getSerializable("usuario");
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = null;
        listaUsuario = new ArrayList<Usuario>();
        // select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFILES,null);
        while (cursor.moveToNext()){
            perfil = new Perfil(null,null);
            perfil.setIdPerfil(Integer.parseInt(cursor.getString(0)));
            perfil.setNombre(cursor.getString(1));
            listaPerfiles.add(perfil);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for (int i = 0; i< listaPerfiles.size();i++){
            listaInformacion.add(listaPerfiles.get(i).getIdPerfil() + " - "
                    + listaPerfiles.get(i).getNombre());
        }
    }
}
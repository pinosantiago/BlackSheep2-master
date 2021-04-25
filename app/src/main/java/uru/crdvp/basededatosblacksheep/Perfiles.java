package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Currency;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Perfil;
import uru.crdvp.basededatosblacksheep.entidades.Usuario;
import uru.crdvp.basededatosblacksheep.entidades.UsuariosPerfiles;
import uru.crdvp.basededatosblacksheep.utilidades.Utilidades;

public class Perfiles extends AppCompatActivity {

    ListView listViewPerfiles;
    ConexionSQLiteHelper conn;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuario;
    ArrayList<UsuariosPerfiles> listaUsuarioPerfiles;
    ArrayList<Perfil> listaPerfiles;
    Usuario usuarioIngreso;
    Perfiles perfiles;
    FloatingActionButton fabAgregarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);
        recibirDatos();
        conn             = new ConexionSQLiteHelper(getApplicationContext(),"bd_BlackSheep",null,1);
        listViewPerfiles = (ListView) findViewById(R.id.listViewPerfiles);
        fabAgregarPerfil = (FloatingActionButton) findViewById(R.id.fabAgregarPerfil);

        //consultarListaPersonas();
        consultarPerfilUsuario();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listViewPerfiles.setAdapter(adaptador);

        listViewPerfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Perfil perfil = listaPerfiles.get(position);
                //Toast.makeText(getApplicationContext(),"Perfil: " + perfil.getIdPerfil() + "\n" + "Nombre: " + perfil.getNombre(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Perfiles.this,Cajas.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("perfil",perfil);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        fabAgregarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfiles.this,RegistroPerfiles.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario",usuarioIngreso);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        listaInformacion.clear();
        consultarPerfilUsuario();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listViewPerfiles.setAdapter(adaptador);

        listViewPerfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Perfil perfil = listaPerfiles.get(position);
                //Toast.makeText(getApplicationContext(),"Perfil: " + perfil.getIdPerfil() + "\n" + "Nombre: " + perfil.getNombre(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Perfiles.this,Cajas.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("perfil",perfil);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        fabAgregarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfiles.this,RegistroPerfiles.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario",usuarioIngreso);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void consultarPerfilUsuario() {
        SQLiteDatabase db    = conn.getReadableDatabase();
        listaUsuarioPerfiles = new ArrayList<UsuariosPerfiles>();
        listaPerfiles        = new ArrayList<Perfil>();
        listaInformacion     = new ArrayList<String>();

        String idPerfil,idUsuario;
        Perfil perfil = null;
        UsuariosPerfiles usuariosPerfiles = null;

        //String [] parametros  = {usuarioIngreso.getIdUsuario().toString()};
        //Toast.makeText(getApplicationContext(),"Se cargo el Usuario: "+ parametros[0],Toast.LENGTH_SHORT).show();
        // select * from TABLA_USUARIO_PERFILES
        try {
            //--> Obtengo datos de relacion Perfil/Usuario!
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO_PERFILES,null);
            int k = 1;
            while (cursor.moveToNext()){
                // Cargo el IdPerfil de la relacion
                idPerfil  = cursor.getString(0);
                idUsuario = cursor.getString(1);
                if(usuarioIngreso.getIdUsuario().toString().toUpperCase().equals(idUsuario.toUpperCase())){
                    usuariosPerfiles = new UsuariosPerfiles(null,null);
                    usuariosPerfiles.setIdUsuario(idUsuario);
                    usuariosPerfiles.setIdPerfil(Integer.parseInt(idPerfil));
                    listaUsuarioPerfiles.add(usuariosPerfiles);
                    k++;
                }
            }
            //--> Obtengo datos de Perfiles!
            Cursor cursor2 = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFILES,null);
            int r = 1;
            while (cursor2.moveToNext()){
                perfil = new Perfil(null,null);
                perfil.setIdPerfil(Integer.parseInt(cursor2.getString(0)));
                perfil.setNombre(cursor2.getString(1));
                listaPerfiles.add(perfil);
                r++;

            }

            //Toast.makeText(getApplicationContext(),"Cantidad de relaciones: " + k + "\n" + "Cantidad de perfiles: " + r,Toast.LENGTH_SHORT).show();

            listaInformacion = new ArrayList<String>();
            for(int i = 0; i<listaUsuarioPerfiles.size();i++){
                //String usuario  = listaUsuarioPerfiles.get(i).getIdUsuario();
                int idPerfilUsu = listaUsuarioPerfiles.get(i).getIdPerfil();
                for(int j = 0; j<listaPerfiles.size();j++){
                    int idPerfilAux = listaPerfiles.get(j).getIdPerfil();
                    if(idPerfilUsu == idPerfilAux){
                        int perfilNumerador = j+1;
                        listaInformacion.add(perfilNumerador + " - "
                                + listaPerfiles.get(j).getNombre());
                    }
                }
            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "El usuario " + usuarioIngreso.getIdUsuario(),Toast.LENGTH_SHORT).show();
        }
    }

    private void recibirDatos() {
        Bundle objetoEnviado = getIntent().getExtras();
        usuarioIngreso = (Usuario) objetoEnviado.getSerializable("usuario");
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for (int i = 0; i< listaPerfiles.size();i++){
                int idAux = listaPerfiles.get(i).getIdPerfil() + 1;
                listaInformacion.add(idAux + " - " + listaPerfiles.get(i).getNombre());
            }
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = null;
        listaUsuario = new ArrayList<Usuario>();
        // select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO,null);
        while (cursor.moveToNext()){
            usuario = new Usuario(null,null,null,null,null);
            usuario.setIdUsuario(cursor.getString(0));
            usuario.setContraseña(cursor.getString(1));
            usuario.setNombre(cursor.getString(2));
            usuario.setPais(cursor.getString(4));
            listaUsuario.add(usuario);
        }
        obtenerLista();
    }
}





package uru.crdvp.basededatosblacksheep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uru.crdvp.basededatosblacksheep.entidades.Usuario;

public class DetallePerfil extends AppCompatActivity {

    TextView tvIdUsuario,tvNombreUsu,tvPaisUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_perfil);

        tvIdUsuario = (TextView) findViewById(R.id.tvIdUsuario);
        tvNombreUsu = (TextView) findViewById(R.id.tvNombreUsu);
        tvPaisUsu = (TextView) findViewById(R.id.tvPaisUsu);

        Bundle objetoEnviado = getIntent().getExtras();
        Usuario user = null;
        if(objetoEnviado!=null){
            user = (Usuario) objetoEnviado.getSerializable("usuario");
            tvIdUsuario.setText(user.getIdUsuario().toString());
            tvNombreUsu.setText(user.getNombre().toString());
            tvPaisUsu.setText(user.getPais().toString());
        } else {
            tvIdUsuario.setText("");
            tvNombreUsu.setText("");
            tvPaisUsu.setText("");
            Toast.makeText(getApplicationContext(),"No se recibieron datos!",Toast.LENGTH_SHORT).show();
        }
    }
}
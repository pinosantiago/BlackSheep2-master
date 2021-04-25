package uru.crdvp.basededatosblacksheep;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Movimientos;

public class AdaptadorBalance extends RecyclerView.Adapter<AdaptadorBalance.ViewHolder> {


    ArrayList<Movimientos> listDatos;
    public AdaptadorBalance(ArrayList<Movimientos> listDatos){
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public AdaptadorBalance.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_balance,null,false);
        return new AdaptadorBalance.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull AdaptadorBalance.ViewHolder holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dato;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.textoRv);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @SuppressLint("ResourceAsColor")
        public void asignarDatos(Movimientos s) {
            dato.setText(s.getMonto().toString());
            if (s.getIngEgr().equals("E")){
                dato.setTextColor(Color.parseColor("#c4001d"));

            } else {
                dato.setTextColor(Color.parseColor("#388e3c"));
            }
        }
    }
}


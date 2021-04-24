package uru.crdvp.basededatosblacksheep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorBalance extends RecyclerView.Adapter<AdaptadorBalance.ViewHolder> {


    ArrayList<String> listDatos;
    public AdaptadorBalance(ArrayList<String> listDatos){
        this.listDatos = listDatos;
    }



    @NonNull
    @Override
    public AdaptadorBalance.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_balance,null,false);
        return new AdaptadorBalance.ViewHolder(view);
    }

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

        public void asignarDatos(String s) {
            dato.setText(s);
        }
    }
}

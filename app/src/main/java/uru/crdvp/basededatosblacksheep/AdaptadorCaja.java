package uru.crdvp.basededatosblacksheep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;

public class AdaptadorCaja extends RecyclerView.Adapter<AdaptadorCaja.CajaViewHolder> implements View.OnClickListener {
    ArrayList<Caja> listaCajas;
    View.OnClickListener listener;


    public AdaptadorCaja( ArrayList<Caja> listaCajas ){
        this.listaCajas = listaCajas;
    }

    @NonNull
    @Override
    public CajaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_caja,parent,false);

        CajaViewHolder viewHolder = new CajaViewHolder(view);

        view.setOnClickListener(listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CajaViewHolder holder, int position) {

         holder.asignarDatos(listaCajas.get(position).getNombre()+":",
                 "$ "+listaCajas.get(position).getMonto().toString(),listaCajas.get(position).getPorcentaje().toString()+ "%");
    }

    @Override
    public int getItemCount() {
        return listaCajas.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    class CajaViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCaja, montoCaja, porcCaja;
        public CajaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCaja = itemView.findViewById(R.id.tvNombreCaja);
            montoCaja  = itemView.findViewById(R.id.tvMontoCaja);
            porcCaja   = itemView.findViewById(R.id.tvPorcentajeCaja);
        }

        void asignarDatos(String nombre, String monto, String porcentaje){
            nombreCaja.setText(nombre);
            montoCaja.setText(monto);
            porcCaja.setText(porcentaje);
        }


    }

}

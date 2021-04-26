package uru.crdvp.basededatosblacksheep;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uru.crdvp.basededatosblacksheep.entidades.Caja;
import uru.crdvp.basededatosblacksheep.entidades.Movimientos;

public class AdaptadorPorcentajeCajas extends RecyclerView.Adapter<AdaptadorPorcentajeCajas.ViewHolder> {

    ArrayList<Caja> listaCajas;

    public AdaptadorPorcentajeCajas(ArrayList<Caja> listaCajas) {
        super();
        this.listaCajas = listaCajas;
    }

    @NonNull
    @Override
    public AdaptadorPorcentajeCajas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_porcentaje_caja,null,false);
        return new AdaptadorPorcentajeCajas.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull AdaptadorPorcentajeCajas.ViewHolder holder, int position) {
        holder.asignarDatosCaja(listaCajas.get(position));

        //este metodo se ejecuta cuando se crea el elemento del recicler view,
        // recibe una posicion del elemento en la lsita de objetos y la vista que se crea
        final Caja currentCaja = listaCajas.get(position);
        //obtengo el nombre del objeto en la posicion x
        holder.nombreCaja.setText(currentCaja.getNombre());
        holder.porcentajeCaja.setText(currentCaja.getPorcentaje().toString());
        //le asigno al editext el evento  setOnFocusChangeListener que se ejecutara al momento que
        //el editext pierda o reciba el foco
        holder.nombreCaja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //en mi caso necesito obtener el valor cuando se pierda el foco
                if (!hasFocus) {
                    //ahora edito el objeto en la pocicion que se cambio el texto
                    //y asigno ese nuevo texto a la propiedad nombre del objeto cliente
                    listaCajas.get(position).setPorcentaje(Integer.parseInt(holder.porcentajeCaja.getText().toString().trim()));
                }
            }
        });
        holder.porcentajeCaja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //en mi caso necesito obtener el valor cuando se pierda el foco
                if (!hasFocus) {
                    //ahora edito el objeto en la pocicion que se cambio el texto
                    //y asigno ese nuevo texto a la propiedad nombre del objeto cliente
                    listaCajas.get(position).setPorcentaje(Integer.parseInt(holder.porcentajeCaja.getText().toString().trim()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCajas.size();
    }

    public ArrayList<Caja> devuelvoPorcentajes() {
        return listaCajas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreCaja;
        EditText porcentajeCaja;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCaja     = (TextView) itemView.findViewById(R.id.tvPorcentajeItem);
            porcentajeCaja = (EditText) itemView.findViewById(R.id.edtPorcentajeItem);
        }

        public void asignarDatosCaja(Caja caja) {
            nombreCaja.setText(caja.getNombre()+":");
        }
    }
}

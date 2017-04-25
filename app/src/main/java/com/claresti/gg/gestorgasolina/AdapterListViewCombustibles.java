package com.claresti.gg.gestorgasolina;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListViewCombustibles extends BaseAdapter{

    LayoutInflater inflator;
    ArrayList<ObjCombustible> combustibles;
    Context context;
    RelativeLayout ventana;

    public AdapterListViewCombustibles(Context context, ArrayList<ObjCombustible> combustibles, RelativeLayout ventana) {
        inflator = LayoutInflater.from(context);
        this.combustibles = combustibles;
        this.context = context;
        this.ventana = ventana;
    }

    @Override
    public int getCount() {
        return combustibles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Inicializacion de variables
        convertView = inflator.inflate(R.layout.gasolina, null);
        final TextView color = (TextView)convertView.findViewById(R.id.colorGas);
        final TextView nombre = (TextView)convertView.findViewById(R.id.tipoGas);
        final TextView precio = (TextView)convertView.findViewById(R.id.precio);
        final ImageButton editar = (ImageButton)convertView.findViewById(R.id.editar);
        final ImageButton aceptar = (ImageButton)convertView.findViewById(R.id.aceptar);
        final ImageButton cancelar = (ImageButton)convertView.findViewById(R.id.cancelar);
        final EditText editarPrecio = (EditText)convertView.findViewById(R.id.precioT);
        //asignacion de valores
        color.setBackgroundColor(Color.parseColor(combustibles.get(position).getComColor()));
        nombre.setText(combustibles.get(position).getComNombre());
        precio.setText(Float.toString(combustibles.get(position).getComPrecio()));
        //asignacion de listeners
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar.setVisibility(View.GONE);
                cancelar.setVisibility(View.VISIBLE);
                aceptar.setVisibility(View.VISIBLE);
                editarPrecio.setVisibility(View.VISIBLE);
                editarPrecio.setText(precio.getText());
                precio.setVisibility(View.GONE);
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.GONE);
                aceptar.setVisibility(View.GONE);
                editarPrecio.setVisibility(View.GONE);
                precio.setVisibility(View.VISIBLE);
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("Se actualizo correctamente");
                editar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.GONE);
                aceptar.setVisibility(View.GONE);
                editarPrecio.setVisibility(View.GONE);
                precio.setVisibility(View.VISIBLE);
            }
        });
        //regresa el elemento
        return convertView;
    }

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Snackbar.make(ventana, msg, Snackbar.LENGTH_LONG).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}

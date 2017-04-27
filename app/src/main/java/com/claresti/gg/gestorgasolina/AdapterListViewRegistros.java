package com.claresti.gg.gestorgasolina;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterListViewRegistros extends BaseAdapter{

    LayoutInflater inflator;
    ArrayList<ObjRegistro> registros;
    Context context;

    public AdapterListViewRegistros(Context context, ArrayList<ObjRegistro> registros) {
        inflator = LayoutInflater.from(context);
        this.registros = registros;
        this.context = context;
    }


    @Override
    public int getCount() {
        return registros.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.registro, null);
        //Asignacion de variables del layout
        final TextView color = (TextView)convertView.findViewById(R.id.colorGas);
        final TextView fecha = (TextView)convertView.findViewById(R.id.fecha);
        final TextView dineroLitros = (TextView)convertView.findViewById(R.id.total);
        final TextView kmRecorridos = (TextView)convertView.findViewById(R.id.kilometros);
        final ImageButton editar = (ImageButton)convertView.findViewById(R.id.edit);
        final ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.borrar);
        //Llenado de informacion
        color.setBackgroundColor(Color.parseColor(registros.get(position).getObjCombustible().getComColor()));
        fecha.setText(convertFecha(registros.get(position).getRegFecha()));
        dineroLitros.setText("$" + registros.get(position).getRegDinero() + " - " + registros.get(position).getRegLitros() + " lts.");
        kmRecorridos.setText("" + registros.get(position).getRegKmRecorridos() + "");
        return convertView;
    }

    /**
     * Funcion que regresa el formato que se ostrara de la fecha
     * @param fecha
     * @return String fechaMuestra
     */
    public String convertFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Nobiembre", "Diciembre"};
        String fechaMuestra = dias[calendar.get(Calendar.DAY_OF_WEEK) - 1] + " " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + meses[calendar.get(Calendar.MONTH)] + " del " + calendar.get(Calendar.YEAR);
        return fechaMuestra;
    }
}

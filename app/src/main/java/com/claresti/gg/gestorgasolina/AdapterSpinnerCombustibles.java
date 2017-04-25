package com.claresti.gg.gestorgasolina;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSpinnerCombustibles extends BaseAdapter {

    LayoutInflater inflator;
    ArrayList<ObjCombustible> combustibles;

    public AdapterSpinnerCombustibles(Context context, ArrayList<ObjCombustible> combustibles) {
        inflator = LayoutInflater.from(context);
        this.combustibles = combustibles;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.spinner_combustible, null);
        TextView color = (TextView)convertView.findViewById(R.id.colorGas);
        TextView nombre = (TextView)convertView.findViewById(R.id.tipoGas);
        color.setBackgroundColor(Color.parseColor(combustibles.get(position).getComColor()));
        nombre.setText(combustibles.get(position).getComNombre());
        return convertView;
    }
}

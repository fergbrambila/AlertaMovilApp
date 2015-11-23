package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThexCrack10 on 20-11-2015.
 */
public class Adaptador extends ArrayAdapter {
    Activity context;
    Reporte[] datos;

    public Adaptador(Activity context, Reporte[] datos)
    {
        super(context,R.layout.adaptador_reportes,datos);
        this.datos = datos;
        this.context = context;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater =context.getLayoutInflater();
        View item = inflater.inflate(R.layout.adaptador_reportes, null);

        TextView titulo = (TextView) item.findViewById(R.id.Titulo);
        titulo.setText(datos[position].getTitulo());
        TextView desc = (TextView) item.findViewById(R.id.Desc);
        titulo.setText(datos[position].getDescripcion());
        ImageView imagen = (ImageView) item.findViewById(R.id.ImagenContain);
        imagen.setImageResource(datos[position].getImg());

        return item;
    }
}

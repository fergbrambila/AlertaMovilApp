package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter {
    Activity context;
    ArrayList<Reporte> datos;

    public Adaptador(Activity context, ArrayList<Reporte> datos) {
        super(context, R.layout.adaptador_reportes, datos);
        this.datos = datos;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.adaptador_reportes, null);

        TextView titulo = (TextView) item.findViewById(R.id.Titulo);
        titulo.setText(datos.get(position).getSubTipo());
        TextView ciudad = (TextView) item.findViewById(R.id.Ciudad);
        ciudad.setText(datos.get(position).getCiudad());
        TextView desc = (TextView) item.findViewById(R.id.Desc);
        desc.setText(datos.get(position).getComentario());
        if(datos.get(position).getImg() == 0) {
            datos.get(position).setImg(R.mipmap.alerta);
        }
        ImageView imagen = (ImageView) item.findViewById(R.id.ImagenContain);
        imagen.setImageResource(datos.get(position).getImg());

        return item;
    }
}

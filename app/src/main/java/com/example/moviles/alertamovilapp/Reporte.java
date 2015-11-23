package com.example.moviles.alertamovilapp;

/**
 * Created by ThexCrack10 on 20-11-2015.
 */
public class Reporte {
    private String titulo, descripcion;
    private int img;

    public Reporte()
    {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.img = img;
    }
    public String getTitulo(){
        return titulo;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String getDescripcion(){
        return  descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public  int getImg(){
        return img;
    }
    public void setImg(int img){
        this.img = img;
    }


}

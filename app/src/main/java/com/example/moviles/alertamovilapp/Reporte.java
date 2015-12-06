package com.example.moviles.alertamovilapp;

import java.io.Serializable;

/**
 * clase de objeto de tipo reporte que es serializble parapoder guardar la informacion de los reportes
 * cuando llegan como array
 */
public class Reporte implements Serializable {
    private String fecha;
    private String email;
    private String tipo;
    private double latitud;
    private double longitud;
    private String subTipo;
    private String comentario;
    private String ciudad;
    private String titulo;
    private int img;


    public String getComentario() { return comentario;}

    public void setComentario(String comentario) {this.comentario = comentario;}

    public String getSubTipo() { return subTipo;}

    public void setSubTipo(String subTipo) { this.subTipo = subTipo;}

    public String getFecha() { return fecha;}

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setTitulo(String subTipo) { this.titulo = subTipo;}

    public String getTitulo() { return titulo; }

    public void setImg(int img) { this.img = img; }

    public int getImg() {return img; }

}

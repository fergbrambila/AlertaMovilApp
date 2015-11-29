package com.example.moviles.alertamovilapp.clases;

import java.io.Serializable;

public class Usuario implements Serializable {
private String email;
private String nombre;
private String celular;
private String nombreCiudad;
private String fechaNacimiento;
private String apellido;
private String password;


public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}


public String getNombreCiudad() {
	return nombreCiudad;
}
public void setNombreCiudad(String nombreCiudad) {
	this.nombreCiudad = nombreCiudad;
}

 
}

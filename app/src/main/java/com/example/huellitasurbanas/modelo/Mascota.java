package com.example.huellitasurbanas.modelo;

public class Mascota {
    private String uid;
    private String uidDueno;
    private String nombre;
    private String raza;
    private int edad;
    private String tamaño;
    private String observaciones;
    private int foto;

    public Mascota() {
    }

    public Mascota(String uid, String uidDueno, String nombre, String raza, int edad, String tamaño, String observaciones, int foto) {
        this.uid = uid;
        this.uidDueno = uidDueno;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.tamaño = tamaño;
        this.observaciones = observaciones;
        this.foto = foto;
    }

    public Mascota(String uid, String uidDueno, String nombre, int edad, String raza, String tamaño, String observaciones) {
        this.uid = uid;
        this.uidDueno = uidDueno;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.tamaño = tamaño;
        this.observaciones = observaciones;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidDueno() {
        return uidDueno;
    }

    public void setUidDueno(String uidDueno) {
        this.uidDueno = uidDueno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
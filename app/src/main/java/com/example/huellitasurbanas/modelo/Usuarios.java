package com.example.huellitasurbanas.modelo;

public class Usuarios {
    private String nombre;
    private String uid;
    private String correoElectronico;
    private String rol;
    private int fotoPerfil;
    private String ciudad;
    private double valoracionMedia;

    public Usuarios() {
    }

    public Usuarios(String nombre, String correoElectronico, String rol, int fotoPerfil, String ciudad, double valoracionMedia) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.rol = rol;
        this.fotoPerfil = fotoPerfil;
        this.ciudad = ciudad;
        this.valoracionMedia = valoracionMedia;
    }

    public Usuarios(String nombre, String correoElectronico, int fotoPerfil, String ciudad, double valoracionMedia, String uid) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.fotoPerfil = fotoPerfil;
        this.ciudad = ciudad;
        this.valoracionMedia = valoracionMedia;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getValoracionMedia() {
        return valoracionMedia;
    }

    public void setValoracionMedia(double valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }
}

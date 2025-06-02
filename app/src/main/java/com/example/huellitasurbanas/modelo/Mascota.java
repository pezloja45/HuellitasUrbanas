package com.example.huellitasurbanas.modelo;

/**
 * Representa una mascota registrada por un usuario.
 * Contiene información como nombre, raza, edad, tamaño, observaciones y URL de foto.
 */
public class Mascota {
    private String uid;
    private String uidDueno;
    private String nombre;
    private String raza;
    private int edad;
    private String tamaño;
    private String observaciones;
    private String fotoUrl;

    /** Constructor vacío necesario para Firebase. */
    public Mascota() {
    }

    /**
     * Constructor completo de mascota con todos los atributos.
     *
     * @param uid           Identificador único de la mascota.
     * @param uidDueno      Identificador único del dueño.
     * @param nombre        Nombre de la mascota.
     * @param raza          Raza de la mascota.
     * @param edad          Edad de la mascota (en años).
     * @param tamaño        Tamaño de la mascota (Pequeño, Mediano, Grande).
     * @param observaciones Comentarios u observaciones del dueño.
     * @param fotoUrl       URL de la foto de la mascota.
     */
    public Mascota(String uid, String uidDueno, String nombre, String raza, int edad, String tamaño, String observaciones, String fotoUrl) {
        this.uid = uid;
        this.uidDueno = uidDueno;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.tamaño = tamaño;
        this.observaciones = observaciones;
        this.fotoUrl = fotoUrl;
    }

    /**
     * Constructor sin foto de perfil (opcional para creación).
     */
    public Mascota(String uid, String uidDueno, String nombre, int edad, String raza, String tamaño, String observaciones) {
        this.uid = uid;
        this.uidDueno = uidDueno;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.tamaño = tamaño;
        this.observaciones = observaciones;
    }

    /** @return Identificador único de la mascota. */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /** @return Identificador único del dueño de la mascota. */
    public String getUidDueno() {
        return uidDueno;
    }

    public void setUidDueno(String uidDueno) {
        this.uidDueno = uidDueno;
    }

    /** @return Nombre de la mascota. */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Raza de la mascota. */
    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    /** @return Edad de la mascota. */
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    /** @return Tamaño de la mascota. */
    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    /** @return Observaciones generales sobre la mascota. */
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /** @return URL de la foto de la mascota. */
    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
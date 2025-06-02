package com.example.huellitasurbanas.modelo;

/**
 * Representa un usuario del sistema Huellitas Urbanas.
 * Puede ser dueño de mascotas o tener un rol específico dentro de la aplicación.
 */
public class Usuarios {
    private String nombre;
    private String uid;
    private String correoElectronico;
    private String rol;
    private String fotoPerfil;
    private String ciudad;
    private double valoracionMedia;

    /** Constructor vacío necesario para Firebase. */
    public Usuarios() {}

    /**
     * Constructor para crear un usuario sin UID explícito.
     *
     * @param nombre           Nombre del usuario.
     * @param correoElectronico Correo electrónico del usuario.
     * @param rol              Rol (por ejemplo: "dueño", "cuidador", etc.).
     * @param fotoPerfil       URL de la foto de perfil.
     * @param ciudad           Ciudad de residencia.
     * @param valoracionMedia  Valoración promedio del usuario.
     */
    public Usuarios(String nombre, String correoElectronico, String rol, String fotoPerfil, String ciudad, double valoracionMedia) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.rol = rol;
        this.fotoPerfil = fotoPerfil;
        this.ciudad = ciudad;
        this.valoracionMedia = valoracionMedia;
    }

    /**
     * Constructor para crear un usuario con UID explícito.
     *
     * @param nombre           Nombre del usuario.
     * @param correoElectronico Correo electrónico del usuario.
     * @param fotoPerfil       URL de la foto de perfil.
     * @param ciudad           Ciudad de residencia.
     * @param valoracionMedia  Valoración promedio del usuario.
     * @param uid              Identificador único del usuario.
     */
    public Usuarios(String nombre, String correoElectronico, String fotoPerfil, String ciudad, double valoracionMedia, String uid) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.fotoPerfil = fotoPerfil;
        this.ciudad = ciudad;
        this.valoracionMedia = valoracionMedia;
        this.uid = uid;
    }

    /** @return Identificador único del usuario. */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /** @return Nombre del usuario. */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Correo electrónico del usuario. */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /** @return Rol asignado al usuario. */
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /** @return URL de la foto de perfil. */
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /** @return Ciudad donde reside el usuario. */
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /** @return Valoración promedio del usuario. */
    public double getValoracionMedia() {
        return valoracionMedia;
    }

    public void setValoracionMedia(double valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }
}
package com.example.huellitasurbanas.modelo;

public class ChatItem {
    private Usuarios usuario;
    private String ultimoMensaje;

    public ChatItem(Usuarios usuario, String ultimoMensaje) {
        this.usuario = usuario;
        this.ultimoMensaje = ultimoMensaje;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}
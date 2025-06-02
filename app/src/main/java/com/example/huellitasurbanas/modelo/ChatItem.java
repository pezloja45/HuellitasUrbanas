package com.example.huellitasurbanas.modelo;

/**
 * Modelo que representa un ítem de chat abierto.
 * Contiene información del usuario con el que se conversa y el último mensaje del chat.
 */
public class ChatItem {

    private Usuarios usuario;
    private String ultimoMensaje;

    /**
     * Constructor de la clase ChatItem.
     *
     * @param usuario        Usuario con el que se mantiene el chat.
     * @param ultimoMensaje  Último mensaje enviado o recibido en el chat.
     */
    public ChatItem(Usuarios usuario, String ultimoMensaje) {
        this.usuario = usuario;
        this.ultimoMensaje = ultimoMensaje;
    }

    /**
     * Obtiene el usuario asociado al chat.
     *
     * @return Objeto de tipo {@link Usuarios}.
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el último mensaje del chat.
     *
     * @return Cadena con el último mensaje.
     */
    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}
package com.example.huellitasurbanas.modelo;

/**
 * Representa un mensaje enviado entre usuarios.
 * Incluye información del emisor, receptor, contenido y marca temporal.
 */
public class Message {
    private String senderId;
    private String receiverId;
    private String message;
    private long timestamp;

    /** Constructor vacío necesario para Firebase. */
    public Message() {}

    /**
     * Constructor completo para inicializar un mensaje.
     *
     * @param senderId    ID del usuario que envía el mensaje.
     * @param receiverId  ID del usuario que recibe el mensaje.
     * @param message     Contenido del mensaje.
     * @param timestamp   Marca de tiempo en milisegundos (Unix epoch).
     */
    public Message(String senderId, String receiverId, String message, long timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    /** @return ID del remitente del mensaje. */
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /** @return ID del destinatario del mensaje. */
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /** @return Contenido del mensaje. */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** @return Marca temporal del mensaje (en milisegundos). */
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /** @return Representación legible del mensaje para depuración. */
    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
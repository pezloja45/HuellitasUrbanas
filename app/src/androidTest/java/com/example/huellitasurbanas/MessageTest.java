package com.example.huellitasurbanas;

import static org.junit.Assert.*;

import com.example.huellitasurbanas.modelo.Message;

import org.junit.Test;

public class MessageTest {

    @Test
    public void testGettersSetters() {
        Message mensaje = new Message();
        mensaje.setSenderId("user123");
        mensaje.setReceiverId("user456");
        mensaje.setMessage("Hola, ¿cómo estás?");
        mensaje.setTimestamp(1685836800000L);

        assertEquals("user123", mensaje.getSenderId());
        assertEquals("user456", mensaje.getReceiverId());
        assertEquals("Hola, ¿cómo estás?", mensaje.getMessage());
        assertEquals(1685836800000L, mensaje.getTimestamp());
    }

    @Test
    public void testConstructorCompleto() {
        long timestamp = System.currentTimeMillis();
        Message mensaje = new Message("user123", "user456", "Hola!", timestamp);

        assertEquals("user123", mensaje.getSenderId());
        assertEquals("user456", mensaje.getReceiverId());
        assertEquals("Hola!", mensaje.getMessage());
        assertEquals(timestamp, mensaje.getTimestamp());
    }

    @Test
    public void testToString() {
        Message mensaje = new Message("sender", "receiver", "mensaje", 1000L);
        String esperado = "Message{senderId='sender', receiverId='receiver', message='mensaje', timestamp=1000}";
        assertEquals(esperado, mensaje.toString());
    }
}

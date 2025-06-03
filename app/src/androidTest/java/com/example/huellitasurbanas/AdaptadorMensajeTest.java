package com.example.huellitasurbanas.controlador;

import static org.junit.Assert.assertEquals;

import com.example.huellitasurbanas.modelo.Message;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AdaptadorMensajeTest {

    private AdaptadorMensaje adaptador;
    private String idUsuarioActual = "user123";

    @Before
    public void setUp() {
        Message msg1 = new Message("user123", "Hola!");
        Message msg2 = new Message("otroUser", "Hola, ¿cómo estás?");
        List<Message> mensajes = Arrays.asList(msg1, msg2);
        adaptador = new AdaptadorMensaje(mensajes, idUsuarioActual);
    }

    @Test
    public void testGetItemCount_devuelveCorrecto() {
        assertEquals(2, adaptador.getItemCount());
    }

    @Test
    public void testGetItemViewType_distingueEnviadosYRecibidos() {
        // Primer mensaje es enviado por usuario actual
        assertEquals(1, adaptador.getItemViewType(0));
        // Segundo mensaje es recibido
        assertEquals(0, adaptador.getItemViewType(1));
    }
}
package com.example.huellitasurbanas;

import static org.junit.Assert.assertEquals;

import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Usuarios;

import org.junit.Test;

public class devuelveValoresCorrectos {
    @Test
    public void chatItem_gettersDevuelvenValoresCorrectos() {
        Usuarios usuario = new Usuarios("uid123", "Juan", "urlFoto");
        String mensaje = "Hola, ¿cómo estás?";
        ChatItem chatItem = new ChatItem(usuario, mensaje);

        assertEquals(usuario, chatItem.getUsuario());
        assertEquals(mensaje, chatItem.getUltimoMensaje());
    }

}

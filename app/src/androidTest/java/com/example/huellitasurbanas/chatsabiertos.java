package com.example.huellitasurbanas;

import static org.junit.Assert.assertEquals;

import com.example.huellitasurbanas.controlador.AdaptadorChatsAbiertos;
import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Usuarios;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
public class chatsabiertos {
    private AdaptadorChatsAbiertos adaptador;

    @Before
    public void setUp() {
        Usuarios usuario = new Usuarios();
        usuario.setNombre("Carlos");
        usuario.setFotoPerfil("");

        ChatItem chat = new ChatItem(usuario, "Hola, ¿cómo estás?");
        List<ChatItem> chats = Arrays.asList(chat);

        adaptador = new AdaptadorChatsAbiertos(null, chats, null);
    }

    @Test
    public void testGetItemCount_devuelveCantidadCorrecta() {
        assertEquals(1, adaptador.getItemCount());
    }
}
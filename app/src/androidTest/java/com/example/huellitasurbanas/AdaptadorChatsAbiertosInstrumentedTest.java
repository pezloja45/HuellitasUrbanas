// AdaptadorChatsAbiertosInstrumentedTest.java (src/androidTest/java/)
package com.example.huellitasurbanas.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Usuarios;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AdaptadorChatsAbiertosInstrumentedTest {

    private AdaptadorChatsAbiertos adaptador;
    private boolean[] clickLlamado;
    private boolean[] longClickLlamado;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();

        Usuarios usuario = new Usuarios();
        usuario.setNombre("Ana");
        usuario.setFotoPerfil("");

        ChatItem chat = new ChatItem(usuario, "¡Hola!");

        clickLlamado = new boolean[]{false};
        longClickLlamado = new boolean[]{false};

        AdaptadorChatsAbiertos.OnChatClickListener oyente = new AdaptadorChatsAbiertos.OnChatClickListener() {
            @Override
            public void onChatClick(ChatItem chatItem) {
                clickLlamado[0] = true;
            }

            @Override
            public void onChatLongClick(ChatItem chatItem) {
                longClickLlamado[0] = true;
            }
        };

        adaptador = new AdaptadorChatsAbiertos(context, Collections.singletonList(chat), oyente);
    }

    @Test
    public void testClicEnItem_disparaListener() {
        Context context = ApplicationProvider.getApplicationContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_chat_abierto, null, false);

        AdaptadorChatsAbiertos.ViewHolder viewHolder = new AdaptadorChatsAbiertos.ViewHolder(itemView);
        adaptador.onBindViewHolder(viewHolder, 0);

        viewHolder.itemView.performClick();

        assertEquals(true, clickLlamado[0]);
    }

    @Test
    public void testClicLargoEnItem_disparaListener() {
        Context context = ApplicationProvider.getApplicationContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_chat_abierto, null, false);

        AdaptadorChatsAbiertos.ViewHolder viewHolder = new AdaptadorChatsAbiertos.ViewHolder(itemView);
        adaptador.onBindViewHolder(viewHolder, 0);

        viewHolder.itemView.performLongClick();

        assertEquals(true, longClickLlamado[0]);
    }
}
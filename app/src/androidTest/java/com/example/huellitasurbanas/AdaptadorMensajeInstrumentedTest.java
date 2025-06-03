package com.example.huellitasurbanas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.controlador.AdaptadorMensaje;
import com.example.huellitasurbanas.modelo.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AdaptadorMensajeInstrumentedTest {

    private AdaptadorMensaje adaptador;
    private String idUsuarioActual = "user123";

    @Before
    public void setUp() {
        Message mensaje = new Message(idUsuarioActual, "Mensaje enviado");
        adaptador = new AdaptadorMensaje(Collections.singletonList(mensaje), idUsuarioActual);
    }

    @Test
    public void testOnBindViewHolder_muestraTextoCorrectamente() {
        Context context = ApplicationProvider.getApplicationContext();
        // Simulamos layout de mensaje enviado
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, null, false);
        AdaptadorMensaje.VistaMensaje viewHolder = new AdaptadorMensaje.VistaMensaje(view);

        adaptador.onBindViewHolder(viewHolder, 0);

        assertEquals("Mensaje enviado", viewHolder.textoMensaje.getText().toString());
    }
}
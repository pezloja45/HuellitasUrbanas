// AdaptadorMascotaInstrumentedTest.java (src/androidTest/java/)
package com.example.huellitasurbanas.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Mascota;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AdaptadorMascotaInstrumentedTest {

    private AdaptadorMascota adaptador;

    @Before
    public void setUp() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Toby");
        mascota.setRaza("Beagle");
        mascota.setEdad(2);
        mascota.setFotoUrl("");

        adaptador = new AdaptadorMascota(Collections.singletonList(mascota));
    }

    @Test
    public void testDatosMascota_mostradosCorrectamente() {
        Context context = ApplicationProvider.getApplicationContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardmascota, null, false);
        AdaptadorMascota.ViewHolder viewHolder = new AdaptadorMascota.ViewHolder(itemView);

        adaptador.onBindViewHolder(viewHolder, 0);

        assertEquals("Toby", viewHolder.textoNombre.getText().toString());
        assertEquals("Beagle", viewHolder.textoRaza.getText().toString());
        assertEquals("Edad: 2 años", viewHolder.textoEdad.getText().toString());
    }
}
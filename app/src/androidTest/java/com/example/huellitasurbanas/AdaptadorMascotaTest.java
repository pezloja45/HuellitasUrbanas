// AdaptadorMascotaTest.java (src/test/java/)
package com.example.huellitasurbanas.controlador;

import static org.junit.Assert.assertEquals;

import com.example.huellitasurbanas.modelo.Mascota;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AdaptadorMascotaTest {

    private AdaptadorMascota adaptador;

    @Before
    public void setUp() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setEdad(3);
        mascota.setRaza("Labrador");

        List<Mascota> mascotas = Arrays.asList(mascota);
        adaptador = new AdaptadorMascota(mascotas);
    }

    @Test
    public void testGetItemCount_devuelveCantidadCorrecta() {
        assertEquals(1, adaptador.getItemCount());
    }
}
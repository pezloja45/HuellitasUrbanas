package com.example.huellitasurbanas;

import static org.junit.Assert.assertEquals;

import com.example.huellitasurbanas.controlador.AdaptadorBuscar;
import com.example.huellitasurbanas.modelo.Usuarios;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class devuelveTamaño {
    @Test
    public void testGetItemCount_devuelveTamañoCorrecto() {
        List<Usuarios> usuarios = Arrays.asList(new Usuarios(), new Usuarios(), new Usuarios());
        AdaptadorBuscar adaptador = new AdaptadorBuscar(usuarios, null);
        assertEquals(3, adaptador.getItemCount());
    }

}

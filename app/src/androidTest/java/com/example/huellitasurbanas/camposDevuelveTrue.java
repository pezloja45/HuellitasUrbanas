package com.example.huellitasurbanas;

import static org.junit.Assert.assertTrue;

import com.example.huellitasurbanas.controlador.CrearMascota;

import org.junit.Test;

public class camposDevuelveTrue {
    @Test
    public void validarCampos_ValidosDevuelveTrue() {
        CrearMascota actividad = new CrearMascota();

        boolean resultado = actividad.validarDatos("Firulais", "Raza", "5", "Mediano");
        assertTrue(resultado);
    }
}

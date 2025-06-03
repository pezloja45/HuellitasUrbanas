package com.example.huellitasurbanas;

import static org.junit.Assert.assertFalse;

import com.example.huellitasurbanas.controlador.CrearMascota;

import org.junit.Test;

public class validarEdad {
    @Test
    public void validarEdad_NoNumericaDevuelveFalso() {
        CrearMascota actividad = new CrearMascota();

        boolean resultado = actividad.validarDatos("Firulais", "Raza", "abc", "Mediano");
        assertFalse(resultado);
    }

}

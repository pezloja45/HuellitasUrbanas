package com.example.huellitasurbanas;

import static org.junit.Assert.assertFalse;

import com.example.huellitasurbanas.controlador.CrearMascota;

import org.junit.Test;

public class validarCamposVacios {
    @Test
    public void validarCampos_VaciosDevuelveFalso() {
        CrearMascota actividad = new CrearMascota();

        boolean resultado = actividad.validarDatos("", "Raza", "3", "Mediano");
        assertFalse(resultado);

        resultado = actividad.validarDatos("Firulais", "", "3", "Mediano");
        assertFalse(resultado);

        resultado = actividad.validarDatos("Firulais", "Raza", "", "Mediano");
        assertFalse(resultado);

        resultado = actividad.validarDatos("Firulais", "Raza", "3", "");
        assertFalse(resultado);
    }

}

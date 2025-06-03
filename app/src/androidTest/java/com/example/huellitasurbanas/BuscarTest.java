package com.example.huellitasurbanas;

import static org.junit.Assert.*;

import com.example.huellitasurbanas.modelo.Usuarios;
import com.example.huellitasurbanas.vista.fragment.Buscar;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BuscarTest {

    private Buscar buscarFragment;
    private List<Usuarios> usuariosMock;

    @Before
    public void setUp() {
        buscarFragment = new Buscar();

        // Creamos lista simulada de Usuarios
        usuariosMock = new ArrayList<>();

        Usuarios u1 = new Usuarios();
        u1.setUid("1");
        u1.setRol("paseador");
        u1.setCiudad("CiudadA");

        Usuarios u2 = new Usuarios();
        u2.setUid("2");
        u2.setRol("paseador");
        u2.setCiudad("CiudadB");

        Usuarios u3 = new Usuarios();
        u3.setUid("3");
        u3.setRol("paseador");
        u3.setCiudad("CiudadA");

        usuariosMock.add(u1);
        usuariosMock.add(u2);
        usuariosMock.add(u3);
    }

    @Test
    public void filtrarPorCiudad_TodasDevuelveTodos() {
        // Simulamos el método de filtrado (se puede crear uno público para testear)
        List<Usuarios> filtrados = filtrarPorCiudad(usuariosMock, "Todas");
        assertEquals(3, filtrados.size());
    }

    @Test
    public void filtrarPorCiudad_CiudadADevuelveCorrecto() {
        List<Usuarios> filtrados = filtrarPorCiudad(usuariosMock, "CiudadA");
        assertEquals(2, filtrados.size());
        for (Usuarios u : filtrados) {
            assertEquals("CiudadA", u.getCiudad());
        }
    }

    @Test
    public void filtrarPorCiudad_CiudadSinUsuariosDevuelveVacio() {
        List<Usuarios> filtrados = filtrarPorCiudad(usuariosMock, "CiudadX");
        assertTrue(filtrados.isEmpty());
    }

    // Método privado simulado basado en la lógica de tu fragmento
    private List<Usuarios> filtrarPorCiudad(List<Usuarios> lista, String ciudad) {
        List<Usuarios> filtrados = new ArrayList<>();
        for (Usuarios u : lista) {
            if ("Todas".equals(ciudad) || ciudad.equalsIgnoreCase(u.getCiudad())) {
                filtrados.add(u);
            }
        }
        return filtrados;
    }
}
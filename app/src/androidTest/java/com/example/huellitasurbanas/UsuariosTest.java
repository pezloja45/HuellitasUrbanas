package com.example.huellitasurbanas;

import static org.junit.Assert.*;
import org.junit.Test;

import com.example.huellitasurbanas.modelo.Usuarios;

public class UsuariosTest {

    @Test
    public void testConstructorVacioYSettersGetters() {
        Usuarios usuario = new Usuarios();

        usuario.setUid("uid123");
        usuario.setNombre("Juan Perez");
        usuario.setCorreoElectronico("juan@example.com");
        usuario.setRol("dueño");
        usuario.setFotoPerfil("http://foto.com/juan.jpg");
        usuario.setCiudad("CiudadX");
        usuario.setValoracionMedia(4.5);

        assertEquals("uid123", usuario.getUid());
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("juan@example.com", usuario.getCorreoElectronico());
        assertEquals("dueño", usuario.getRol());
        assertEquals("http://foto.com/juan.jpg", usuario.getFotoPerfil());
        assertEquals("CiudadX", usuario.getCiudad(), "CiudadX");
        assertEquals(4.5, usuario.getValoracionMedia(), 0.001);
    }

    @Test
    public void testConstructorSinUid() {
        Usuarios usuario = new Usuarios(
                "Maria Lopez",
                "maria@example.com",
                "cuidador",
                "http://foto.com/maria.jpg",
                "CiudadY",
                3.8
        );

        assertEquals("Maria Lopez", usuario.getNombre());
        assertEquals("maria@example.com", usuario.getCorreoElectronico());
        assertEquals("cuidador", usuario.getRol());
        assertEquals("http://foto.com/maria.jpg", usuario.getFotoPerfil());
        assertEquals("CiudadY", usuario.getCiudad());
        assertEquals(3.8, usuario.getValoracionMedia(), 0.001);
        assertNull(usuario.getUid());
    }

    @Test
    public void testConstructorConUid() {
        Usuarios usuario = new Usuarios(
                "Pedro Gómez",
                "pedro@example.com",
                "http://foto.com/pedro.jpg",
                "CiudadZ",
                5.0,
                "uid999"
        );

        assertEquals("Pedro Gómez", usuario.getNombre());
        assertEquals("pedro@example.com", usuario.getCorreoElectronico());
        assertNull(usuario.getRol()); // porque en este constructor no se asigna rol
        assertEquals("http://foto.com/pedro.jpg", usuario.getFotoPerfil());
        assertEquals("CiudadZ", usuario.getCiudad());
        assertEquals(5.0, usuario.getValoracionMedia(), 0.001);
        assertEquals("uid999", usuario.getUid());
    }
}
package com.example.huellitasurbanas;

import static org.junit.Assert.*;

import com.example.huellitasurbanas.modelo.Mascota;

import org.junit.Test;

public class MascotaTest {

    @Test
    public void testGettersSetters() {
        Mascota mascota = new Mascota();
        mascota.setUid("123");
        mascota.setUidDueno("456");
        mascota.setNombre("Firulais");
        mascota.setRaza("Labrador");
        mascota.setEdad(3);
        mascota.setTamaño("Mediano");
        mascota.setObservaciones("Muy juguetón");
        mascota.setFotoUrl("http://foto.com/firulais.jpg");

        assertEquals("123", mascota.getUid());
        assertEquals("456", mascota.getUidDueno());
        assertEquals("Firulais", mascota.getNombre());
        assertEquals("Labrador", mascota.getRaza());
        assertEquals(3, mascota.getEdad());
        assertEquals("Mediano", mascota.getTamaño());
        assertEquals("Muy juguetón", mascota.getObservaciones());
        assertEquals("http://foto.com/firulais.jpg", mascota.getFotoUrl());
    }

    @Test
    public void testConstructorCompleto() {
        Mascota mascota = new Mascota("123", "456", "Firulais", "Labrador", 3, "Mediano", "Muy juguetón", "http://foto.com/firulais.jpg");

        assertEquals("123", mascota.getUid());
        assertEquals("456", mascota.getUidDueno());
        assertEquals("Firulais", mascota.getNombre());
        assertEquals("Labrador", mascota.getRaza());
        assertEquals(3, mascota.getEdad());
        assertEquals("Mediano", mascota.getTamaño());
        assertEquals("Muy juguetón", mascota.getObservaciones());
        assertEquals("http://foto.com/firulais.jpg", mascota.getFotoUrl());
    }

    @Test
    public void testConstructorSinFoto() {
        Mascota mascota = new Mascota("123", "456", "Firulais", 3, "Labrador", "Mediano", "Muy juguetón");

        assertEquals("123", mascota.getUid());
        assertEquals("456", mascota.getUidDueno());
        assertEquals("Firulais", mascota.getNombre());
        assertEquals("Labrador", mascota.getRaza());
        assertEquals(3, mascota.getEdad());
        assertEquals("Mediano", mascota.getTamaño());
        assertEquals("Muy juguetón", mascota.getObservaciones());
        assertNull(mascota.getFotoUrl());
    }
}
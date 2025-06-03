// Archivo: MainScreenDuenoTest.java
package com.example.huellitasurbanas;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.vista.MainScreenDueno;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainScreenDuenoTest {

    @Test
    public void testActivityLoadsAndDefaultFragmentIsChat() {
        // Lanzar la actividad
        ActivityScenario.launch(MainScreenDueno.class);

        // Verificar que el BottomNavigationView está visible
        Espresso.onView(withId(R.id.bnv_dueno))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verificar que el fragment Chat está cargado -
        // asumiendo que tiene alguna vista con id R.id.chat_fragment_root
        Espresso.onView(withId(R.id.chat_fragment_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNavigationToBuscarFragment() {
        ActivityScenario.launch(MainScreenDueno.class);

        // Click en el item Buscar
        Espresso.onView(withId(R.id.bnt_buscarDueno))
                .perform(click());

        // Verificar que el fragment Buscar está visible
        Espresso.onView(withId(R.id.buscar_fragment_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNavigationToMascotasFragment() {
        ActivityScenario.launch(MainScreenDueno.class);

        // Click en el item Mascotas
        Espresso.onView(withId(R.id.bnt_mascotasDueno))
                .perform(click());

        // Verificar que el fragment Mascotas está visible
        Espresso.onView(withId(R.id.mascotas_fragment_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNavigationToPerfilFragment() {
        ActivityScenario.launch(MainScreenDueno.class);

        // Click en el item Perfil
        Espresso.onView(withId(R.id.btn_perfilDueno))
                .perform(click());

        // Verificar que el fragment Perfil está visible
        Espresso.onView(withId(R.id.perfil_fragment_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
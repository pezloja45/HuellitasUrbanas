package com.example.huellitasurbanas;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.vista.MainScreenPaseador;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainScreenPaseadorTest {

    @Test
    public void testActivityLoadsAndDefaultFragmentIsChatPaseador() {
        ActivityScenario.launch(MainScreenPaseador.class);

        // Verifica que el BottomNavigationView se muestra
        Espresso.onView(withId(R.id.bnv_paseador))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verifica que el fragment ChatPaseador está visible
        Espresso.onView(withId(R.id.chatPaseador_fragment_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNavigationToPerfilPaseadorFragment() {
        ActivityScenario.launch(MainScreenPaseador.class);

        // Click en el item Perfil
        Espresso.onView(withId(R.id.btn_paseadorPerfil))
                .perform(click());

        // Verifica que el fragment PerfilPaseador está visible
        Espresso.onView(withId(R.id.perfilPaseador_fragment_root))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
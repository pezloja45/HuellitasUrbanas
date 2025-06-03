package com.example.huellitasurbanas;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.regex.Pattern.matches;

import org.junit.Test;

public class adaptadorMuestra {
    @Test
    public void testRecyclerViewMuestraDatos() {
        // Lanzar la actividad que contiene el RecyclerView
        onView(withId(R.id.recyclerViewPaseadores))
                .check(matches(isDisplayed()));

        // Verificar que el primer item contiene el texto esperado
        onView(withText("Nombre Esperado")).check(matches(isDisplayed()));
        onView(withText("Ciudad Esperada")).check(matches(isDisplayed()));
    }

}

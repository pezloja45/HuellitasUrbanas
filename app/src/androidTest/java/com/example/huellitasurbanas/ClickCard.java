package com.example.huellitasurbanas;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Test;

public class ClickCard {
    @Test
    public void testClickEnItemLlamaListener() {
        // Simular clic sobre el primer item del RecyclerView
        onView(withId(R.id.recyclerViewPaseadores))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Verificar que la acción esperada ocurrió (por ejemplo, abrir una nueva pantalla)
        onView(withId(R.id.pantallaDetallePaseador)).check(matches(isDisplayed()));
    }

}

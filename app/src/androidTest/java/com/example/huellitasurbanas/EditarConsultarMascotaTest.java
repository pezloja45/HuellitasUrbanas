package com.example.huellitasurbanas;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditarConsultarMascotaTest {

    private static final String TEST_UID = "mascotaTest123";

    @Before
    public void setup() {
        // Preparar el intent para abrir la actividad con el UID de mascota
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.putExtra("uidMascota", TEST_UID);

        ActivityScenario.launch(EditarConsultarMascota.class, intent.getExtras());
    }

    @Test
    public void testEdadInvalidaShowsToast() {
        // Escribe texto inválido en campo edad
        Espresso.onView(ViewMatchers.withId(R.id.edit_edad))
                .perform(ViewActions.clearText(), ViewActions.typeText("abc"), ViewActions.closeSoftKeyboard());

        // Click en botón actualizar
        Espresso.onView(ViewMatchers.withId(R.id.btn_actualizar_mascota))
                .perform(ViewActions.click());

        // Verificar que se muestra el Toast "Edad no válida"
        Espresso.onView(ViewMatchers.withText("Edad no válida"))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testActualizarSinCambiarImagen() {
        // Cambiar algún campo
        Espresso.onView(ViewMatchers.withId(R.id.edit_nombre))
                .perform(ViewActions.clearText(), ViewActions.typeText("NuevoNombre"), ViewActions.closeSoftKeyboard());

        // Click en actualizar
        Espresso.onView(ViewMatchers.withId(R.id.btn_actualizar_mascota))
                .perform(ViewActions.click());

        // Se espera un Toast de éxito (puedes cambiar el texto si lo modificaste)
        Espresso.onView(ViewMatchers.withText("Mascota actualizada correctamente"))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
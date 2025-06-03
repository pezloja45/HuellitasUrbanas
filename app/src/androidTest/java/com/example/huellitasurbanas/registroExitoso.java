package com.example.huellitasurbanas;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static java.util.EnumSet.allOf;
import static java.util.function.Predicate.not;
import static java.util.regex.Pattern.matches;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.controlador.CrearMascota;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class registroExitoso {

        @Rule
        public ActivityScenarioRule<CrearMascota> activityRule =
                new ActivityScenarioRule<>(CrearMascota.class);

        @Test
        public void alCrearMascota_ConDatosValidos_MuestraToastExitoYCierra() {
            onView(withId(R.id.str_nombreMascota)).perform(typeText("Firulais"), closeSoftKeyboard());
            onView(withId(R.id.str_raza)).perform(typeText("Golden Retriever"), closeSoftKeyboard());
            onView(withId(R.id.str_eddad)).perform(typeText("3"), closeSoftKeyboard());
            onView(withId(R.id.spinner_tamaño)).perform(click());
            onData(allOf(is(instanceOf(String.class)), is("Mediano"))).perform(click());

            onView(withId(R.id.btn_crearMascota)).perform(click());

            // Verifica que se muestre el toast con mensaje esperado
            onView(withText("Mascota registrada con éxito"))
                    .inRoot(withDecorView(not(is(getActivity(activityRule).getWindow().getDecorView()))))
                    .check(matches(isDisplayed()));

            // Aquí podrías verificar que la actividad se cierra, si tienes un idling o un listener
        }
    }

}

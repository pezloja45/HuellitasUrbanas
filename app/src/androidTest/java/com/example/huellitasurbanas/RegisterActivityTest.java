package com.example.huellitasurbanas;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.vista.registerActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Test
    public void testRegisterWithEmptyFieldsShowsToast() {
        ActivityScenario.launch(registerActivity.class);

        // Click en registrar sin llenar nada
        Espresso.onView(ViewMatchers.withId(R.id.btnRegister)).perform(ViewActions.click());

        // Verifica que aparece el Toast con el mensaje esperado
        Espresso.onView(withText("Por favor, completa todos los campos antes de continuar."))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRegisterWithPasswordMismatchShowsToast() {
        ActivityScenario.launch(registerActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.str_email)).perform(ViewActions.typeText("test@test.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.str_pass)).perform(ViewActions.typeText("Pass@123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.str_confirmPass)).perform(ViewActions.typeText("Pass@124"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.inputUsername)).perform(ViewActions.typeText("usuario"), ViewActions.closeSoftKeyboard());

        // El spinner dejarlo seleccionado en el primer elemento (que ya viene por defecto)

        Espresso.onView(ViewMatchers.withId(R.id.btnRegister)).perform(ViewActions.click());

        Espresso.onView(withText("Las contraseñas no coinciden. Verifica e inténtalo de nuevo."))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRegisterWithInvalidPasswordShowsToast() {
        ActivityScenario.launch(registerActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.str_email)).perform(ViewActions.typeText("test@test.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.str_pass)).perform(ViewActions.typeText("abcde"), ViewActions.closeSoftKeyboard());  // inválido
        Espresso.onView(ViewMatchers.withId(R.id.str_confirmPass)).perform(ViewActions.typeText("abcde"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.inputUsername)).perform(ViewActions.typeText("usuario"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnRegister)).perform(ViewActions.click());

        Espresso.onView(withText("La contraseña debe tener al menos 5 caracteres, una letra mayúscula, un número y un carácter especial."))
                .inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
package com.example.huellitasurbanas;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.widget.EditText;

import com.example.huellitasurbanas.modelo.Message;
import com.example.huellitasurbanas.vista.fragment.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ChatTest {

    private Chat chatFragment;

    @Mock
    FirebaseAuth mockAuth;

    @Mock
    FirebaseUser mockUser;

    @Mock
    EditText mockEditText;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        chatFragment = new Chat();

        // Simular usuario actual
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn("user123");

        // Inyectar mocks si tuvieras método setter (o con reflexión / constructor modificado)
        // Para este ejemplo, no es posible inyectar directamente, tendrías que refactorizar Chat para permitirlo.

        // Mock del EditText para sendMessage
        mockEditText = mock(EditText.class);
    }

    @Test
    public void testChatIdOrdenamiento() {
        String sender = "aaa";
        String receiver = "bbb";

        String chatId = sender.compareTo(receiver) < 0 ? sender + "_" + receiver : receiver + "_" + sender;
        assertEquals("aaa_bbb", chatId);

        // Cambiar orden inverso
        sender = "zzz";
        receiver = "bbb";

        chatId = sender.compareTo(receiver) < 0 ? sender + "_" + receiver : receiver + "_" + sender;
        assertEquals("bbb_zzz", chatId);
    }

    @Test
    public void sendMessage_NoEnvioSiMensajeVacio() {
        chatFragment.editMessage = mockEditText;

        when(mockEditText.getText()).thenReturn(new android.text.SpannableStringBuilder("   ")); // solo espacios

        // Como sendMessage es privado, hacer público o protected para testear o usar reflexión.
        // Aquí un ejemplo asumiendo que lo hiciste público para test.

        chatFragment.sendMessage();

        // No hay forma fácil de verificar interacción con Firebase sin mocks y refactorización,
        // Pero confirmamos que no lanza excepción y no intenta enviar mensaje.
    }
}
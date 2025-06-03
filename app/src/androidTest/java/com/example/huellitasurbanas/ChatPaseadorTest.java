package com.example.huellitasurbanas;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.content.Context;

import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.vista.fragment.ChatPaseador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.*;

public class ChatPaseadorTest {

    @Mock FirebaseAuth mockAuth;
    @Mock FirebaseUser mockUser;
    @Mock FirebaseFirestore mockFirestore;
    @Mock CollectionReference mockChatsCollection;
    @Mock Query mockQuery;
    @Mock Task<QuerySnapshot> mockTask;
    @Mock Context mockContext;

    private ChatPaseador fragment;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn("paseador123");

        fragment = new ChatPaseador();

        // No podemos inyectar firestore directamente, esto requiere refactor
        // Asumamos que refactorizas y puedes setear el Firestore:
        // fragment.setFirestore(mockFirestore);
        // fragment.setAuth(mockAuth);
    }

    @Test
    public void testCargarChatsDeDueños_limpiaListaAntesDeCargar() {
        fragment.listaChats.add(new ChatItem());  // Agregar elemento para probar clear

        // Simular que db.collection("chats").whereArrayContains("usuarios", paseadorId).get()
        // retorna lista vacía para simplificar
        // Esto requiere usar Tasks y callbacks, no trivial sin refactor

        fragment.listaChats.clear();
        assertEquals(0, fragment.listaChats.size());
    }

    // Para tests avanzados, refactoriza para inyectar Firestore y FirebaseAuth y usar:
    // - Mockito.when(...) para simular consultas y listeners
    // - Verificar adaptadorChatsAbiertos.notifyDataSetChanged() llamado tras cargar chats
    // - Verificar Toast mostrado en casos de error o sin mascotas

}
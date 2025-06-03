package com.example.huellitasurbanas;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PerfilPaseadorTest {

    @Mock FirebaseAuth mockAuth;
    @Mock FirebaseUser mockUser;
    @Mock FirebaseFirestore mockFirestore;
    @Mock CollectionReference mockCollection;
    @Mock DocumentReference mockDocRef;
    @Mock Task<DocumentSnapshot> mockTask;
    @Mock DocumentSnapshot mockDocSnapshot;

    PerfilPaseador perfilPaseador;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        perfilPaseador = new PerfilPaseador();
        perfilPaseador.mAuth = mockAuth;
        perfilPaseador.db = mockFirestore;
    }

    @Test
    public void cargarDatosUsuario_noUsuario_errorCallback() {
        when(mockAuth.getCurrentUser()).thenReturn(null);

        perfilPaseador.cargarDatosUsuario(new PerfilPaseador.FirestoreCallback() {
            @Override
            public void onSuccess(Usuarios usuario) {
                fail("No debe llamar onSuccess");
            }
            @Override
            public void onError(String errorMessage) {
                assertEquals("Usuario no autenticado", errorMessage);
            }
        });
    }

    @Test
    public void cargarDatosUsuario_usuarioExiste_callbackSuccess() {
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn("uid123");

        when(mockFirestore.collection("usuarios")).thenReturn(mockCollection);
        when(mockCollection.document("uid123")).thenReturn(mockDocRef);
        when(mockDocRef.get()).thenReturn(mockTask);

        Usuarios usuarioMock = new Usuarios();
        usuarioMock.setNombre("Carlos");
        usuarioMock.setCorreoElectronico("carlos@mail.com");

        doAnswer(invocation -> {
            OnSuccessListener<DocumentSnapshot> listener = invocation.getArgument(0);
            listener.onSuccess(mockDocSnapshot);
            return null;
        }).when(mockTask).addOnSuccessListener(any());

        when(mockDocSnapshot.toObject(Usuarios.class)).thenReturn(usuarioMock);

        perfilPaseador.cargarDatosUsuario(new PerfilPaseador.FirestoreCallback() {
            @Override
            public void onSuccess(Usuarios usuario) {
                assertNotNull(usuario);
                assertEquals("Carlos", usuario.getNombre());
            }
            @Override
            public void onError(String errorMessage) {
                fail("No debe llamar onError");
            }
        });
    }
}
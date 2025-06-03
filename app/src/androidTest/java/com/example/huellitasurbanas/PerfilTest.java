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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PerfilTest {

    @Mock
    FirebaseAuth mockAuth;

    @Mock
    FirebaseUser mockUser;

    @Mock
    FirebaseFirestore mockFirestore;

    @Mock
    DocumentReference mockDocRef;

    @Mock
    Task<DocumentSnapshot> mockTask;

    @Mock
    DocumentSnapshot mockDocumentSnapshot;

    Perfil perfil;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        perfil = new Perfil();

        // Inyectar mocks
        perfil.mAuth = mockAuth;
        perfil.db = mockFirestore;
    }

    @Test
    public void cargarDatosUsuario_usuarioNoAutenticado_callbackError() {
        when(mockAuth.getCurrentUser()).thenReturn(null);

        perfil.cargarDatosUsuario(new Perfil.FirestoreCallback() {
            @Override
            public void onSuccess(Usuarios usuario) {
                fail("No debería llamar a onSuccess");
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
        when(mockUser.getUid()).thenReturn("123");

        when(mockFirestore.collection("usuarios")).thenReturn(mock(CollectionReference.class));
        CollectionReference collection = mockFirestore.collection("usuarios");
        when(collection.document("123")).thenReturn(mockDocRef);
        when(mockDocRef.get()).thenReturn(mockTask);

        Usuarios usuarioMock = new Usuarios();
        usuarioMock.setNombre("Juan");
        usuarioMock.setCorreoElectronico("juan@mail.com");

        // Simular get success
        doAnswer(invocation -> {
            OnSuccessListener<DocumentSnapshot> listener = invocation.getArgument(0);
            listener.onSuccess(mockDocumentSnapshot);
            return null;
        }).when(mockTask).addOnSuccessListener(any());

        when(mockDocumentSnapshot.exists()).thenReturn(true);
        when(mockDocumentSnapshot.toObject(Usuarios.class)).thenReturn(usuarioMock);

        perfil.cargarDatosUsuario(new Perfil.FirestoreCallback() {
            @Override
            public void onSuccess(Usuarios usuario) {
                assertNotNull(usuario);
                assertEquals("Juan", usuario.getNombre());
            }

            @Override
            public void onError(String errorMessage) {
                fail("No debería llamar a onError");
            }
        });
    }
}
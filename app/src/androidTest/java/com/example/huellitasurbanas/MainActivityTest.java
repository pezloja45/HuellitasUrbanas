package com.example.huellitasurbanas;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.example.huellitasurbanas.vista.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class MainActivityTest {

    @Mock FirebaseAuth mockAuth;
    @Mock FirebaseUser mockUser;
    @Mock FirebaseFirestore mockFirestore;
    @Mock CollectionReference mockCollection;
    @Mock DocumentReference mockDocRef;
    @Mock Task<AuthResult> mockAuthTask;
    @Mock Task<DocumentSnapshot> mockDocTask;
    @Mock DocumentSnapshot mockDocSnapshot;

    MainActivity mainActivity;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mainActivity = new MainActivity();

        // Inject mocks into MainActivity fields (puedes hacer setter o cambiar a package private)
        mainActivity.mAuth = mockAuth;
        mainActivity.db = mockFirestore;
    }

    @Test
    public void loginUser_loginFails_showsError() {
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthTask);

        doAnswer(invocation -> {
            OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
            // Simular fallo de login
            listener.onComplete(mockAuthTask);
            when(mockAuthTask.isSuccessful()).thenReturn(false);
            when(mockAuthTask.getException()).thenReturn(new Exception("Credenciales incorrectas"));
            return null;
        }).when(mockAuthTask).addOnCompleteListener(any());

        mainActivity.loginUser("email@mail.com", "wrongPass");

        // Aquí podrías verificar si el Toast o método de UI para error fue llamado, usando un wrapper o un spy.
    }

    @Test
    public void loginUser_loginSuccess_userHasRole_redirects() {
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthTask);
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn("uid123");

        when(mockFirestore.collection("usuarios")).thenReturn(mockCollection);
        when(mockCollection.document("uid123")).thenReturn(mockDocRef);
        when(mockDocRef.get()).thenReturn(mockDocTask);

        doAnswer(invocation -> {
            OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
            when(mockAuthTask.isSuccessful()).thenReturn(true);
            listener.onComplete(mockAuthTask);
            return null;
        }).when(mockAuthTask).addOnCompleteListener(any());

        doAnswer(invocation -> {
            OnSuccessListener<DocumentSnapshot> listener = invocation.getArgument(0);
            when(mockDocSnapshot.exists()).thenReturn(true);
            when(mockDocSnapshot.getString("rol")).thenReturn("paseador");
            listener.onSuccess(mockDocSnapshot);
            return null;
        }).when(mockDocTask).addOnSuccessListener(any());

        mainActivity.loginUser("email@mail.com", "correctPass");

        // Verifica que se haya llamado el método redirigirPorRol("paseador") o que la intent se haya lanzado.
    }
}
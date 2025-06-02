package com.example.huellitasurbanas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class registerActivity extends AppCompatActivity {

    // Elementos UI
    TextView str_tieneCuenta;
    Button btn_registrar;
    EditText str_username, str_email, str_pass, str_confirmPass;
    Spinner spinnerCiudad;

    // Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    // URL imagen perfil por defecto
    private static final String FOTO_PERFIL_POR_DEFECTO = "https://firebasestorage.googleapis.com/v0/b/huellitasurbanas-c6820.appspot.com/o/defaultuser.jfif?alt=media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Obtener referencias a los elementos de UI
        str_username = findViewById(R.id.inputUsername);
        str_email = findViewById(R.id.str_email);
        str_pass = findViewById(R.id.str_pass);
        str_confirmPass = findViewById(R.id.str_confirmPass);
        btn_registrar = findViewById(R.id.btnRegister);
        str_tieneCuenta = findViewById(R.id.alreadyHaveAccount);
        spinnerCiudad = findViewById(R.id.spinner_ciudad);

        // Configurar spinner con lista de ciudades del recurso 'ciudades_es'
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.ciudades_es,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(adapter);

        // Acción para ir a la pantalla de login si ya tiene cuenta
        str_tieneCuenta.setOnClickListener(v ->
                startActivity(new Intent(registerActivity.this, MainActivity.class))
        );

        // Listener para botón de registro
        btn_registrar.setOnClickListener(v -> {
            String email = str_email.getText().toString().trim();
            String pass = str_pass.getText().toString().trim();
            String confirmPass = str_confirmPass.getText().toString().trim();
            String username = str_username.getText().toString().trim();
            String city = spinnerCiudad.getSelectedItem().toString();

            // Validaciones básicas
            if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || username.isEmpty() || city.isEmpty()) {
                Toast.makeText(registerActivity.this, "Por favor, completa todos los campos antes de continuar.", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirmPass)) {
                Toast.makeText(registerActivity.this, "Las contraseñas no coinciden. Verifica e inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
            } else if (!isPasswordValid(pass)) {
                Toast.makeText(registerActivity.this, "La contraseña debe tener al menos 5 caracteres, una letra mayúscula, un número y un carácter especial.", Toast.LENGTH_LONG).show();
            } else {
                // Registrar usuario en Firebase
                registerUser(email, pass, username, city);
            }
        });
    }

    /**
     * Registra un usuario nuevo con correo y contraseña.
     * Luego guarda el usuario en Firestore con datos adicionales.
     *
     * @param email    correo electrónico
     * @param pass     contraseña
     * @param username nombre de usuario
     * @param ciudad   ciudad seleccionada
     */
    private void registerUser(String email, String pass, String username, String ciudad) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                Usuarios nuevoUsuario = new Usuarios(
                        username,
                        email,
                        FOTO_PERFIL_POR_DEFECTO, // Foto por defecto
                        ciudad,
                        0.0,    // rating inicial o cualquier otro dato que uses
                        uid
                );

                // Guardar usuario en Firestore
                db.collection("usuarios").document(uid)
                        .set(nuevoUsuario)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(registerActivity.this, "Registro exitoso. ¡Bienvenido!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registerActivity.this, MainActivity.class)); // Ir a login o home
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(registerActivity.this, "Error al guardar los datos del usuario en Firestore: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });

            } else {
                Toast.makeText(registerActivity.this, "No se pudo registrar el usuario: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(registerActivity.this, "Se produjo un error inesperado al registrar: " + e.getMessage(), Toast.LENGTH_LONG).show()
        );
    }

    /**
     * Valida que la contraseña cumpla el patrón:
     * Al menos 5 caracteres, una letra mayúscula, un número y un carácter especial.
     *
     * @param password contraseña a validar
     * @return true si es válida, false si no
     */
    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,}$";
        return password.matches(passwordPattern);
    }
}
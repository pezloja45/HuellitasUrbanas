package com.example.huellitasurbanas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.huellitasurbanas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    TextView str_crearCuenta;
    EditText str_email, str_pass;
    Button btn_login;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        str_email = findViewById(R.id.str_emailInicio);
        str_pass = findViewById(R.id.str_pass);
        btn_login = findViewById(R.id.btn_login);
        str_crearCuenta = findViewById(R.id.str_crearCuenta);

        str_crearCuenta.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,registerActivity.class)));

        btn_login.setOnClickListener(v -> {
            String email = str_email.getText().toString();
            String pass = str_pass.getText().toString();

            if (email.isEmpty() && pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, pass);
            }
        });
    }

    private void loginUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        db.collection("usuarios").document(uid).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        String rol = documentSnapshot.getString("rol");
                                        if (rol == null || rol.isEmpty()) {
                                            mostrarDialogoDeRol(uid);
                                        } else {
                                            redirigirPorRol(rol);
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "No se encontraron datos del usuario en la base de datos.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(MainActivity.this, "Error al obtener información del usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    } else {
                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos. Intenta nuevamente.", Toast.LENGTH_LONG).show();
                        Log.e("LoginError", "Fallo al iniciar sesión: ", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error de conexión o fallo inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void mostrarDialogoDeRol(String uid) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Selecciona tu rol")
                .setMessage("¿Con qué rol deseas continuar?")
                .setPositiveButton("Paseador", (dialog, which) -> {
                    db.collection("usuarios").document(uid)
                            .update("rol", "paseador")
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(MainActivity.this, "Rol asignado: Paseador", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, MainScreenPaseador.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(MainActivity.this, "Error al guardar el rol: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                })
                .setNegativeButton("Dueño", (dialog, which) -> {
                    db.collection("usuarios").document(uid)
                            .update("rol", "dueño")
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(MainActivity.this, "Rol asignado: Dueño de mascota", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, MainScreenDueno.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(MainActivity.this, "Error al guardar el rol: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void redirigirPorRol(String rol) {
        if (rol.equalsIgnoreCase("paseador")) {
            Toast.makeText(MainActivity.this, "Bienvenido, paseador.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainScreenPaseador.class));
        } else if (rol.equalsIgnoreCase("dueño")) {
            Toast.makeText(MainActivity.this, "Bienvenido, dueño de mascota.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainScreenDueno.class));
        } else {
            Toast.makeText(MainActivity.this, "Rol no reconocido: " + rol, Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
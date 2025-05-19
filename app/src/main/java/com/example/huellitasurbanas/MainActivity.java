package com.example.huellitasurbanas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
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

        str_email = findViewById(R.id.str_ciudad);
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
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                db.collection("usuarios").document(email).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String rol = documentSnapshot.getString("rol");

                                if (rol == null || rol.isEmpty()) {
                                    // Mostrar diálogo, y solo avanzar después de la selección
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("Escoge tu rol")
                                            .setMessage("¿Eres paseador o dueño de mascota?")
                                            .setPositiveButton("Paseador", (dialog, which) -> {
                                                db.collection("usuarios").document(email)
                                                        .update("rol", "paseador")
                                                        .addOnSuccessListener(unused -> {
                                                            startActivity(new Intent(MainActivity.this, MainScreenPaseador.class));
                                                            finish();
                                                        });
                                            })
                                            .setNegativeButton("Dueño", (dialog, which) -> {
                                                db.collection("usuarios").document(email)
                                                        .update("rol", "dueño")
                                                        .addOnSuccessListener(unused -> {
                                                            startActivity(new Intent(MainActivity.this, PantallaPrincipal.class));
                                                            finish();
                                                        });
                                            })
                                            .setCancelable(false) // evita que el usuario cierre el diálogo sin elegir
                                            .create()
                                            .show();

                                } else {
                                    // Si ya tiene rol, entrar directamente
                                    startActivity(new Intent(MainActivity.this, PantallaPrincipal.class));
                                    finish();
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Usuario no encontrado en Firestore", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity.this, "Error al leer datos del usuario", Toast.LENGTH_SHORT).show();
                        });

            } else {
                Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
        });
    }

}
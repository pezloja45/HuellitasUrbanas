package com.example.huellitasurbanas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView str_crearCuenta;
    EditText str_email, str_pass;
    Button btn_login;

    //Debo registrarlo en shared prefs para que guarde siempre si fue primera vez o no
    boolean ifFirstTime;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        str_email = findViewById(R.id.str_email);
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
                startActivity(new Intent(MainActivity.this,PantallaPrincipal.class));
            } else {
                Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
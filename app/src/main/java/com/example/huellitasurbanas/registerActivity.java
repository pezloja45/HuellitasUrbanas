package com.example.huellitasurbanas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {

    TextView str_tieneCuenta;
    Button btn_registrar;
    EditText str_username, str_email, str_pass, str_confirmPass;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        str_username = findViewById(R.id.inputUsername);
        str_email = findViewById(R.id.str_email);
        str_pass = findViewById(R.id.str_pass);
        str_confirmPass = findViewById(R.id.str_confirmPass);

        btn_registrar = findViewById(R.id.btnRegister);
        str_tieneCuenta =findViewById(R.id.alreadyHaveAccount);



        str_tieneCuenta.setOnClickListener(v -> startActivity(new Intent(registerActivity.this,MainActivity.class)));

        btn_registrar.setOnClickListener(v -> {
            String email = str_email.getText().toString().trim();
            String pass = str_pass.getText().toString().trim();
            String confirmPass = str_confirmPass.getText().toString().trim();
            String username = str_username.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || username.isEmpty()) {
                Toast.makeText(registerActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirmPass)) {
                Toast.makeText(registerActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else if (!isPasswordValid(pass)) {
                Toast.makeText(registerActivity.this, "La contraseña debe tener al menos 5 caracteres, una mayúscula, un número y un carácter especial", Toast.LENGTH_LONG).show();
            } else {
                registerUser(email, pass);
            }
        });
    }

    private void registerUser(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(registerActivity.this,MainActivity.class));
            } else {
                Toast.makeText(registerActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(registerActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show());
    }

    private boolean isPasswordValid(String password) {
        // Al menos 5 caracteres, una mayúscula, un número y un carácter especial
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,}$";
        return password.matches(passwordPattern);
    }



}
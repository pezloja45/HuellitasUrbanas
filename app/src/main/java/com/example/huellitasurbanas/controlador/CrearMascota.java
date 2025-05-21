package com.example.huellitasurbanas.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Mascota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class CrearMascota extends AppCompatActivity {

    private EditText str_nombreMascota, str_raza, str_eddad, str_observaciones;
    private Spinner spinner_tamaño;
    private Button btn_crearMascota;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mascota);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        str_nombreMascota = findViewById(R.id.str_nombreMascota);
        str_raza = findViewById(R.id.str_raza);
        str_eddad = findViewById(R.id.str_eddad);
        spinner_tamaño = findViewById(R.id.spinner_tamaño);
        str_observaciones = findViewById(R.id.str_observaciones);
        btn_crearMascota = findViewById(R.id.btn_crearMascota);

        btn_crearMascota.setOnClickListener(v -> crearMascota());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tamaños_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tamaño.setAdapter(adapter);
    }

    private void crearMascota() {
        String nombre = str_nombreMascota.getText().toString().trim();
        String raza = str_raza.getText().toString().trim();
        String edadStr = str_eddad.getText().toString().trim();
        String tamaño = spinner_tamaño.getSelectedItem().toString();
        String observaciones = str_observaciones.getText().toString().trim();

        if (nombre.isEmpty() || raza.isEmpty() || edadStr.isEmpty() || tamaño.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        String uidMascota = UUID.randomUUID().toString();
        String uidDueno = mAuth.getCurrentUser().getUid();

        Mascota nuevaMascota = new Mascota(uidMascota, uidDueno, nombre, edad, raza, tamaño, observaciones);

        db.collection("mascotas").document(uidMascota)
                .set(nuevaMascota)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Mascota registrada con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}

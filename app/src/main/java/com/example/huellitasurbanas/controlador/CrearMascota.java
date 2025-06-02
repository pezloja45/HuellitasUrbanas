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

/**
 * Actividad para registrar una nueva mascota en la base de datos.
 * Captura información básica como nombre, raza, edad, tamaño y observaciones.
 */
public class CrearMascota extends AppCompatActivity {

    private EditText campoNombre, campoRaza, campoEdad, campoObservaciones;
    private Spinner spinnerTamaño;
    private Button botonCrearMascota;

    private FirebaseAuth autenticacionFirebase;
    private FirebaseFirestore baseDatos;

    /**
     * Método que se ejecuta al iniciar la actividad. Inicializa vistas y configuraciones.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mascota);

        // Inicialización de Firebase
        autenticacionFirebase = FirebaseAuth.getInstance();
        baseDatos = FirebaseFirestore.getInstance();

        // Enlazar componentes de la vista
        campoNombre = findViewById(R.id.str_nombreMascota);
        campoRaza = findViewById(R.id.str_raza);
        campoEdad = findViewById(R.id.str_eddad);
        campoObservaciones = findViewById(R.id.str_observaciones);
        spinnerTamaño = findViewById(R.id.spinner_tamaño);
        botonCrearMascota = findViewById(R.id.btn_crearMascota);

        // Configurar spinner con los tamaños disponibles
        ArrayAdapter<CharSequence> adaptadorTamaños = ArrayAdapter.createFromResource(
                this,
                R.array.tamaños_array,
                android.R.layout.simple_spinner_item
        );
        adaptadorTamaños.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTamaño.setAdapter(adaptadorTamaños);

        // Listener del botón para registrar la mascota
        botonCrearMascota.setOnClickListener(v -> registrarMascota());
    }

    /**
     * Valida los campos ingresados y guarda la mascota en Firestore.
     * Muestra mensajes en caso de error o éxito.
     */
    private void registrarMascota() {
        String nombre = campoNombre.getText().toString().trim();
        String raza = campoRaza.getText().toString().trim();
        String edadTexto = campoEdad.getText().toString().trim();
        String tamaño = spinnerTamaño.getSelectedItem().toString();
        String observaciones = campoObservaciones.getText().toString().trim();

        // Validación de campos obligatorios
        if (nombre.isEmpty() || raza.isEmpty() || edadTexto.isEmpty() || tamaño.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear nuevo objeto Mascota con identificador único
        String uidMascota = UUID.randomUUID().toString();
        String uidDueño = autenticacionFirebase.getCurrentUser().getUid();

        Mascota nuevaMascota = new Mascota(
                uidMascota,
                uidDueño,
                nombre,
                edad,
                raza,
                tamaño,
                observaciones
        );

        // Guardar en Firestore
        baseDatos.collection("mascotas").document(uidMascota)
                .set(nuevaMascota)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Mascota registrada con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Finaliza la actividad y vuelve a la anterior
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
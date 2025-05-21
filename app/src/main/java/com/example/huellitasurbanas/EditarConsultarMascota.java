package com.example.huellitasurbanas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.huellitasurbanas.modelo.Mascota;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditarConsultarMascota extends AppCompatActivity {

    private TextInputEditText editNombre, editRaza, editEdad, editTamano, editObservaciones;
    private Button btnActualizar;
    private ImageView imgMascota;

    private FirebaseFirestore db;
    private StorageReference storageRef;

    private String uidMascota;
    private Mascota mascotaActual;
    private Uri imageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_consultar_mascota);

        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        uidMascota = getIntent().getStringExtra("uidMascota");
        if (uidMascota == null || uidMascota.isEmpty()) {
            Toast.makeText(this, "Error: UID de mascota no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializa vistas
        editNombre = findViewById(R.id.edit_nombre);
        editRaza = findViewById(R.id.edit_raza);
        editEdad = findViewById(R.id.edit_edad);
        editTamano = findViewById(R.id.edit_tamano);
        editObservaciones = findViewById(R.id.edit_observaciones);
        btnActualizar = findViewById(R.id.btn_actualizar_mascota);
        imgMascota = findViewById(R.id.img_mascota);

        cargarDatosMascota();

        imgMascota.setOnClickListener(v -> abrirGaleria());
        btnActualizar.setOnClickListener(v -> actualizarMascota());
    }

    private void cargarDatosMascota() {
        db.collection("mascotas").document(uidMascota).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        mascotaActual = doc.toObject(Mascota.class);
                        if (mascotaActual != null) {
                            editNombre.setText(mascotaActual.getNombre());
                            editRaza.setText(mascotaActual.getRaza());
                            editEdad.setText(String.valueOf(mascotaActual.getEdad()));
                            editTamano.setText(mascotaActual.getTamaño());
                            editObservaciones.setText(mascotaActual.getObservaciones());

                            if (mascotaActual.getFotoUrl() != null && !mascotaActual.getFotoUrl().isEmpty()) {
                                Picasso.get().load(mascotaActual.getFotoUrl()).into(imgMascota);
                            }
                        }
                    } else {
                        Toast.makeText(this, "Mascota no encontrada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                });
    }

    private void abrirGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgMascota.setImageURI(imageUri);
        }
    }

    private void actualizarMascota() {
        if (mascotaActual == null) return;

        String nombre = editNombre.getText().toString().trim();
        String raza = editRaza.getText().toString().trim();
        String edadStr = editEdad.getText().toString().trim();
        String tamano = editTamano.getText().toString().trim();
        String observaciones = editObservaciones.getText().toString().trim();

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        mascotaActual.setNombre(nombre);
        mascotaActual.setRaza(raza);
        mascotaActual.setEdad(edad);
        mascotaActual.setTamaño(tamano);
        mascotaActual.setObservaciones(observaciones);

        if (imageUri != null) {
            StorageReference fileRef = storageRef.child("mascotas/" + uidMascota + ".jpg");
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        mascotaActual.setFotoUrl(uri.toString());
                        guardarMascota();
                    }))
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error al subir imagen: " + e.getMessage(), Toast.LENGTH_LONG).show());
        } else {
            guardarMascota();
        }
    }

    private void guardarMascota() {
        db.collection("mascotas").document(uidMascota)
                .set(mascotaActual)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Mascota actualizada correctamente", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}

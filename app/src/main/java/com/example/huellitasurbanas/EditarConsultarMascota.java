package com.example.huellitasurbanas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huellitasurbanas.modelo.Mascota;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditarConsultarMascota extends AppCompatActivity {

    // Campos de texto para editar los atributos de la mascota
    private TextInputEditText editNombre, editRaza, editEdad, editTamano, editObservaciones;
    // Botón para actualizar la información
    private Button btnActualizar;
    // Imagen de la mascota (editable)
    private ImageView imgMascota;

    // Instancias de Firestore y Firebase Storage
    private FirebaseFirestore db;
    private StorageReference storageRef;

    // Identificador único de la mascota a editar
    private String uidMascota;
    // Objeto Mascota que representa la información actual
    private Mascota mascotaActual;
    // URI de la imagen seleccionada por el usuario
    private Uri imageUri;

    // Código para identificar la petición de selección de imagen en galería
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_consultar_mascota);

        // Inicialización de Firebase Firestore y Storage
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        // Obtener el UID de la mascota enviado desde la actividad anterior
        uidMascota = getIntent().getStringExtra("uidMascota");
        if (uidMascota == null || uidMascota.isEmpty()) {
            Toast.makeText(this, "Error: UID de mascota no encontrado", Toast.LENGTH_SHORT).show();
            finish();  // Cierra la actividad si no hay UID
            return;
        }

        // Referencias a los elementos UI del layout
        editNombre = findViewById(R.id.edit_nombre);
        editRaza = findViewById(R.id.edit_raza);
        editEdad = findViewById(R.id.edit_edad);
        editTamano = findViewById(R.id.edit_tamano);
        editObservaciones = findViewById(R.id.edit_observaciones);
        btnActualizar = findViewById(R.id.btn_actualizar_mascota);
        imgMascota = findViewById(R.id.img_mascota);

        // Cargar la información actual de la mascota desde Firestore y mostrarla en los campos
        cargarDatosMascota();

        // Permitir cambiar la foto al hacer click en la imagen
        imgMascota.setOnClickListener(v -> abrirGaleria());
        // Configurar acción para actualizar mascota al presionar el botón
        btnActualizar.setOnClickListener(v -> actualizarMascota());
    }

    /**
     * Carga los datos de la mascota desde Firestore usando su UID
     * y los muestra en los campos de edición.
     */
    private void cargarDatosMascota() {
        db.collection("mascotas").document(uidMascota).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        mascotaActual = doc.toObject(Mascota.class);
                        if (mascotaActual != null) {
                            // Rellenar los campos con los datos actuales
                            editNombre.setText(mascotaActual.getNombre());
                            editRaza.setText(mascotaActual.getRaza());
                            editEdad.setText(String.valueOf(mascotaActual.getEdad()));
                            editTamano.setText(mascotaActual.getTamaño());
                            editObservaciones.setText(mascotaActual.getObservaciones());

                            // Cargar imagen si existe URL válida
                            if (mascotaActual.getFotoUrl() != null && !mascotaActual.getFotoUrl().isEmpty()) {
                                Picasso.get().load(mascotaActual.getFotoUrl()).into(imgMascota);
                            }
                        }
                    } else {
                        Toast.makeText(this, "Mascota no encontrada", Toast.LENGTH_SHORT).show();
                        finish(); // Cerrar actividad si no existe la mascota
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                });
    }

    /**
     * Abre la galería para que el usuario seleccione una imagen para la mascota.
     */
    private void abrirGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    /**
     * Recibe el resultado de la selección de imagen en galería.
     * Actualiza la vista con la imagen seleccionada.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgMascota.setImageURI(imageUri); // Mostrar la imagen seleccionada en la UI
        }
    }

    /**
     * Valida los campos, actualiza el objeto mascota con los datos nuevos,
     * sube la nueva imagen si la hay y guarda los cambios en Firestore.
     */
    private void actualizarMascota() {
        if (mascotaActual == null) return;

        // Obtener valores desde los campos de texto
        String nombre = editNombre.getText().toString().trim();
        String raza = editRaza.getText().toString().trim();
        String edadStr = editEdad.getText().toString().trim();
        String tamano = editTamano.getText().toString().trim();
        String observaciones = editObservaciones.getText().toString().trim();

        // Validar y parsear edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar los campos del objeto mascota
        mascotaActual.setNombre(nombre);
        mascotaActual.setRaza(raza);
        mascotaActual.setEdad(edad);
        mascotaActual.setTamaño(tamano);
        mascotaActual.setObservaciones(observaciones);

        // Si el usuario seleccionó una nueva imagen, subirla a Firebase Storage
        if (imageUri != null) {
            StorageReference fileRef = storageRef.child("mascotas/" + uidMascota + ".jpg");
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot ->
                            // Obtener URL de descarga y actualizar el objeto mascota
                            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                mascotaActual.setFotoUrl(uri.toString());
                                guardarMascota();
                            })
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error al subir imagen: " + e.getMessage(), Toast.LENGTH_LONG).show());
        } else {
            // Si no hay nueva imagen, solo guarda los datos
            guardarMascota();
        }
    }

    /**
     * Guarda el objeto mascota actualizado en Firestore.
     */
    private void guardarMascota() {
        db.collection("mascotas").document(uidMascota)
                .set(mascotaActual)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Mascota actualizada correctamente", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
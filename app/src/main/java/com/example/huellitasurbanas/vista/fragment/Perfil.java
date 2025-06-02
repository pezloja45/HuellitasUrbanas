package com.example.huellitasurbanas.vista.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Arrays;

public class Perfil extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private EditText edt_nombre, edt_correo;
    private Spinner spinner_ciudad;

    private Button btn_guardar;
    private ImageView img_fotoPerfil;

    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        subirImagenAFirebase(selectedImageUri);
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        img_fotoPerfil = view.findViewById(R.id.img_fotoPerfil);
        edt_nombre = view.findViewById(R.id.edt_nombre);
        edt_correo = view.findViewById(R.id.edt_correo);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        spinner_ciudad = view.findViewById(R.id.spinner_ciudad);

        String[] ciudades = getResources().getStringArray(R.array.ciudades_es);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, ciudades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ciudad.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        cargarDatosUsuario();

        btn_guardar.setOnClickListener(v -> guardarCambios());

        img_fotoPerfil.setOnClickListener(v -> abrirGaleria());

        return view;
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void subirImagenAFirebase(Uri imageUri) {
        if (imageUri == null || mAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "No hay usuario o imagen para subir", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = mAuth.getCurrentUser().getUid();

        btn_guardar.setEnabled(false);
        Toast.makeText(getContext(), "Subiendo imagen...", Toast.LENGTH_SHORT).show();

        FirebaseStorage.getInstance()
                .getReference("fotos_perfil/" + uid + ".jpg")
                .putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    actualizarFotoEnFirestore(uid, imageUrl);
                }))
                .addOnFailureListener(e -> {
                    btn_guardar.setEnabled(true);
                    Toast.makeText(getContext(), "Error al subir imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void actualizarFotoEnFirestore(String uid, String imageUrl) {
        db.collection("usuarios").document(uid)
                .update("fotoPerfil", imageUrl)
                .addOnSuccessListener(unused -> {
                    btn_guardar.setEnabled(true);
                    Glide.with(requireContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.baseline_person_24)
                            .error(R.drawable.baseline_person_24)
                            .circleCrop()
                            .into(img_fotoPerfil);
                    Toast.makeText(getContext(), "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    btn_guardar.setEnabled(true);
                    Toast.makeText(getContext(), "Error al actualizar Firestore: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void guardarCambios() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = mAuth.getCurrentUser().getUid();
        String nombre = edt_nombre.getText().toString().trim();
        String ciudad = spinner_ciudad.getSelectedItem().toString();

        if (nombre.isEmpty()) {
            edt_nombre.setError("El nombre no puede estar vacío");
            edt_nombre.requestFocus();
            return;
        }

        btn_guardar.setEnabled(false);
        Toast.makeText(getContext(), "Guardando cambios...", Toast.LENGTH_SHORT).show();

        db.collection("usuarios").document(uid)
                .update("nombre", nombre, "ciudad", ciudad)
                .addOnSuccessListener(unused -> {
                    btn_guardar.setEnabled(true);
                    Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    btn_guardar.setEnabled(true);
                    Toast.makeText(getContext(), "Error al actualizar datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void cargarDatosUsuario() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = mAuth.getCurrentUser().getUid();

        db.collection("usuarios").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Usuarios usuario = documentSnapshot.toObject(Usuarios.class);
                        if (usuario != null) {
                            edt_nombre.setText(usuario.getNombre());
                            String ciudadUsuario = usuario.getCiudad();
                            String[] ciudades = getResources().getStringArray(R.array.ciudades_es);
                            int index = Arrays.asList(ciudades).indexOf(ciudadUsuario);
                            if (index >= 0) {
                                spinner_ciudad.setSelection(index);
                            }

                            edt_correo.setText(usuario.getCorreoElectronico());

                            Glide.with(requireContext())
                                    .load(usuario.getFotoPerfil())
                                    .placeholder(R.drawable.baseline_person_24)
                                    .error(R.drawable.baseline_person_24)
                                    .circleCrop()
                                    .into(img_fotoPerfil);
                        }
                    } else {
                        Toast.makeText(getContext(), "No se encontraron datos del usuario.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al obtener perfil: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
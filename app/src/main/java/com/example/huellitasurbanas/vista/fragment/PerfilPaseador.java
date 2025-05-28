package com.example.huellitasurbanas.vista.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public class PerfilPaseador extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private ImageView img_fotoPerfil;
    private EditText edt_nombre, edt_ciudad, edt_correo;
    private View btn_guardar;

    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    subirImagenAFirebase(selectedImageUri);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_paseador, container, false);

        img_fotoPerfil = view.findViewById(R.id.img_fotoPerfil);
        edt_nombre = view.findViewById(R.id.edt_nombre);
        edt_ciudad = view.findViewById(R.id.edt_ciudad);
        edt_correo = view.findViewById(R.id.edt_correo);
        btn_guardar = view.findViewById(R.id.btn_guardar);

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
        if (imageUri == null || mAuth.getCurrentUser() == null) return;

        String uid = mAuth.getCurrentUser().getUid();

        FirebaseStorage.getInstance()
                .getReference("fotos_perfil/" + uid + ".jpg")
                .putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        actualizarFotoEnFirestore(uid, imageUrl);
                    });
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al subir imagen: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void actualizarFotoEnFirestore(String uid, String imageUrl) {
        db.collection("usuarios").document(uid)
                .update("fotoPerfil", imageUrl)
                .addOnSuccessListener(unused -> {
                    Glide.with(requireContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.baseline_person_24)
                            .into(img_fotoPerfil);
                    Toast.makeText(getContext(), "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al actualizar Firestore: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void cargarDatosUsuario() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("usuarios").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Usuarios usuario = documentSnapshot.toObject(Usuarios.class);
                    if (usuario != null) {
                        edt_nombre.setText(usuario.getNombre());
                        edt_ciudad.setText(usuario.getCiudad());
                        edt_correo.setText(usuario.getCorreoElectronico());
                        edt_correo.setEnabled(false); // No editable

                        Glide.with(requireContext())
                                .load(usuario.getFotoPerfil())
                                .placeholder(R.drawable.baseline_person_24)
                                .into(img_fotoPerfil);
                    }
                });
    }

    private void guardarCambios() {
        String uid = mAuth.getCurrentUser().getUid();
        String nuevoNombre = edt_nombre.getText().toString().trim();
        String nuevaCiudad = edt_ciudad.getText().toString().trim();

        if (TextUtils.isEmpty(nuevoNombre) || TextUtils.isEmpty(nuevaCiudad)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("usuarios").document(uid)
                .update("nombre", nuevoNombre, "ciudad", nuevaCiudad)
                .addOnSuccessListener(unused -> Toast.makeText(getContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
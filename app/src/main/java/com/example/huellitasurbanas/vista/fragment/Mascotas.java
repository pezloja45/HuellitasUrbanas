package com.example.huellitasurbanas.vista.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.controlador.AdaptadorMascota;
import com.example.huellitasurbanas.controlador.CrearMascota;
import com.example.huellitasurbanas.modelo.Mascota;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Mascotas extends Fragment {

    private FloatingActionButton btn_addMascota;
    private RecyclerView rv_mascotas;
    private List<Mascota> listaMascotas;
    private AdaptadorMascota adaptador;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mascotas, container, false);

        btn_addMascota = view.findViewById(R.id.btn_addMascota);
        rv_mascotas = view.findViewById(R.id.rv_mascotas);

        rv_mascotas.setLayoutManager(new LinearLayoutManager(getContext()));
        listaMascotas = new ArrayList<>();
        adaptador = new AdaptadorMascota(listaMascotas);
        rv_mascotas.setAdapter(adaptador);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btn_addMascota.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CrearMascota.class);
            startActivity(intent);
        });

        cargarMascotas();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarMascotas();
    }

    private void cargarMascotas() {
        String uidDueno = mAuth.getCurrentUser().getUid();

        db.collection("mascotas")
                .whereEqualTo("uidDueno", uidDueno)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaMascotas.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Mascota mascota = doc.toObject(Mascota.class);
                        listaMascotas.add(mascota);
                    }
                    adaptador.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Error al cargar mascotas: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}

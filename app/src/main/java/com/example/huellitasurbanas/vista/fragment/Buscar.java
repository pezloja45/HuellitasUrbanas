package com.example.huellitasurbanas.vista.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.controlador.AdaptadorBuscar;
import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Buscar extends Fragment {

    RecyclerView rv_buscarPaseador;
    AdaptadorBuscar adaptador;
    List<Usuarios> listaUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        rv_buscarPaseador = view.findViewById(R.id.rv_buscarPaseador);
        rv_buscarPaseador.setLayoutManager(new LinearLayoutManager(getContext()));

        listaUsuarios = new ArrayList<>();
        adaptador = new AdaptadorBuscar(listaUsuarios, usuario -> {
            Chat chatFragment = new Chat();
            Bundle args = new Bundle();
            args.putString("receiverId", usuario.getUid());
            chatFragment.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainerPaseador, chatFragment)
                    .addToBackStack(null)
                    .commit();
        });
        rv_buscarPaseador.setAdapter(adaptador);

        cargarPaseadores();
        return view;
    }

    private void cargarPaseadores() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios")
                .whereEqualTo("rol", "paseador")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaUsuarios.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Usuarios usuario = doc.toObject(Usuarios.class);
                        if (usuario != null) {
                            usuario.setUid(doc.getId());
                            listaUsuarios.add(usuario);
                        }
                    }
                    adaptador.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                });
    }
}

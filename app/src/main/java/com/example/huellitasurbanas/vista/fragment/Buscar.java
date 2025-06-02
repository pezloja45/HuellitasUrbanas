package com.example.huellitasurbanas.vista.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.controlador.AdaptadorBuscar;
import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento encargado de buscar y mostrar la lista de paseadores.
 * Permite filtrar por ciudad mediante un diálogo.
 */
public class Buscar extends Fragment {

    private RecyclerView rv_buscarPaseador;
    private AdaptadorBuscar adaptador;
    private List<Usuarios> listaUsuarios;
    private MaterialToolbar menu_buscar;
    private String[] ciudades;
    private String ciudadSeleccionada = "Todas";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        inicializarComponentes(view);
        cargarPaseadores(ciudadSeleccionada);
        return view;
    }

    /**
     * Inicializa la interfaz de usuario y configura eventos.
     *
     * @param view Vista raíz del fragmento.
     */
    private void inicializarComponentes(View view) {
        rv_buscarPaseador = view.findViewById(R.id.rv_buscarPaseador);
        rv_buscarPaseador.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cargar ciudades desde recursos + opción "Todas"
        String[] rawCiudades = getResources().getStringArray(R.array.ciudades_es);
        ciudades = new String[rawCiudades.length + 1];
        ciudades[0] = "Todas";
        System.arraycopy(rawCiudades, 0, ciudades, 1, rawCiudades.length);

        // Configurar Toolbar
        menu_buscar = view.findViewById(R.id.menu_buscar);
        menu_buscar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.btn_filtrar) {
                mostrarDialogoFiltro();
                return true;
            }
            return false;
        });

        listaUsuarios = new ArrayList<>();
        adaptador = new AdaptadorBuscar(listaUsuarios, usuario -> {
            Chat chatFragment = new Chat();
            Bundle args = new Bundle();
            args.putString("receiverId", usuario.getUid());
            chatFragment.setArguments(args);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, chatFragment)
                    .addToBackStack(null)
                    .commit();
        });

        rv_buscarPaseador.setAdapter(adaptador);
    }

    /**
     * Muestra un diálogo para seleccionar la ciudad por la que se filtrará la búsqueda.
     */
    private void mostrarDialogoFiltro() {
        int seleccionActual = 0;
        for (int i = 0; i < ciudades.length; i++) {
            if (ciudades[i].equalsIgnoreCase(ciudadSeleccionada)) {
                seleccionActual = i;
                break;
            }
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Selecciona ciudad")
                .setSingleChoiceItems(ciudades, seleccionActual, (dialog, which) -> {
                    ciudadSeleccionada = ciudades[which];
                    dialog.dismiss();
                    cargarPaseadores(ciudadSeleccionada);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    /**
     * Carga la lista de usuarios con rol "paseador" desde Firestore.
     * Aplica un filtro por ciudad si corresponde.
     *
     * @param ciudad Ciudad por la cual filtrar los resultados. Si es "Todas", no se aplica filtro.
     */
    private void cargarPaseadores(String ciudad) {
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
                            if ("Todas".equals(ciudad) || ciudad.equalsIgnoreCase(usuario.getCiudad())) {
                                listaUsuarios.add(usuario);
                            }
                        }
                    }
                    adaptador.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Aquí podrías mostrar un Toast o registrar el error
                });
    }
}
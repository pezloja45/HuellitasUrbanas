package com.example.huellitasurbanas.vista.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.controlador.AdaptadorChatsAbiertos;
import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment para que el paseador vea chats abiertos con dueños.
 * Además, puede ver mascotas del dueño con click largo.
 */
public class ChatPaseador extends Fragment {

    private FirebaseFirestore db;
    private String paseadorId;
    private RecyclerView recyclerView;

    private List<ChatItem> listaChats = new ArrayList<>();
    private AdaptadorChatsAbiertos adaptadorChatsAbiertos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_paseador, container, false);

        db = FirebaseFirestore.getInstance();
        paseadorId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = view.findViewById(R.id.recycler_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptadorChatsAbiertos = new AdaptadorChatsAbiertos(getContext(), listaChats, new AdaptadorChatsAbiertos.OnChatClickListener() {
            @Override
            public void onChatClick(ChatItem chatItem) {
                Usuarios usuario = chatItem.getUsuario();
                Chat chatFragment = new Chat();
                Bundle args = new Bundle();
                args.putString("receiverId", usuario.getUid());
                chatFragment.setArguments(args);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainerPaseador, chatFragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onChatLongClick(ChatItem chatItem) {
                Usuarios usuario = chatItem.getUsuario();
                mostrarMascotasDeUsuario(usuario.getUid());
            }
        });

        recyclerView.setAdapter(adaptadorChatsAbiertos);

        cargarChatsDeDueños();

        return view;
    }

    /**
     * Muestra un diálogo con las mascotas registradas del dueño.
     */
    private void mostrarMascotasDeUsuario(String uid) {
        db.collection("mascotas")
                .whereEqualTo("uidDueno", uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(getContext(), "Este usuario no tiene mascotas registradas.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    StringBuilder builder = new StringBuilder();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String nombre = doc.getString("nombre");
                        String raza = doc.getString("raza");
                        String tamaño = doc.getString("tamaño");
                        String observaciones = doc.getString("observaciones");

                        builder.append("🐾 Nombre: ").append(nombre).append("\n")
                                .append("📍 Raza: ").append(raza).append("\n")
                                .append("📏 Tamaño: ").append(tamaño).append("\n")
                                .append("📝 Observaciones: ").append(observaciones).append("\n\n");
                    }

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View dialogView = inflater.inflate(R.layout.dialog_mascotas, null);

                    TextView tvMascotasInfo = dialogView.findViewById(R.id.tvMascotasInfo);
                    Button btnCerrar = dialogView.findViewById(R.id.btnCerrar);

                    tvMascotasInfo.setText(builder.toString());

                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setView(dialogView)
                            .create();

                    btnCerrar.setOnClickListener(v -> dialog.dismiss());

                    dialog.show();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al cargar mascotas", Toast.LENGTH_SHORT).show());
    }

    /**
     * Carga la lista de chats abiertos en los que participa el paseador
     * mostrando sólo los mensajes recibidos de dueños.
     */
    private void cargarChatsDeDueños() {
        listaChats.clear();

        db.collection("chats")
                .whereArrayContains("usuarios", paseadorId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot chatDoc : querySnapshot.getDocuments()) {
                        String chatId = chatDoc.getId();
                        List<String> usuarios = (List<String>) chatDoc.get("usuarios");

                        if (usuarios == null || usuarios.size() < 2) continue;

                        String otroUid = null;
                        for (String uid : usuarios) {
                            if (!uid.equals(paseadorId)) {
                                otroUid = uid;
                                break;
                            }
                        }

                        if (otroUid != null) {
                            final String finalOtroUid = otroUid;

                            // Obtener último mensaje (orden ascendente para coger primero, o DESC + limit 1 para último)
                            db.collection("chats").document(chatId).collection("messages")
                                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                                    .limit(1)
                                    .get()
                                    .addOnSuccessListener(mensajesSnapshot -> {
                                        if (!mensajesSnapshot.isEmpty()) {
                                            DocumentSnapshot ultimoMensaje = mensajesSnapshot.getDocuments().get(0);
                                            String sender = ultimoMensaje.getString("senderId");

                                            // Sólo mostrar si el último mensaje es del dueño (no del paseador)
                                            if (!paseadorId.equals(sender)) {
                                                String texto = ultimoMensaje.getString("message");

                                                db.collection("usuarios").document(finalOtroUid)
                                                        .get()
                                                        .addOnSuccessListener(usuarioDoc -> {
                                                            Usuarios usuario = usuarioDoc.toObject(Usuarios.class);
                                                            if (usuario == null) return;
                                                            usuario.setUid(finalOtroUid);

                                                            listaChats.add(new ChatItem(usuario, texto));
                                                            adaptadorChatsAbiertos.notifyDataSetChanged();
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al cargar chats", Toast.LENGTH_SHORT).show());
    }
}
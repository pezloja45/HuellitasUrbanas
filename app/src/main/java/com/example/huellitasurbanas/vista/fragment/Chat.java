package com.example.huellitasurbanas.vista.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.controlador.AdaptadorChatsAbiertos;
import com.example.huellitasurbanas.controlador.AdaptadorMensaje;
import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Message;
import com.example.huellitasurbanas.modelo.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment {

    private FirebaseFirestore db;
    private String senderId, receiverId, chatId;

    private EditText editMessage;
    private Button btnSend;
    private RecyclerView recyclerView;

    // Para mensajes individuales
    private List<Message> messageList = new ArrayList<>();
    private AdaptadorMensaje adapterMensajes;

    // Para lista de chats abiertos
    private List<ChatItem> listaChats = new ArrayList<>();
    private AdaptadorChatsAbiertos adaptadorChatsAbiertos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        db = FirebaseFirestore.getInstance();
        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        receiverId = null;
        if (getArguments() != null) {
            receiverId = getArguments().getString("receiverId");
        }

        recyclerView = view.findViewById(R.id.recycler_messages);

        if (receiverId == null) {
            // Modo lista chats abiertos
            editMessage = null;
            btnSend = null;

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            adaptadorChatsAbiertos = new AdaptadorChatsAbiertos(getContext(), listaChats, chatItem -> {
                //Usuarios usuario = chatItem.getUsuario();
                Chat chatFragment = new Chat();
                Bundle args = new Bundle();
                //args.putString("receiverId", usuario.getUid());
                chatFragment.setArguments(args);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainerPaseador, chatFragment)
                        .addToBackStack(null)
                        .commit();
            });
            recyclerView.setAdapter(adaptadorChatsAbiertos);

            cargarChatsAbiertos();

        } else {
            // Modo chat individual
            chatId = senderId.compareTo(receiverId) < 0 ? senderId + "_" + receiverId : receiverId + "_" + senderId;

            editMessage = view.findViewById(R.id.edit_message);
            btnSend = view.findViewById(R.id.btn_send);

            adapterMensajes = new AdaptadorMensaje(messageList, senderId);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapterMensajes);

            btnSend.setOnClickListener(v -> sendMessage());

            listenForMessages();
        }

        return view;
    }

    private void cargarChatsAbiertos() {
        listaChats.clear();

        db.collection("chats")
                .whereArrayContains("usuarios", senderId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot chatDoc : querySnapshot.getDocuments()) {
                        String chatId = chatDoc.getId();
                        List<String> usuarios = (List<String>) chatDoc.get("usuarios");

                        String otroUid = null;
                        for (String uid : usuarios) {
                            if (!uid.equals(senderId)) {
                                otroUid = uid;
                                break;
                            }
                        }

                        if (otroUid != null) {
                            final String finalOtroUid = otroUid;
                            db.collection("usuarios").document(finalOtroUid)
                                    .get()
                                    .addOnSuccessListener(usuarioDoc -> {
                                        Usuarios usuario = usuarioDoc.toObject(Usuarios.class);
                                        if (usuario == null) return;
                                        usuario.setUid(finalOtroUid);

                                        db.collection("chats")
                                                .document(chatId)
                                                .collection("messages")
                                                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                                                .limit(1)
                                                .get()
                                                .addOnSuccessListener(mensajesSnapshot -> {
                                                    String texto = "Sin mensajes aún";
                                                    if (!mensajesSnapshot.isEmpty()) {
                                                        texto = mensajesSnapshot.getDocuments().get(0).getString("texto");
                                                    }

                                                    listaChats.add(new ChatItem(usuario, texto));
                                                    adaptadorChatsAbiertos.notifyDataSetChanged();
                                                });
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al cargar chats", Toast.LENGTH_SHORT).show();
                });
    }

    private void sendMessage() {
        String text = editMessage.getText().toString().trim();
        if (text.isEmpty()) return;

        Message msg = new Message(senderId, receiverId, text, System.currentTimeMillis());

        db.collection("chats").document(chatId).collection("messages")
                .add(msg)
                .addOnSuccessListener(documentReference -> {
                    editMessage.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
                });
    }

    private void listenForMessages() {
        db.collection("chats").document(chatId).collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(getContext(), "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (snapshots != null) {
                        messageList.clear();
                        for (DocumentSnapshot doc : snapshots) {
                            Message message = doc.toObject(Message.class);
                            if (message != null) {
                                messageList.add(message);
                            }
                        }
                        adapterMensajes.notifyDataSetChanged();
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    }
                });
    }
}
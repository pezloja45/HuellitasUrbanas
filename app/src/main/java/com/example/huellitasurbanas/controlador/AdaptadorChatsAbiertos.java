package com.example.huellitasurbanas.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Usuarios;

import java.util.List;

public class AdaptadorChatsAbiertos extends RecyclerView.Adapter<AdaptadorChatsAbiertos.ViewHolder> {

    public interface OnChatClickListener {
        void onChatClick(Usuarios usuario);
    }

    private List<ChatItem> listaChats;
    private Context context;
    private OnChatClickListener listener;

    public AdaptadorChatsAbiertos(Context context, List<ChatItem> listaChats, OnChatClickListener listener) {
        this.context = context;
        this.listaChats = listaChats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_abierto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatItem chat = listaChats.get(position);
        Usuarios usuario = chat.getUsuario();

        holder.txtNombre.setText(usuario.getNombre());
        holder.txtUltimoMensaje.setText(chat.getUltimoMensaje());

        holder.itemView.setOnClickListener(v -> listener.onChatClick(usuario));
    }

    @Override
    public int getItemCount() {
        return listaChats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtUltimoMensaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreChat);
            txtUltimoMensaje = itemView.findViewById(R.id.txtUltimoMensaje);
        }
    }
}
package com.example.huellitasurbanas.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.ChatItem;
import com.example.huellitasurbanas.modelo.Usuarios;

import java.util.List;

public class AdaptadorChatsAbiertos extends RecyclerView.Adapter<AdaptadorChatsAbiertos.ViewHolder> {

    public interface OnChatClickListener {
        void onChatClick(ChatItem chatItem);
        void onChatLongClick(ChatItem chatItem);
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

        String photoUrl = usuario.getFotoPerfil();
        if (photoUrl != null && !photoUrl.isEmpty()) {
            Glide.with(context)
                    .load(photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.baseline_person_150)
                    .into(holder.imgFotoChat);
        } else {
            holder.imgFotoChat.setImageResource(R.drawable.baseline_person_150);
        }

        holder.itemView.setOnClickListener(v -> listener.onChatClick(chat));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onChatLongClick(chat);
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return listaChats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFotoChat;
        TextView txtNombre, txtUltimoMensaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFotoChat = itemView.findViewById(R.id.imgFotoChat);
            txtNombre = itemView.findViewById(R.id.txtNombreChat);
            txtUltimoMensaje = itemView.findViewById(R.id.txtUltimoMensaje);
        }
    }
}
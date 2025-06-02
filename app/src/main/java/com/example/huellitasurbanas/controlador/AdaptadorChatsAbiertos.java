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

/**
 * Adaptador para el RecyclerView que muestra la lista de chats abiertos.
 * Cada item representa una conversación con un usuario.
 */
public class AdaptadorChatsAbiertos extends RecyclerView.Adapter<AdaptadorChatsAbiertos.ViewHolder> {

    /**
     * Interfaz para manejar eventos de clic y clic prolongado sobre los items del chat.
     */
    public interface OnChatClickListener {
        void onChatClick(ChatItem chatItem);
        void onChatLongClick(ChatItem chatItem);
    }

    private List<ChatItem> listaChats;
    private Context contexto;
    private OnChatClickListener oyente;

    /**
     * Constructor del adaptador.
     *
     * @param contexto   Contexto de la aplicación o actividad.
     * @param listaChats Lista de chats abiertos a mostrar.
     * @param oyente     Oyente para los eventos de clic y clic largo.
     */
    public AdaptadorChatsAbiertos(Context contexto, List<ChatItem> listaChats, OnChatClickListener oyente) {
        this.contexto = contexto;
        this.listaChats = listaChats;
        this.oyente = oyente;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.item_chat_abierto, padre, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int posicion) {
        ChatItem chat = listaChats.get(posicion);
        Usuarios usuario = chat.getUsuario();

        // Asignar nombre y último mensaje
        holder.textoNombre.setText(usuario.getNombre());
        holder.textoUltimoMensaje.setText(chat.getUltimoMensaje());

        // Cargar imagen de perfil usando Glide, o imagen por defecto si no hay
        String urlFoto = usuario.getFotoPerfil();
        if (urlFoto != null && !urlFoto.isEmpty()) {
            Glide.with(contexto)
                    .load(urlFoto)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.baseline_person_150)
                    .into(holder.imagenPerfil);
        } else {
            holder.imagenPerfil.setImageResource(R.drawable.baseline_person_150);
        }

        // Manejar clic corto
        holder.itemView.setOnClickListener(v -> oyente.onChatClick(chat));

        // Manejar clic largo
        holder.itemView.setOnLongClickListener(v -> {
            oyente.onChatLongClick(chat);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaChats.size();
    }

    /**
     * ViewHolder interno que contiene las vistas para cada item de chat.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenPerfil;
        TextView textoNombre, textoUltimoMensaje;

        /**
         * Constructor del ViewHolder.
         *
         * @param vistaItem Vista inflada del layout item_chat_abierto.xml.
         */
        public ViewHolder(@NonNull View vistaItem) {
            super(vistaItem);
            imagenPerfil = vistaItem.findViewById(R.id.imgFotoChat);
            textoNombre = vistaItem.findViewById(R.id.txtNombreChat);
            textoUltimoMensaje = vistaItem.findViewById(R.id.txtUltimoMensaje);
        }
    }
}
package com.example.huellitasurbanas.controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptador para mostrar mensajes en un RecyclerView dentro del chat.
 * Distingue entre mensajes enviados y recibidos, utilizando layouts diferentes.
 */
public class AdaptadorMensaje extends RecyclerView.Adapter<AdaptadorMensaje.VistaMensaje> {

    private final List<Message> listaMensajes;
    private final String idUsuarioActual;

    /**
     * Constructor del adaptador.
     * @param listaMensajes Lista de objetos Message a mostrar.
     * @param idUsuarioActual ID del usuario actual para distinguir entre enviados y recibidos.
     */
    public AdaptadorMensaje(List<Message> listaMensajes, String idUsuarioActual) {
        this.listaMensajes = listaMensajes;
        this.idUsuarioActual = idUsuarioActual;
    }

    /**
     * Determina el tipo de vista dependiendo de si el mensaje fue enviado o recibido.
     * @param posicion Índice del mensaje en la lista.
     * @return 1 si fue enviado por el usuario actual, 0 si fue recibido.
     */
    @Override
    public int getItemViewType(int posicion) {
        Message mensaje = listaMensajes.get(posicion);
        return mensaje.getSenderId().equals(idUsuarioActual) ? 1 : 0;
    }

    /**
     * Crea el ViewHolder correspondiente según el tipo de mensaje.
     */
    @NonNull
    @Override
    public VistaMensaje onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista;
        if (tipoVista == 1) {
            // Layout para mensajes enviados
            vista = LayoutInflater.from(padre.getContext())
                    .inflate(R.layout.item_message_sent, padre, false);
        } else {
            // Layout para mensajes recibidos
            vista = LayoutInflater.from(padre.getContext())
                    .inflate(R.layout.item_message_received, padre, false);
        }
        return new VistaMensaje(vista);
    }

    /**
     * Asocia el contenido del mensaje al ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull VistaMensaje holder, int posicion) {
        Message mensaje = listaMensajes.get(posicion);
        holder.textoMensaje.setText(mensaje.getMessage());
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    /**
     * ViewHolder interno que representa la vista de un solo mensaje.
     */
    public static class VistaMensaje extends RecyclerView.ViewHolder {
        TextView textoMensaje;

        /**
         * Constructor del ViewHolder para mensajes.
         * @param itemView Vista inflada del mensaje.
         */
        public VistaMensaje(@NonNull View itemView) {
            super(itemView);
            textoMensaje = itemView.findViewById(R.id.text_message);
        }
    }
}
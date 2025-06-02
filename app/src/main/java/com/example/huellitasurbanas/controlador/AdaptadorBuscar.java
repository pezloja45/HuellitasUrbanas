package com.example.huellitasurbanas.controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Usuarios;

import java.util.List;

/**
 * Adaptador para mostrar una lista de paseadores en un RecyclerView.
 * Permite manejar eventos de clic en cada item.
 */
public class AdaptadorBuscar extends RecyclerView.Adapter<AdaptadorBuscar.ViewHolder> {

    private List<Usuarios> listaPaseadores;
    private OnPaseadorClickListener listener;

    /**
     * Interfaz para manejar eventos de clic sobre un paseador.
     */
    public interface OnPaseadorClickListener {
        void alHacerClick(Usuarios paseador);
    }

    /**
     * Constructor del adaptador.
     *
     * @param listaPaseadores Lista de objetos Usuarios que representan paseadores.
     * @param listener        Listener para manejar los clics en los items.
     */
    public AdaptadorBuscar(List<Usuarios> listaPaseadores, OnPaseadorClickListener listener) {
        this.listaPaseadores = listaPaseadores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = LayoutInflater.from(padre.getContext()).inflate(R.layout.cardbuscar, padre, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int posicion) {
        Usuarios paseador = listaPaseadores.get(posicion);

        // Se asignan los datos del paseador a las vistas del card
        holder.textoNombre.setText(paseador.getNombre());
        holder.textoCiudad.setText(paseador.getCiudad());

        // Carga la imagen de perfil con Glide
        Glide.with(holder.itemView.getContext())
                .load(paseador.getFotoPerfil())
                .placeholder(R.drawable.baseline_person_150) // Imagen por defecto
                .circleCrop()
                .into(holder.imagenPerfil);

        // Maneja el clic sobre el item completo
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.alHacerClick(paseador);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPaseadores.size();
    }

    /**
     * Clase interna que representa el ViewHolder para cada item del RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textoNombre, textoCiudad;
        ImageView imagenPerfil;

        /**
         * Constructor que inicializa las vistas del card.
         *
         * @param itemView Vista individual del item.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textoNombre = itemView.findViewById(R.id.str_nombrePasedorCard);
            textoCiudad = itemView.findViewById(R.id.str_ciudadPaseadorCard);
            imagenPerfil = itemView.findViewById(R.id.img_perfilPaseadorCard);
        }
    }
}
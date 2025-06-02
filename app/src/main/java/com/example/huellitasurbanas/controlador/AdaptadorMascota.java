package com.example.huellitasurbanas.controlador;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huellitasurbanas.EditarConsultarMascota;
import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Mascota;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * Adaptador para mostrar una lista de mascotas en un RecyclerView.
 * Cada tarjeta incluye la foto, nombre, raza y edad de la mascota.
 * Al hacer clic en una tarjeta, se abre la actividad para editar o consultar los datos de la mascota.
 */
public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.ViewHolder> {

    private List<Mascota> listaMascotas;

    /**
     * Constructor del adaptador.
     * @param listaMascotas Lista de mascotas a mostrar en el RecyclerView.
     */
    public AdaptadorMascota(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = LayoutInflater.from(padre.getContext()).inflate(R.layout.cardmascota, padre, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int posicion) {
        Mascota mascota = listaMascotas.get(posicion);

        // Asignar texto a los campos de la tarjeta
        holder.textoNombre.setText(mascota.getNombre());
        holder.textoRaza.setText(mascota.getRaza());
        holder.textoEdad.setText("Edad: " + mascota.getEdad());

        // Cargar imagen de la mascota desde URL usando Glide
        Glide.with(holder.itemView.getContext())
                .load(mascota.getFotoUrl())
                .placeholder(R.drawable.baseline_person_150) // Imagen temporal mientras carga
                .circleCrop()
                .error(R.drawable.baseline_error_150) // Imagen de error si falla
                .into(holder.imagenMascota);

        // Abrir actividad para editar o consultar datos al hacer clic
        holder.tarjetaMascota.setOnClickListener(v -> {
            Intent intentEditar = new Intent(v.getContext(), EditarConsultarMascota.class);
            intentEditar.putExtra("uidMascota", mascota.getUid());
            v.getContext().startActivity(intentEditar);
        });
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    /**
     * ViewHolder interno que contiene las vistas de cada tarjeta de mascota.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textoNombre, textoRaza, textoEdad;
        ImageView imagenMascota;
        MaterialCardView tarjetaMascota;

        /**
         * Constructor del ViewHolder.
         * @param itemView Vista inflada del layout de la tarjeta.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textoNombre = itemView.findViewById(R.id.str_nombreMascotaCard);
            textoRaza = itemView.findViewById(R.id.str_razaMascotaCard);
            textoEdad = itemView.findViewById(R.id.str_edadMascotaCard);
            imagenMascota = itemView.findViewById(R.id.img_mascotaCard);
            tarjetaMascota = itemView.findViewById(R.id.card_mascota);
        }
    }
}
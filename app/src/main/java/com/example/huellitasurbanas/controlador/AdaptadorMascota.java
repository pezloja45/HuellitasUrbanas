package com.example.huellitasurbanas.controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Mascota;

import java.util.List;

public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.ViewHolder> {

    private List<Mascota> listaMascotas;

    public AdaptadorMascota(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    @NonNull
    @Override
    public AdaptadorMascota.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmascota, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMascota.ViewHolder holder, int position) {
        Mascota mascota = listaMascotas.get(position);
        holder.nombreMascota.setText(mascota.getNombre());
        holder.razaMascota.setText(mascota.getRaza());
        holder.edadMascota.setText("Edad: " + mascota.getEdad());
        holder.imgMascota.setImageResource(mascota.getFoto());
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreMascota, razaMascota, edadMascota;
        ImageView imgMascota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreMascota = itemView.findViewById(R.id.str_nombreMascotaCard);
            razaMascota = itemView.findViewById(R.id.str_razaMascotaCard);
            edadMascota = itemView.findViewById(R.id.str_edadMascotaCard);
            imgMascota = itemView.findViewById(R.id.img_mascotaCard);
        }
    }
}

package com.example.huellitasurbanas.controlador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.modelo.Usuarios;

import java.util.List;

public class AdaptadorBuscar extends RecyclerView.Adapter<AdaptadorBuscar.ViewHolder> {

    private List<Usuarios> listaUsuarios;

    public AdaptadorBuscar(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public AdaptadorBuscar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardbuscar, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBuscar.ViewHolder holder, int position) {
        Usuarios usuario = listaUsuarios.get(position);
        holder.str_nombrePasedorCard.setText(usuario.getNombre());
        holder.str_ciudadPaseadorCard.setText(usuario.getCiudad());
        holder.img_perfilPaseadorCard.setImageResource(usuario.getFotoPerfil());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView str_nombrePasedorCard, str_ciudadPaseadorCard;
        ImageView img_perfilPaseadorCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            str_nombrePasedorCard = itemView.findViewById(R.id.str_nombrePasedorCard);
            str_ciudadPaseadorCard = itemView.findViewById(R.id.str_ciudadPaseadorCard);
            img_perfilPaseadorCard = itemView.findViewById(R.id.img_perfilPaseadorCard);
        }
    }
}

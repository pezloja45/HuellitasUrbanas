package com.example.huellitasurbanas.controlador;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.huellitasurbanas.vista.fragment.Chat;

import java.util.List;

public class AdaptadorBuscar extends RecyclerView.Adapter<AdaptadorBuscar.ViewHolder> {

    private List<Usuarios> listaUsuarios;
    private OnUsuarioClickListener listener;

    public AdaptadorBuscar(List<Usuarios> listaUsuarios, OnUsuarioClickListener listener) {
        this.listaUsuarios = listaUsuarios;
        this.listener = listener;
    }

    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuarios usuario);
    }

    @NonNull
    @Override
    public AdaptadorBuscar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardbuscar, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuarios usuario = listaUsuarios.get(position);
        holder.str_nombrePasedorCard.setText(usuario.getNombre());
        holder.str_ciudadPaseadorCard.setText(usuario.getCiudad());
        Glide.with(holder.itemView.getContext())
                .load(usuario.getFotoPerfil())
                .placeholder(R.drawable.baseline_person_150)
                .into(holder.img_perfilPaseadorCard);


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUsuarioClick(usuario);
            }
        });
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

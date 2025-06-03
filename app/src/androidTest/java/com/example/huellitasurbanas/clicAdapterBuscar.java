package com.example.huellitasurbanas;

import android.view.LayoutInflater;
import android.view.View;

import com.example.huellitasurbanas.controlador.AdaptadorBuscar;
import com.example.huellitasurbanas.modelo.Usuarios;
import static org.mockito.Mockito.mock;


import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class clicAdapterBuscar {
    @Test
    public void testClickEnItem_llamaAlListener() {
        Usuarios paseador = new Usuarios();
        paseador.setNombre("Test");
        paseador.setCiudad("Ciudad");

        List<Usuarios> usuarios = Collections.singletonList(paseador);
        AdaptadorBuscar.OnPaseadorClickListener listener = mock(AdaptadorBuscar.OnPaseadorClickListener.class);
        AdaptadorBuscar adaptador = new AdaptadorBuscar(usuarios, listener);

        // Inflar manualmente la vista
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardbuscar, null, false);
        AdaptadorBuscar.ViewHolder holder = new AdaptadorBuscar.ViewHolder(itemView);

        adaptador.onBindViewHolder(holder, 0);
        holder.itemView.performClick();

        verify(listener).alHacerClick(paseador);
    }

}

package com.example.huellitasurbanas.vista;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.vista.fragment.ChatPaseador;
import com.example.huellitasurbanas.vista.fragment.PerfilPaseador;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreenPaseador extends AppCompatActivity {

    // Barra de navegación inferior para el rol "paseador"
    BottomNavigationView bnv_paseador;

    // Contenedor para mostrar los fragments correspondientes
    FrameLayout frameContainerPaseador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_paseador);

        // Inicialización de vistas con sus respectivos IDs del layout
        bnv_paseador = findViewById(R.id.bnv_paseador);
        frameContainerPaseador = findViewById(R.id.frameContainerPaseador);

        // Carga inicial: se muestra el fragment ChatPaseador al abrir la pantalla
        loadFrag(new ChatPaseador());

        // Marca visualmente el item "Chat" como seleccionado en la barra inferior
        bnv_paseador.setSelectedItemId(R.id.bnt_chatPaseador);

        // Listener para detectar selección de opciones en la barra de navegación
        bnv_paseador.setOnItemSelectedListener(item -> {
            // Cambia el fragment mostrado según el item seleccionado
            if (item.getItemId() == R.id.bnt_chatPaseador) {
                loadFrag(new ChatPaseador());
            }
            if (item.getItemId() == R.id.btn_paseadorPerfil) {
                loadFrag(new PerfilPaseador());
            }
            return true; // Indica que el evento ha sido consumido
        });
    }

    /**
     * Método para reemplazar el fragment actual en el contenedor por uno nuevo
     * @param fragment Fragment que se quiere mostrar
     */
    private void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerPaseador, fragment);
        transaction.commit();
    }
}
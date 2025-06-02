package com.example.huellitasurbanas.vista;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.huellitasurbanas.vista.fragment.Buscar;
import com.example.huellitasurbanas.vista.fragment.Chat;
import com.example.huellitasurbanas.vista.fragment.Mascotas;
import com.example.huellitasurbanas.vista.fragment.Perfil;
import com.example.huellitasurbanas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreenDueno extends AppCompatActivity {

    // Declaración de la barra de navegación inferior para el dueño
    BottomNavigationView bnv_dueno;

    // Contenedor donde se cargarán los fragments correspondientes
    FrameLayout frameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_dueno);

        // Inicialización de vistas
        bnv_dueno = findViewById(R.id.bnv_dueno);
        frameContainer = findViewById(R.id.frameContainer);

        // Carga inicial: Se muestra el fragment Chat por defecto
        loadFrag(new Chat());

        // Selecciona visualmente el item "Chat" en la barra de navegación
        bnv_dueno.setSelectedItemId(R.id.bnt_chatDueno);

        // Listener que detecta cuando se selecciona un item de la barra de navegación
        bnv_dueno.setOnItemSelectedListener(item -> {
            // Dependiendo del item seleccionado, carga el fragment correspondiente
            if (item.getItemId() == R.id.bnt_chatDueno) {
                loadFrag(new Chat());
            }
            if (item.getItemId() == R.id.bnt_buscarDueno) {
                loadFrag(new Buscar());
            }
            if (item.getItemId() == R.id.bnt_mascotasDueno) {
                loadFrag(new Mascotas());
            }
            if (item.getItemId() == R.id.btn_perfilDueno) {
                loadFrag(new Perfil());
            }
            // Retorna true para indicar que el evento ha sido manejado
            return true;
        });
    }

    /**
     * Método auxiliar para reemplazar el fragment que se muestra dentro del contenedor
     * @param fragment Fragment que se quiere mostrar
     */
    private void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.commit(); // Ejecuta la transacción
    }
}
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

    BottomNavigationView bnv_dueno;
    FrameLayout frameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_dueno);

        bnv_dueno = findViewById(R.id.bnv_dueno);
        frameContainer = findViewById(R.id.frameContainer);

        bnv_dueno.setOnItemSelectedListener(item -> {
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
            return true;
        });
    }

    private void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.commit();
    }
}
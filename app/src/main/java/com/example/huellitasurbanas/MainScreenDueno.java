package com.example.huellitasurbanas;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreenDueno extends AppCompatActivity {

    BottomNavigationView bnv_dueno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_dueno);

        bnv_dueno = findViewById(R.id.bnv_dueno);

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
        transaction.replace(R.id.main, fragment);
        transaction.commit();
    }
}
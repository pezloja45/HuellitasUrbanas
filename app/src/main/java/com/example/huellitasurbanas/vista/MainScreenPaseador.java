package com.example.huellitasurbanas.vista;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.huellitasurbanas.R;
import com.example.huellitasurbanas.vista.fragment.Chat;
import com.example.huellitasurbanas.vista.fragment.ChatPaseador;
import com.example.huellitasurbanas.vista.fragment.PerfilPaseador;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreenPaseador extends AppCompatActivity {

    BottomNavigationView bnv_paseador;
    FrameLayout frameContainerPaseador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_paseador);

        bnv_paseador = findViewById(R.id.bnv_paseador);
        frameContainerPaseador = findViewById(R.id.frameContainerPaseador);

        loadFrag(new ChatPaseador());
        bnv_paseador.setSelectedItemId(R.id.bnt_chatPaseador);

        bnv_paseador.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bnt_chatPaseador) {
                loadFrag(new ChatPaseador());
            }
            if (item.getItemId() == R.id.btn_paseadorPerfil) {
                loadFrag(new PerfilPaseador());
            }
            return true;
        });
    }


    private void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainerPaseador, fragment);
        transaction.commit();
    }
}
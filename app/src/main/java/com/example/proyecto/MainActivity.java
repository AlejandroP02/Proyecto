package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto.databinding.ActivityMainBinding;
import com.example.proyecto.databinding.FragmentLlavesBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.WriterException;

import org.apache.commons.codec.binary.Base64;

import java.io.File;

/**
 * Clase principal que representa la actividad principal de la aplicación.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Objeto para acceder y manipular los elementos de la interfaz de usuario.
     */
    ActivityMainBinding binding;
    /**
     * Instancia del ViewModel utilizado en la actividad.
     */
    ViewModel viewModel;

    /**
     * Método llamado cuando la actividad se está iniciando. En este método se realiza
     * la inicialización de la interfaz de usuario y la configuración de la barra de herramientas.
     *
     * @param savedInstanceState Datos almacenados del estado anterior de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicialización del binding.
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        //signInClient.launch(GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).build()).getSignInIntent());

        // Configuración de la barra de herramientas.
        setSupportActionBar(binding.toolbar);

        // Configuración de la AppBar para la navegación entre fragmentos.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.homeFragment
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();
        // Inicialización del ViewModel.
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        // Configuración de la navegación con NavController y AppBar
        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

    }

}



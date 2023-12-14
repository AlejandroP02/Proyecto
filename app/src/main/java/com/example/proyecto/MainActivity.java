package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto.databinding.ActivityMainBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.WriterException;

import org.apache.commons.codec.binary.Base64;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        //signInClient.launch(GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).build()).getSignInIntent());

        setSupportActionBar(binding.toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.homeFragment
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        try {
            String base64QR = viewModel.generarQR("https://www.ejemplo.com");
            byte[] qrBytes = Base64.decodeBase64(base64QR);
            FirebaseStorage.getInstance().getReference("XD.jpg").putBytes(qrBytes);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }




        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
    }



}



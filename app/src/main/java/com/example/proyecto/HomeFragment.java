package com.example.proyecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Fragmento que representa la pantalla principal de la aplicación.
 */
public class HomeFragment extends Fragment {

    /**
     * Constructor público de la clase `HomeFragment`.
     * Se puede utilizar para proporcionar argumentos al fragmento si es necesario.
     */
    public HomeFragment() {
        // Constructor vacío requerido por la clase Fragment.
    }

    /**
     * Método llamado para realizar la inicialización del fragmento.
     * Puede ser utilizado para realizar tareas de inicialización específicas.
     *
     * @param savedInstanceState Datos almacenados del estado anterior del fragmento.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Método llamado para crear y devolver la vista asociada al fragmento.
     *
     * @param inflater Inflador utilizado para inflar el diseño de la vista.
     * @param container Contenedor padre en el que se debe colocar la vista.
     * @param savedInstanceState Datos almacenados del estado anterior del fragmento.
     * @return Vista creada para el fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño de la vista asociado al fragmento.
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}

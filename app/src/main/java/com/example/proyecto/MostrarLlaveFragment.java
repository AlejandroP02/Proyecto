package com.example.proyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.databinding.FragmentMostrarLlaveBinding;

/**
 * Fragmento que muestra los detalles de una llave seleccionada.
 */
public class MostrarLlaveFragment extends Fragment {
    /**
     * Objeto para acceder y manipular los elementos de la
     * interfaz de usuario.
     */
    private FragmentMostrarLlaveBinding binding;

    /**
     * Método llamado para crear y devolver la vista asociada
     * al fragmento.
     * @param inflater Inflador utilizado para inflar el
     *                diseño de la vista.
     * @param container Contenedor padre en el que se debe
     *                  colocar la vista.
     * @param savedInstanceState Datos almacenados del estado
     *                          anterior del fragmento.
     * @return Vista creada para el fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarLlaveBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Método llamado después de que la vista ha sido creada.
     * También recoge los datos de la entidad llave específica
     * y actualiza los elementos para que contengan la
     * información relacionada con la llave.
     * @param view Vista creada para el fragmento.
     * @param savedInstanceState Datos almacenados del estado
     *                           anterior del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicialización del ViewModel
        ViewModel elementosViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        // Observador para responder a cambios en la llave seleccionada
        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Llave>() {
            @Override
            public void onChanged(Llave llave) {
                // Actualizar la interfaz con los detalles de la llave seleccionada
                binding.aula.setText(llave.aula);
                binding.numero.setText(llave.num+"");
                Bitmap bitmap = BitmapFactory.decodeByteArray(llave.qr, 0, llave.qr.length);
                binding.qr.setImageBitmap(bitmap);
            }
        });
    }
}
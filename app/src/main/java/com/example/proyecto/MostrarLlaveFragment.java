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


public class MostrarLlaveFragment extends Fragment {
    private FragmentMostrarLlaveBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarLlaveBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModel elementosViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Llave>() {
            @Override
            public void onChanged(Llave llave) {
                binding.aula.setText(llave.aula);
                binding.numero.setText(llave.num+"");
                Bitmap bitmap = BitmapFactory.decodeByteArray(llave.qr, 0, llave.qr.length);
                binding.qr.setImageBitmap(bitmap);
            }
        });
    }
}
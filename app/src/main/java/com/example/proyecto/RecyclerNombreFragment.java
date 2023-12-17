package com.example.proyecto;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecyclerNombreFragment extends LlavesFragment{
    @Override
    LiveData<List<Llave>> obtenerLlaves() {
        return viewModel.aulaOrder();
    }
}

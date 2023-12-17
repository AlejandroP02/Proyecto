package com.example.proyecto;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Fragmento que muestra una lista de llaves
 * ordenadas por el nombre del aula.
 */
public class RecyclerNombreFragment extends LlavesFragment{
    /**
     * Método para obtener una lista observable
     * de todas las llaves ordenadas por el
     * nombre del aula.
     * @return Lista observable de llaves
     * ordenadas por el nombre del aula.
     */
    @Override
    LiveData<List<Llave>> obtenerLlaves() {
        return viewModel.aulaOrder();
    }
}

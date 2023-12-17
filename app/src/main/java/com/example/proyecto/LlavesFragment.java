package com.example.proyecto;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;

import com.example.proyecto.databinding.FragmentLlavesBinding;
import com.example.proyecto.databinding.ViewholderLlaveBinding;

import java.util.List;

/**
 * Fragmento que muestra la lista de llaves y
 * permite la navegación a otras pantallas.
 */
public class LlavesFragment extends Fragment {

    /**
     * Objeto para acceder y manipular los
     * elementos de la interfaz de usuario.
     */
    private FragmentLlavesBinding binding;
    /**
     * ViewModel asociado al fragmento.
     */
    protected ViewModel viewModel;
    /**
     * Controlador de navegación para la aplicación.
     */
    private NavController navController;

    /**
     * Método llamado para crear y devolver la vista
     * asociada al fragmento.
     * @param inflater Inflador utilizado para inflar
     *                el diseño de la vista.
     * @param container Contenedor padre en el que se
     *                  debe colocar la vista.
     * @param savedInstanceState Datos almacenados del
     *                           estado anterior del
     *                           fragmento.
     * @return Vista creada para el fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentLlavesBinding.inflate(inflater, container, false)).getRoot();

    }

    /**
     * Método llamado después de que la vista ha sido creada.
     * @param view Vista creada para el fragmento.
     * @param savedInstanceState Datos almacenados del
     *                           estado anterior del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización del ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        // Inicialización del controlador de navegación
        navController = Navigation.findNavController(view);

        // Configuración del evento de clic en el botón para ir a la pantalla de nueva llave
        binding.irANuevaLlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.anyadirFragment);
            }
        });

        // Inicialización del adaptador para el RecyclerView
        LlavesAdapter llavesAdapter = new LlavesAdapter();
        binding.recyclerView.setAdapter(llavesAdapter);

        // Observación de cambios en la lista de llaves y actualización del adaptador
        obtenerLlaves().observe(getViewLifecycleOwner(), new Observer<List<Llave>>() {

            @Override
            public void onChanged(List<Llave> llaves) {
                llavesAdapter.establecerLista(llaves);
            }
        });

        // Configuración del ItemTouchHelper para la eliminación de llaves mediante el deslizamiento
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Llave llave = llavesAdapter.obtenerLlave(posicion);
                viewModel.eliminar(llave);

            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    /**
     * Método para obtener el LiveData de la
     * lista de llaves desde el ViewModel.
     * @return LiveData de la lista de llaves.
     */
    LiveData<List<Llave>> obtenerLlaves(){
        return viewModel.obtener();
    }

    /**
     * Clase interna que representa un ViewHolder
     * para las llaves en el RecyclerView.
     */
    class LlaveViewHolder extends RecyclerView.ViewHolder {
        /**
         * Objeto para acceder y manipular los elementos
         * de la interfaz de usuario.
         */
        private final ViewholderLlaveBinding binding;

        /**
         * Constructor del ViewHolder.
         * @param binding Objeto para acceder y
         *                manipular los elementos
         *                de la interfaz de usuario.
         */
        public LlaveViewHolder(ViewholderLlaveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * Clase interna que representa un adaptador
     * para las llaves en el RecyclerView.
     */
    class LlavesAdapter extends RecyclerView.Adapter<LlaveViewHolder> {
        /**
         * Lista de llaves que se mostrarán
         * en el RecyclerView.
         */
        List<Llave> llaves;

        /**
         * Método llamado para crear un nuevo ViewHolder.
         * @param parent Grupo al que pertenece la vista.
         * @param viewType Tipo de la vista.
         * @return Nuevo ViewHolder creado.
         */
        @NonNull
        @Override
        public LlaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LlaveViewHolder(ViewholderLlaveBinding.inflate(getLayoutInflater(), parent, false));
        }

        /**
         * Método llamado para vincular un ViewHolder
         * existente con datos.
         * @param holder ViewHolder a vincular.
         * @param position Posición del elemento en el
         *                 conjunto de datos.
         */
        @Override
        public void onBindViewHolder(@NonNull LlaveViewHolder holder, int position) {
            // Llave a mostrar.
            Llave llave = llaves.get(position);

            // Configuración de los datos en el ViewHolder.
            holder.binding.aula.setText(llave.aula);

            // Configuración del evento de clic en el elemento para mostrar detalles.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.seleccionar(llave);
                    navController.navigate(R.id.mostrarLlaveFragment);
                }
            });
        }

        /**
         * Método que devuelve la cantidad total de
         * elementos en el conjunto de datos.
         * @return Cantidad total de elementos.
         */
        @Override
        public int getItemCount() {
            return llaves != null ? llaves.size() : 0;
        }

        /**
         * Método para establecer la lista de llaves
         * y notificar cambios al adaptador.
         * @param llaves Lista de llaves a establecer.
         */
        public void establecerLista(List<Llave> llaves){
            this.llaves = llaves;
            notifyDataSetChanged();
        }

        /**
         * Método para obtener una llave en una
         * posición específica en la lista.
         * @param posicion Posición de la llave
         *                en la lista.
         * @return Llave en la posición especificada.
         */
        public Llave obtenerLlave(int posicion){
            return llaves.get(posicion);
        }
    }

}
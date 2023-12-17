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

public class LlavesFragment extends Fragment {

    private FragmentLlavesBinding binding;
    protected ViewModel viewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentLlavesBinding.inflate(inflater, container, false)).getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        binding.irANuevaLlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.anyadirFragment);
            }
        });
        LlavesAdapter llavesAdapter = new LlavesAdapter();
        binding.recyclerView.setAdapter(llavesAdapter);

        obtenerLlaves().observe(getViewLifecycleOwner(), new Observer<List<Llave>>() {

            @Override
            public void onChanged(List<Llave> llaves) {
                llavesAdapter.establecerLista(llaves);
            }
        });

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
    LiveData<List<Llave>> obtenerLlaves(){
        return viewModel.obtener();
    }
    class LlaveViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderLlaveBinding binding;

        public LlaveViewHolder(ViewholderLlaveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class LlavesAdapter extends RecyclerView.Adapter<LlaveViewHolder> {

        List<Llave> llaves;

        @NonNull
        @Override
        public LlaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LlaveViewHolder(ViewholderLlaveBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LlaveViewHolder holder, int position) {

            Llave llave = llaves.get(position);

            holder.binding.aula.setText(llave.aula);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.seleccionar(llave);
                    navController.navigate(R.id.mostrarLlaveFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return llaves != null ? llaves.size() : 0;
        }

        public void establecerLista(List<Llave> llaves){
            this.llaves = llaves;
            notifyDataSetChanged();
        }
        public Llave obtenerLlave(int posicion){
            return llaves.get(posicion);
        }
    }

}
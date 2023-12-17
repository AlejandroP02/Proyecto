package com.example.proyecto;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyecto.databinding.FragmentAnyadirBinding;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.WriterException;

public class AnyadirFragment extends Fragment {

    private FragmentAnyadirBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentAnyadirBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        NavController navController = Navigation.findNavController(view);

        Button boton = binding.crear;
        binding.crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean datosCorrectos=true;
                String aula = null;
                int num=0;
                aula = binding.aula.getText().toString();
                try {
                    num = Integer.parseInt(binding.numero.getText().toString());
                }catch (NumberFormatException e){
                    if(num<=0){
                        binding.numero.setError("Escribe un numero valido");
                        datosCorrectos=false;
                    }
                }
                byte[] qr= new byte[0];
                if(aula==null || aula.isEmpty()){
                    binding.aula.setError("Escribe un aula");
                    datosCorrectos=false;
                }
                if (datosCorrectos){
                    try {
                        qr = viewModel.generarQR(aula+":"+num);
                        FirebaseStorage.getInstance().getReference(aula+".jpg").putBytes(qr);
                        viewModel.insertar(new Llave(aula, num, qr));
                        binding.mostrarQr.setImageBitmap(BitmapFactory.decodeByteArray(qr, 0, qr.length));
                        boton.setVisibility(View.INVISIBLE);
                        binding.carga.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                navController.popBackStack();
                            }
                        }, 1500); // 3 segundos de retraso
                    } catch (WriterException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        });
    }
}
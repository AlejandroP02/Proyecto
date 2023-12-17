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

/**
 * Fragmento que permite agregar nuevas llaves a la aplicación.
 */
public class AnyadirFragment extends Fragment {

    /**
     * Objeto para acceder y manipular los elementos de la interfaz de usuario.
     */
    private FragmentAnyadirBinding binding;

    /**
     * Método llamado para crear y devolver la vista asociada al fragmento.
     * @param inflater Inflador utilizado para inflar el diseño de la vista.
     * @param container Contenedor padre en el que se debe colocar la vista.
     * @param savedInstanceState Datos almacenados del estado anterior del fragmento.
     * @return Vista creada para el fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentAnyadirBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Método llamado después de que la vista ha sido creada.
     * Se encarga de guardar los datos ingresados de las
     * llaves y formar un código QR con la información.
     * @param view Vista creada para el fragmento.
     * @param savedInstanceState Datos almacenados del estado anterior del fragmento.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización del ViewModel y NavController
        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Configuración del botón y su acción al hacer clic
        Button boton = binding.crear;
        binding.crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validación de datos
                boolean datosCorrectos = true;
                String aula = null;
                int num = 0;
                aula = binding.aula.getText().toString();
                try {
                    num = Integer.parseInt(binding.numero.getText().toString());
                } catch (NumberFormatException e) {
                    if (num <= 0) {
                        binding.numero.setError("Escribe un numero valido");
                        datosCorrectos = false;
                    }
                }
                byte[] qr = new byte[0];
                if (aula == null || aula.isEmpty()) {
                    binding.aula.setError("Escribe un aula");
                    datosCorrectos = false;
                }

                // Acciones si los datos son correctos
                if (datosCorrectos){
                    try {
                        // Generar QR y almacenar en Firebase Storage
                        qr = viewModel.generarQR(aula+":"+num);
                        FirebaseStorage.getInstance().getReference(aula+".jpg").putBytes(qr);
                        // Insertar nueva llave en la base de datos
                        viewModel.insertar(new Llave(aula, num, qr));
                        // Mostrar QR en la interfaz
                        binding.mostrarQr.setImageBitmap(BitmapFactory.decodeByteArray(qr, 0, qr.length));
                        // Ocultar botón y mostrar barra de carga
                        boton.setVisibility(View.INVISIBLE);
                        binding.carga.setVisibility(View.VISIBLE);
                        // Retraso antes de navegar hacia atrás en la pila de fragmentos
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
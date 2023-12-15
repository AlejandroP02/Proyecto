package com.example.proyecto;

import android.app.Application;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidx.lifecycle.MutableLiveData;

import java.util.List;


import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;



public class ViewModel extends AndroidViewModel {

    public ViewModel(@NonNull Application application) {
        super(application);
        llavesRepositorio = new LlavesRepositorio();

        listLlavesMutableLiveData.setValue(llavesRepositorio.obtener());
    }

    public byte[] generarQR(String data) throws WriterException {
        int width = 300;
        int height = 300;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Convierte la matriz de bits en una imagen Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        // Convierte el Bitmap a bytes en formato PNG
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        // Convierte los bytes a una cadena base64
        byte[] byteArray = outputStream.toByteArray();
        return byteArray;
    }
    private void showLoadingIcon(long durationMillis) {
        final ProgressDialog progressDialog = new ProgressDialog(this.getApplication());
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, durationMillis);
    }
    LlavesRepositorio llavesRepositorio;

    MutableLiveData<List<Llave>> listLlavesMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Llave> llaveSeleccionada = new MutableLiveData<>();

    void seleccionar(Llave llave){
        llaveSeleccionada.setValue(llave);
    }

    MutableLiveData<Llave> seleccionado(){
        return llaveSeleccionada;
    }


    MutableLiveData<List<Llave>> obtener(){
        return listLlavesMutableLiveData;
    }

    void insertar(Llave llave){
        llavesRepositorio.insertar(llave, new LlavesRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Llave> llaves) {
                listLlavesMutableLiveData.setValue(llaves);
            }
        });
    }

    void eliminar(Llave llave){
        llavesRepositorio.eliminar(llave, new LlavesRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Llave> llaves) {
                listLlavesMutableLiveData.setValue(llaves);
            }
        });
    }

    void actualizar(Llave llave){
        llavesRepositorio.actualizar(llave, new LlavesRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Llave> llaves) {
                listLlavesMutableLiveData.setValue(llaves);
            }
        });
    }
}

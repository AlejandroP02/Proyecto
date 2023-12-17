package com.example.proyecto;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.functions.Function1;

/**
 * ViewModel que actúa como intermediario entre
 * la interfaz de usuario y el repositorio,
 * proporcionando datos a la interfaz de
 * usuario y gestionando las operaciones de negocio.
 */
public class ViewModel extends AndroidViewModel {
    /**
     * Repositorio para realizar operaciones de acceso a datos.
     */
    LlavesRepositorio llavesRepositorio;
    /**
     * LiveData que representa la llave actualmente seleccionada.
     */
    MutableLiveData<Llave> llaveSeleccionada = new MutableLiveData<>();

    /**
     * Constructor de la clase `ViewModel`.
     * @param application Instancia de la aplicación.
     */
    public ViewModel(@NonNull Application application) {
        super(application);
        llavesRepositorio = new LlavesRepositorio(application);

    }

    /**
     * Método para generar un código QR a
     * partir de una cadena de datos.
     * @param data Cadena de datos para generar
     *             el código QR.
     * @return Arreglo de bytes que representa el
     * código QR en formato PNG.
     * @throws WriterException Excepción lanzada si hay
     * un error al generar el código QR.
     */
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
        byte[] byteArray = outputStream.toByteArray();
        return byteArray;
    }

    /**
     * Método para seleccionar una llave y actualizar
     * el LiveData correspondiente.
     * @param llave Llave a seleccionar.
     */
    void seleccionar(Llave llave){
        llaveSeleccionada.setValue(llave);
    }

    /**
     * Método para obtener el LiveData de la llave
     * actualmente seleccionada.
     * @return LiveData de la llave seleccionada.
     */
    MutableLiveData<Llave> seleccionado(){
        return llaveSeleccionada;
    }

    /**
     * Método para obtener una lista observable de
     * todas las llaves almacenadas en el repositorio.
     * @return Lista observable de llaves.
     */
    LiveData<List<Llave>> obtener(){
        return llavesRepositorio.obtener();
    }

    /**
     * Método para insertar una nueva llave
     * en el repositorio.
     * @param llave Objeto de tipo `Llave` a insertar.
     */
    void insertar(Llave llave){
        llavesRepositorio.insertar(llave);
    }

    /**
     * Método para eliminar una llave existente
     * en el repositorio.
     * @param llave Objeto de tipo `Llave` a eliminar.
     */
    void eliminar(Llave llave){
        llavesRepositorio.eliminar(llave);
    }

    /**
     * Método para obtener una lista observable
     * de todas las llaves ordenadas por el nombre
     * del aula.
     * @return Lista observable de llaves ordenadas
     * por el nombre del aula.
     */
    LiveData<List<Llave>> aulaOrder(){
        return llavesRepositorio.aulaOrder();
    }

}

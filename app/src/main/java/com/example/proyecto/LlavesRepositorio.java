package com.example.proyecto;

import android.app.*;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Clase que actúa como un repositorio para gestionar
 * las operaciones de acceso a datos
 * relacionadas con la entidad `Llave`.
 */
public class LlavesRepositorio {
    /**
     * Executor para realizar operaciones en un hilo de fondo.
     */
    Executor executor = Executors.newSingleThreadExecutor();
    /**
     * Objeto DAO para realizar operaciones en la base de datos.
     */
    BaseDeDatos.LlavesDao llavesDao;

    /**
     * Constructor de la clase `LlavesRepositorio`.
     * @param application Instancia de la aplicación
     *                    para obtener la base de datos.
     */
    LlavesRepositorio(Application application){
        llavesDao = BaseDeDatos.obtenerInstancia(application).obtenerLlavesDao();
    }

    /**
     * Método para obtener una lista observable de todas las
     * llaves almacenadas en la base de datos.
     * @return Lista observable de llaves.
     */
    LiveData<List<Llave>> obtener(){
        return llavesDao.obtener();
    }

    /**
     * Método para insertar una nueva llave en la base
     * de datos en un hilo de fondo.
     * @param llave Objeto de tipo `Llave` a insertar.
     */
    void insertar(Llave llave){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                llavesDao.insertar(llave);
            }
        });
    }
    /**
     * Método para eliminar una llave existente en
     * la base de datos en un hilo de fondo.
     * @param llave Objeto de tipo `Llave` a eliminar.
     */
    void eliminar(Llave llave) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                llavesDao.eliminar(llave);
            }
        });
    }

    /**
     * Método para obtener una lista observable de
     * todas las llaves ordenadas por el nombre del
     * aula de forma descendente.
     * @return Lista observable de llaves ordenadas por el nombre del aula.
     */
    LiveData<List<Llave>> aulaOrder() {
        return llavesDao.aulaOrder();
    }

}
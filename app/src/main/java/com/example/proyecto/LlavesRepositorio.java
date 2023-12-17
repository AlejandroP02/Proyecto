package com.example.proyecto;

import android.app.*;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LlavesRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();


    BaseDeDatos.LlavesDao llavesDao;
    LlavesRepositorio(Application application){
        llavesDao = BaseDeDatos.obtenerInstancia(application).obtenerLlavesDao();
    }


    LiveData<List<Llave>> obtener(){
        return llavesDao.obtener();
    }

    void insertar(Llave llave){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                llavesDao.insertar(llave);
            }
        });
    }

    void eliminar(Llave llave) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                llavesDao.eliminar(llave);
            }
        });
    }

    LiveData<List<Llave>> aulaOrder() {
        return llavesDao.aulaOrder();
    }

}
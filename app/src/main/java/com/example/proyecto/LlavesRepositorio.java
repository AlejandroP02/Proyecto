package com.example.proyecto;

import java.util.ArrayList;
import java.util.List;

public class LlavesRepositorio {

    List<Llave> llaves = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Llave> elementos);
    }

    LlavesRepositorio(){
        /*llaves.add(new Llave("A0", 100));
        llaves.add(new Llave("A1", 101));
        llaves.add(new Llave("A2", 102));
        llaves.add(new Llave("A3", 103));

         */
    }

    List<Llave> obtener() {
        return llaves;
    }

    void insertar(Llave llave, Callback callback){
        llaves.add(llave);
        callback.cuandoFinalice(llaves);
    }

    void eliminar(Llave llave, Callback callback) {
        llaves.remove(llave);
        callback.cuandoFinalice(llaves);
    }

    void actualizar(Llave llave, Callback callback) {
        callback.cuandoFinalice(llaves);
    }
}
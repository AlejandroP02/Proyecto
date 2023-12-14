package com.example.proyecto;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Database(entities = { Llave.class }, version = 1, exportSchema = false)
public abstract class BaseDeDatos extends RoomDatabase {
    private static volatile BaseDeDatos INSTANCIA;

    static BaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (BaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                                    BaseDeDatos.class, "llaves.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }


    //public abstract LlavesDao obtenerLlavesDao();


    @Dao
    interface LavessDao {
        @Query("SELECT * FROM Llave")
        LiveData<List<Llave>> obtener();

        @Insert
        void insertar(Llave llave);

        @Update
        void actualizar(Llave llave);

        @Delete
        void eliminar(Llave llave);
    }
}
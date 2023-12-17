package com.example.proyecto;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

/**
 * Clase que representa la base de datos de la aplicación.
 */
@Database(entities = { Llave.class }, version = 1, exportSchema = false)
public abstract class BaseDeDatos extends RoomDatabase {
    /**
     * Instancia única de la base de datos.
     */
    private static volatile BaseDeDatos INSTANCIA;

    /**
     * Método estático para obtener la instancia única de la
     * base de datos utilizando el patrón Singleton.
     * @param context Contexto de la aplicación.
     * @return Instancia única de la base de datos.
     */
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

    /**
     * Método abstracto para obtener el objeto DAO
     * asociado a la entidad `Llave`.
     * @return Objeto DAO para la entidad `Llave`.
     */
    public abstract LlavesDao obtenerLlavesDao();

    /**
     * Interfaz que define las operaciones de acceso
     * a datos (DAO) para la entidad `Llave`.
     */
    @Dao
    interface LlavesDao {
        /**
         * Consulta que retorna una lista observable de
         * todas las llaves almacenadas en la base de datos.
         * @return Lista observable de llaves.
         */
        @Query("SELECT * FROM Llave")
        LiveData<List<Llave>> obtener();
        /**
         * Operación de inserción de una nueva llave
         * en la base de datos.
         * @param llave Objeto de tipo `Llave` a insertar.
         */
        @Insert
        void insertar(Llave llave);
        /**
         * Operación de actualización de una llave existente
         * en la base de datos.
         * @param llave Objeto de tipo `Llave` a actualizar.
         */
        @Update
        void actualizar(Llave llave);
        /**
         * Operación de eliminación de una llave existente
         * en la base de datos.
         * @param llave Objeto de tipo `Llave` a eliminar.
         */
        @Delete
        void eliminar(Llave llave);
        /**
         * Consulta que retorna una lista observable de
         * todas las llaves ordenadas por el nombre del
         * aula de forma descendente.
         * @return Lista observable de llaves ordenadas por el nombre del aula.
         */
        @Query("SELECT * FROM Llave ORDER BY aula DESC")
        LiveData<List<Llave>> aulaOrder();

    }
}
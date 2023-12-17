package com.example.proyecto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Clase que representa una entidad de base de datos para almacenar información sobre una llave.
 */
@Entity
public class Llave {
    /**
     * Identificador único de la llave, generado automáticamente.
     */
    @PrimaryKey(autoGenerate = true)
    int id;
    /**
     * Nombre del aula asociada a la llave.
     */
    String aula;
    /**
     * Número de la llave.
     */
    int num;
    /**
     * Datos binarios del código QR asociado a la llave.
     */
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] qr;

    /**
     * Constructor de la clase que inicializa los atributos de la llave.
     * @param aula Nombre del aula asociada a la llave.
     * @param num Número de la llave.
     * @param qr Datos binarios del código QR asociado a la llave.
     */
    public Llave(String aula, int num, byte[] qr) {
        this.aula = aula;
        this.num = num;
        this.qr= qr;
    }

}

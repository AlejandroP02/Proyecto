package com.example.proyecto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Llave {
    @PrimaryKey(autoGenerate = true)
    int id;
    String aula;
    int num;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] qr;


    public Llave(String aula, int num, byte[] qr) {
        this.aula = aula;
        this.num = num;
        this.qr= qr;
    }

}

package com.example.proyecto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Llave {
    @PrimaryKey(autoGenerate = true)
    int id;
    String aula;
    int num;
    String qr;


    public Llave(String aula, int num) {
        this.aula = aula;
        this.num = num;
    }

}

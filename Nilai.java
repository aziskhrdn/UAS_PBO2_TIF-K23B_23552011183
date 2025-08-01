/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirakademik_noer_azis;


public class Nilai {
    private int id;
    private int mahasiswaId;
    private String mataKuliah;
    private double nilaiAngka;
    private String nilaiHuruf;

    public Nilai(int id, int mahasiswaId, String mataKuliah, double nilaiAngka, String nilaiHuruf) {
        this.id = id;
        this.mahasiswaId = mahasiswaId;
        this.mataKuliah = mataKuliah;
        this.nilaiAngka = nilaiAngka;
        this.nilaiHuruf = nilaiHuruf;
    }

    public int getId() { return id; }
    public int getMahasiswaId() { return mahasiswaId; }
    public String getMataKuliah() { return mataKuliah; }
    public double getNilaiAngka() { return nilaiAngka; }
    public String getNilaiHuruf() { return nilaiHuruf; }
}

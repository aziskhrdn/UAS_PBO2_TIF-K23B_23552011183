/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirakademik_noer_azis;


public class Jadwal {
    private int id;
    private String mataKuliah;
    private String dosen;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String ruang;

    public Jadwal(int id, String mataKuliah, String dosen, String hari, String jamMulai, String jamSelesai, String ruang) {
        this.id = id;
        this.mataKuliah = mataKuliah;
        this.dosen = dosen;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.ruang = ruang;
    }

    public int getId() { return id; }
    public String getMataKuliah() { return mataKuliah; }
    public String getDosen() { return dosen; }
    public String getHari() { return hari; }
    public String getJamMulai() { return jamMulai; }
    public String getJamSelesai() { return jamSelesai; }
    public String getRuang() { return ruang; }
}


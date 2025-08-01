package com.mycompany.kasirakademik_noer_azis;

public abstract class Sivitas {
    protected int id;
    protected String nama;

    public Sivitas(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }

    // Bisa ditambahkan method abstrak jika ingin dipolymorph
}

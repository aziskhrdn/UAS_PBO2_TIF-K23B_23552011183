package com.mycompany.kasirakademik_noer_azis;

public class Dosen extends Civitas {
    private String nidn;

    public Dosen(int id, String nama, String nidn) {
        super(id, nama);
        this.nidn = nidn;
    }

    public String getNidn() { return nidn; }
    public void setNidn(String nidn) { this.nidn = nidn; }
}


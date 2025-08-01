package com.mycompany.kasirakademik_noer_azis;

public class Mahasiswa {
    private int id;
    private String nama;
    private String nim;
    private int userId;

    // Konstruktor lengkap
    public Mahasiswa(int id, String nama, String nim, int userId) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.userId = userId;
    }

    // Konstruktor tanpa id (misal untuk insert baru)
    public Mahasiswa(String nama, String nim, int userId) {
        this.nama = nama;
        this.nim = nim;
        this.userId = userId;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", nim='" + nim + '\'' +
                ", userId=" + userId +
                '}';
    }
}

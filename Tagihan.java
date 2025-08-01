package com.mycompany.kasirakademik_noer_azis;

/**
 * Model untuk data Tagihan Mahasiswa.
 */
public class Tagihan {
    private int id;
    private int mahasiswaId;
    private String semester;
    private int total;
    private String lunas; // "Lunas" atau "Belum Lunas"

    public Tagihan(int id, int mahasiswaId, String semester, int total, String lunas) {
        this.id = id;
        this.mahasiswaId = mahasiswaId;
        this.semester = semester;
        this.total = total;
        this.lunas = lunas;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getMahasiswaId() {
        return mahasiswaId;
    }

    public String getSemester() {
        return semester;
    }

    public int getTotal() {
        return total;
    }

    public String getLunas() {
        return lunas;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setMahasiswaId(int mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setLunas(String lunas) {
        this.lunas = lunas;
    }

    @Override
    public String toString() {
        return "Tagihan{id=" + id +
               ", mahasiswaId=" + mahasiswaId +
               ", semester='" + semester + '\'' +
               ", total=" + total +
               ", lunas='" + lunas + '\'' +
               '}';
    }
}

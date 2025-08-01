package com.mycompany.kasirakademik_noer_azis;

public class TagihanMahasiswa {
    private int tagihanId;
    private int mahasiswaId;
    private String namaMahasiswa;
    private String nim;
    private int semester;
    private double total;
    private boolean lunas;

    public TagihanMahasiswa(int tagihanId, int mahasiswaId, String namaMahasiswa, String nim, int semester, double total, boolean lunas) {
        this.tagihanId = tagihanId;
        this.mahasiswaId = mahasiswaId;
        this.namaMahasiswa = namaMahasiswa;
        this.nim = nim;
        this.semester = semester;
        this.total = total;
        this.lunas = lunas;
    }

    public int getTagihanId() { return tagihanId; }
    public int getMahasiswaId() { return mahasiswaId; }
    public String getNamaMahasiswa() { return namaMahasiswa; }
    public String getNim() { return nim; }
    public int getSemester() { return semester; }
    public double getTotal() { return total; }
    public boolean isLunas() { return lunas; }
}

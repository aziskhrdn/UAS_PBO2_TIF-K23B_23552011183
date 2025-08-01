package com.mycompany.kasirakademik_noer_azis;

import java.time.LocalDate;

/**
 * Model untuk data Transaksi Pembayaran Tagihan.
 */
public class Transaksi {
    private int id;
    private int tagihanId;
    private double jumlahBayar;
    private LocalDate tanggalBayar;

    public Transaksi(int id, int tagihanId, double jumlahBayar, LocalDate tanggalBayar) {
        this.id = id;
        this.tagihanId = tagihanId;
        this.jumlahBayar = jumlahBayar;
        this.tanggalBayar = tanggalBayar;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTagihanId() {
        return tagihanId;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public LocalDate getTanggalBayar() {
        return tanggalBayar;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTagihanId(int tagihanId) {
        this.tagihanId = tagihanId;
    }

    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public void setTanggalBayar(LocalDate tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    @Override
    public String toString() {
        return "Transaksi{id=" + id +
               ", tagihanId=" + tagihanId +
               ", jumlahBayar=" + jumlahBayar +
               ", tanggalBayar=" + tanggalBayar +
               '}';
    }
}

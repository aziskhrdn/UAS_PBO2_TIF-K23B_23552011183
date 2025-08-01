package com.mycompany.kasirakademik_noer_azis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Kelas DataDummy sebagai tempat data statis (dummy) pengganti database.
 */
public class DataDummy {

    public static List<Mahasiswa> mahasiswaList = new ArrayList<>();
    public static List<Dosen> dosenList = new ArrayList<>();
    public static List<Tagihan> tagihanList = new ArrayList<>();
    public static List<Transaksi> transaksiList = new ArrayList<>();
    public static List<Nilai> nilaiList = new ArrayList<>();
    public static List<Jadwal> jadwalList = new ArrayList<>();

    static {
        // Mahasiswa(id, nama, nim, tahunMasuk) -- contoh dengan 4 parameter
        mahasiswaList.add(new Mahasiswa(1, "Andi", "1903003", 2021));
        mahasiswaList.add(new Mahasiswa(2, "Budi", "1903004", 2020));

        // Dosen(id, nama, nidn)
        dosenList.add(new Dosen(1, "Dr. Susi", "NIDN001"));
        dosenList.add(new Dosen(2, "Dr. Joko", "NIDN002"));

        // Tagihan(id, mahasiswaId, semester, total, statusLunas)
        tagihanList.add(new Tagihan(1, 1, "2023-1", 1_500_000, "Belum Lunas"));
        tagihanList.add(new Tagihan(2, 2, "2023-1", 1_600_000, "Belum Lunas"));

        // Transaksi(id, tagihanId, jumlahBayar, tanggal)
        transaksiList.add(new Transaksi(1, 1, 500_000, LocalDate.of(2023, 2, 15)));
        transaksiList.add(new Transaksi(2, 1, 1_000_000, LocalDate.of(2023, 3, 10)));

        // Nilai(id, mahasiswaId, mataKuliah, nilaiAngka, nilaiHuruf)
        nilaiList.add(new Nilai(1, 1, "Pemrograman Java", 90, "A"));
        nilaiList.add(new Nilai(2, 1, "Basis Data", 85, "A-"));
        nilaiList.add(new Nilai(3, 2, "Pemrograman Java", 75, "B"));

        // Jadwal(id, mataKuliah, dosen, hari, jamMulai, jamSelesai, ruang)
        jadwalList.add(new Jadwal(1, "Pemrograman Java", "Dr. Susi", "Senin", "08:00", "10:00", "R101"));
        jadwalList.add(new Jadwal(2, "Basis Data", "Dr. Joko", "Rabu", "10:00", "12:00", "R102"));
    }

}


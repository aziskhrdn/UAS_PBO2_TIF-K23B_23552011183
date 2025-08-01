package com.mycompany.kasirakademik_noer_azis;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DataNilaiDanJadwalPane extends VBox {

    private TableView<Nilai> tabelNilai;
    private TableView<Jadwal> tabelJadwal;
    private int mahasiswaId;

    public DataNilaiDanJadwalPane(int mahasiswaId) {
        this.mahasiswaId = mahasiswaId;

        setPadding(new Insets(10));
        setSpacing(10);

        // Label dan Tabel Nilai
        Label lblJudulNilai = new Label("Data Nilai");
        tabelNilai = new TableView<>();

        TableColumn<Nilai, String> colMatkul = new TableColumn<>("Mata Kuliah");
        colMatkul.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMataKuliah()));

        TableColumn<Nilai, Double> colNilaiAngka = new TableColumn<>("Nilai Angka");
        colNilaiAngka.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getNilaiAngka()));

        TableColumn<Nilai, String> colNilaiHuruf = new TableColumn<>("Nilai Huruf");
        colNilaiHuruf.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNilaiHuruf()));

        tabelNilai.getColumns().addAll(colMatkul, colNilaiAngka, colNilaiHuruf);

        // Label dan Tabel Jadwal
        Label lblJudulJadwal = new Label("Jadwal Kuliah");
        tabelJadwal = new TableView<>();

        TableColumn<Jadwal, String> colMatkulJadwal = new TableColumn<>("Mata Kuliah");
        colMatkulJadwal.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMataKuliah()));

        TableColumn<Jadwal, String> colDosen = new TableColumn<>("Dosen");
        colDosen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDosen()));

        TableColumn<Jadwal, String> colHari = new TableColumn<>("Hari");
        colHari.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHari()));

        TableColumn<Jadwal, String> colJamMulai = new TableColumn<>("Jam Mulai");
        colJamMulai.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getJamMulai()));

        TableColumn<Jadwal, String> colJamSelesai = new TableColumn<>("Jam Selesai");
        colJamSelesai.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getJamSelesai()));

        TableColumn<Jadwal, String> colRuang = new TableColumn<>("Ruang");
        colRuang.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRuang()));

        tabelJadwal.getColumns().addAll(colMatkulJadwal, colDosen, colHari, colJamMulai, colJamSelesai, colRuang);

        // Tombol Refresh
        Button btnRefresh = new Button("Refresh Data");
        btnRefresh.setOnAction(e -> refreshData());

        // Tambahkan elemen ke layout
        getChildren().addAll(
                lblJudulNilai, tabelNilai,
                lblJudulJadwal, tabelJadwal,
                btnRefresh
        );

        // Load data awal
        refreshData();
    }

    // Method untuk muat ulang data pada tabel
    private void refreshData() {
        // Refresh tabel nilai khusus mahasiswaId
        tabelNilai.getItems().clear();
        DataDummy.nilaiList.stream()
                .filter(n -> n.getMahasiswaId() == mahasiswaId)
                .forEach(tabelNilai.getItems()::add);

        // Refresh tabel jadwal (isi semua)
        tabelJadwal.getItems().clear();
        tabelJadwal.getItems().addAll(DataDummy.jadwalList);
    }
}

package com.mycompany.kasirakademik_noer_azis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalDate;

public class DataTransaksiPane extends VBox {

    private TableView<Transaksi> tableView;
    private ObservableList<Transaksi> list;

    private TextField tfTagihanId, tfJumlahBayar;
    private DatePicker dpTanggalBayar;

    private Button btnTambah, btnUpdate, btnHapus;

    private Transaksi selectedTransaksi = null;

    public DataTransaksiPane() {
        setPadding(new Insets(10));
        setSpacing(10);

        // Tabel
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Transaksi, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaksi, Integer> colTagihanId = new TableColumn<>("Tagihan ID");
        colTagihanId.setCellValueFactory(new PropertyValueFactory<>("tagihanId"));

        TableColumn<Transaksi, Double> colJumlahBayar = new TableColumn<>("Jumlah Bayar");
        colJumlahBayar.setCellValueFactory(new PropertyValueFactory<>("jumlahBayar"));

        TableColumn<Transaksi, LocalDate> colTanggalBayar = new TableColumn<>("Tanggal Bayar");
        colTanggalBayar.setCellValueFactory(new PropertyValueFactory<>("tanggalBayar"));

        tableView.getColumns().addAll(colId, colTagihanId, colJumlahBayar, colTanggalBayar);
        tableView.setPrefHeight(250);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedTransaksi = newSel;
                tfTagihanId.setText(String.valueOf(newSel.getTagihanId()));
                tfJumlahBayar.setText(String.valueOf(newSel.getJumlahBayar()));
                dpTanggalBayar.setValue(newSel.getTanggalBayar());

                btnTambah.setDisable(true);
                btnUpdate.setDisable(false);
                btnHapus.setDisable(false);
            }
        });

        // Form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        tfTagihanId = new TextField();
        tfJumlahBayar = new TextField();
        dpTanggalBayar = new DatePicker();

        form.add(new Label("Tagihan ID:"), 0, 0);
        form.add(tfTagihanId, 1, 0);
        form.add(new Label("Jumlah Bayar:"), 0, 1);
        form.add(tfJumlahBayar, 1, 1);
        form.add(new Label("Tanggal Bayar:"), 0, 2);
        form.add(dpTanggalBayar, 1, 2);

        // Tombol
        btnTambah = new Button("Tambah");
        btnUpdate = new Button("Update");
        btnHapus = new Button("Hapus");

        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);

        btnTambah.setOnAction(e -> tambahTransaksi());
        btnUpdate.setOnAction(e -> updateTransaksi());
        btnHapus.setOnAction(e -> hapusTransaksi());

        HBox buttonBox = new HBox(10, btnTambah, btnUpdate, btnHapus);

        getChildren().addAll(tableView, form, buttonBox);

        loadData();
    }

    private void loadData() {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM transaksi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Transaksi(
                        rs.getInt("id"),
                        rs.getInt("tagihan_id"),
                        rs.getDouble("jumlah_bayar"),
                        rs.getDate("tanggal_bayar").toLocalDate()
                ));
            }

            tableView.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Gagal memuat data transaksi.");
        }
    }

    private void tambahTransaksi() {
        try {
            int tagihanId = Integer.parseInt(tfTagihanId.getText().trim());
            double jumlah = Double.parseDouble(tfJumlahBayar.getText().trim());
            LocalDate tanggal = dpTanggalBayar.getValue();

            if (tanggal == null) {
                alertError("Tanggal bayar tidak boleh kosong.");
                return;
            }

            if (!tagihanExists(tagihanId)) {
                alertError("Tagihan ID tidak ditemukan di database.");
                return;
            }

            String sql = "INSERT INTO transaksi (tagihan_id, jumlah_bayar, tanggal_bayar) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, tagihanId);
                ps.setDouble(2, jumlah);
                ps.setDate(3, Date.valueOf(tanggal));

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    updateStatusLunasJikaPerlu(tagihanId, conn);
                    alertInfo("Transaksi berhasil ditambahkan.");
                    clearForm();
                    loadData();
                }

            }

        } catch (NumberFormatException e) {
            alertError("Tagihan ID dan Jumlah Bayar harus berupa angka.");
        } catch (Exception e) {
            e.printStackTrace();
            alertError("Gagal menambahkan transaksi.");
        }
    }

    private void updateTransaksi() {
        if (selectedTransaksi == null) return;

        try {
            int tagihanId = Integer.parseInt(tfTagihanId.getText().trim());
            double jumlah = Double.parseDouble(tfJumlahBayar.getText().trim());
            LocalDate tanggal = dpTanggalBayar.getValue();

            if (tanggal == null) {
                alertError("Tanggal bayar tidak boleh kosong.");
                return;
            }

            if (!tagihanExists(tagihanId)) {
                alertError("Tagihan ID tidak ditemukan di database.");
                return;
            }

            String sql = "UPDATE transaksi SET tagihan_id = ?, jumlah_bayar = ?, tanggal_bayar = ? WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, tagihanId);
                ps.setDouble(2, jumlah);
                ps.setDate(3, Date.valueOf(tanggal));
                ps.setInt(4, selectedTransaksi.getId());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    updateStatusLunasJikaPerlu(tagihanId, conn);
                    alertInfo("Transaksi berhasil diupdate.");
                    clearForm();
                    loadData();
                }

            }

        } catch (NumberFormatException e) {
            alertError("Tagihan ID dan Jumlah Bayar harus berupa angka.");
        } catch (Exception e) {
            e.printStackTrace();
            alertError("Gagal mengupdate transaksi.");
        }
    }

    private void hapusTransaksi() {
        if (selectedTransaksi == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Yakin hapus transaksi ini?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String sql = "DELETE FROM transaksi WHERE id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setInt(1, selectedTransaksi.getId());
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        alertInfo("Transaksi berhasil dihapus.");
                        clearForm();
                        loadData();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    alertError("Gagal menghapus transaksi.");
                }
            }
        });
    }

    private boolean tagihanExists(int tagihanId) {
        String sql = "SELECT COUNT(*) FROM tagihan WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tagihanId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateStatusLunasJikaPerlu(int tagihanId, Connection conn) {
        try {
            String totalSql = "SELECT total FROM tagihan WHERE id = ?";
            String sumSql = "SELECT SUM(jumlah_bayar) FROM transaksi WHERE tagihan_id = ?";
            String updateSql = "UPDATE tagihan SET lunas = ? WHERE id = ?";

            try (
                PreparedStatement psTotal = conn.prepareStatement(totalSql);
                PreparedStatement psSum = conn.prepareStatement(sumSql);
                PreparedStatement psUpdate = conn.prepareStatement(updateSql)
            ) {
                psTotal.setInt(1, tagihanId);
                ResultSet rsTotal = psTotal.executeQuery();
                int totalTagihan = rsTotal.next() ? rsTotal.getInt(1) : 0;

                psSum.setInt(1, tagihanId);
                ResultSet rsSum = psSum.executeQuery();
                double totalBayar = rsSum.next() ? rsSum.getDouble(1) : 0;

                String status = (totalBayar >= totalTagihan) ? "Lunas" : "Belum Lunas";
                psUpdate.setString(1, status);
                psUpdate.setInt(2, tagihanId);
                psUpdate.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Gagal memperbarui status pelunasan.");
        }
    }

    private void clearForm() {
        tfTagihanId.clear();
        tfJumlahBayar.clear();
        dpTanggalBayar.setValue(null);
        tableView.getSelectionModel().clearSelection();
        selectedTransaksi = null;
        btnTambah.setDisable(false);
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
    }

    private void alertError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    private void alertInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

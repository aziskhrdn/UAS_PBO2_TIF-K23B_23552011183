package com.mycompany.kasirakademik_noer_azis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataMahasiswaPane extends VBox {

    private TableView<Mahasiswa> tableView;
    private ObservableList<Mahasiswa> list;

    private TextField tfNama, tfNim, tfUserId;
    private Button btnTambah, btnUpdate, btnHapus;

    private Mahasiswa selectedMahasiswa = null;

    public DataMahasiswaPane() {
        setPadding(new Insets(10));
        setSpacing(10);

        // Inisialisasi TableView dan kolomnya
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Mahasiswa, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Mahasiswa, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Mahasiswa, String> colNim = new TableColumn<>("NIM");
        colNim.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<Mahasiswa, Integer> colUserId = new TableColumn<>("User ID");
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        tableView.getColumns().addAll(colId, colNama, colNim, colUserId);
        tableView.setPrefHeight(250);

        // Listener pilih baris di TableView
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedMahasiswa = newSel;
                tfNama.setText(selectedMahasiswa.getNama());
                tfNim.setText(selectedMahasiswa.getNim());
                tfUserId.setText(String.valueOf(selectedMahasiswa.getUserId()));
                btnTambah.setDisable(true);
                btnUpdate.setDisable(false);
                btnHapus.setDisable(false);
            }
        });

        // Form input data mahasiswa
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Nama:"), 0, 0);
        tfNama = new TextField();
        form.add(tfNama, 1, 0);

        form.add(new Label("NIM:"), 0, 1);
        tfNim = new TextField();
        form.add(tfNim, 1, 1);

        form.add(new Label("User ID:"), 0, 2);
        tfUserId = new TextField();
        form.add(tfUserId, 1, 2);

        // Tombol aksi
        btnTambah = new Button("Tambah");
        btnUpdate = new Button("Update");
        btnHapus = new Button("Hapus");

        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);

        btnTambah.setOnAction(e -> tambahMahasiswa());
        btnUpdate.setOnAction(e -> updateMahasiswa());
        btnHapus.setOnAction(e -> hapusMahasiswa());

        HBox buttonBox = new HBox(10, btnTambah, btnUpdate, btnHapus);

        getChildren().addAll(tableView, form, buttonBox);

        loadData();
    }

    private void loadData() {
        list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM mahasiswa";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Mahasiswa(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("nim"),
                        rs.getInt("user_id")
                ));
            }
            tableView.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Gagal mengambil data mahasiswa dari database.");
        }
    }

    private void tambahMahasiswa() {
        String nama = tfNama.getText().trim();
        String nim = tfNim.getText().trim();
        String userIdStr = tfUserId.getText().trim();

        if (nama.isEmpty() || nim.isEmpty() || userIdStr.isEmpty()) {
            alertError("Semua field harus diisi.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            alertError("User ID harus berupa angka.");
            return;
        }

        // Validasi user_id ada di tabel users
        if (!cekUserIdExists(userId)) {
            alertError("User ID tidak ditemukan di database users.");
            return;
        }

        // Validasi user_id unik di tabel mahasiswa
        if (cekUserIdDipakaiMahasiswa(userId)) {
            alertError("User ID sudah digunakan oleh mahasiswa lain.");
            return;
        }

        String sql = "INSERT INTO mahasiswa (nama, nim, user_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setString(2, nim);
            ps.setInt(3, userId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                alertInfo("Data mahasiswa berhasil ditambahkan.");
                clearForm();
                loadData();
            } else {
                alertError("Gagal menambahkan data mahasiswa.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Error saat menambahkan data mahasiswa.");
        }
    }

    private void updateMahasiswa() {
        if (selectedMahasiswa == null) return;

        String nama = tfNama.getText().trim();
        String nim = tfNim.getText().trim();
        String userIdStr = tfUserId.getText().trim();

        if (nama.isEmpty() || nim.isEmpty() || userIdStr.isEmpty()) {
            alertError("Semua field harus diisi.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            alertError("User ID harus berupa angka.");
            return;
        }

        if (!cekUserIdExists(userId)) {
            alertError("User ID tidak ditemukan di database users.");
            return;
        }

        // Validasi user_id unik, kecuali user_id milik mahasiswa yang sedang diedit
        if (userId != selectedMahasiswa.getUserId() && cekUserIdDipakaiMahasiswa(userId)) {
            alertError("User ID sudah digunakan oleh mahasiswa lain.");
            return;
        }

        String sql = "UPDATE mahasiswa SET nama = ?, nim = ?, user_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setString(2, nim);
            ps.setInt(3, userId);
            ps.setInt(4, selectedMahasiswa.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                alertInfo("Data mahasiswa berhasil diupdate.");
                clearForm();
                loadData();
                selectedMahasiswa = null;
                btnTambah.setDisable(false);
                btnUpdate.setDisable(true);
                btnHapus.setDisable(true);
            } else {
                alertError("Gagal mengupdate data mahasiswa.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Error saat mengupdate data mahasiswa.");
        }
    }

    private void hapusMahasiswa() {
        if (selectedMahasiswa == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText(null);
        confirm.setContentText("Yakin ingin menghapus data mahasiswa ini?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String sql = "DELETE FROM mahasiswa WHERE id = ?";

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setInt(1, selectedMahasiswa.getId());
                    int affectedRows = ps.executeUpdate();

                    if (affectedRows > 0) {
                        alertInfo("Data mahasiswa berhasil dihapus.");
                        clearForm();
                        loadData();
                        selectedMahasiswa = null;
                        btnTambah.setDisable(false);
                        btnUpdate.setDisable(true);
                        btnHapus.setDisable(true);
                    } else {
                        alertError("Gagal menghapus data mahasiswa.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    alertError("Error saat menghapus data mahasiswa.");
                }
            }
        });
    }

    // Cek apakah user_id ada di tabel users (atau user_mahasiswa sesuai skema)
    private boolean cekUserIdExists(int userId) {
        String sql = "SELECT 1 FROM users WHERE id = ?"; // Ganti 'users' jika nama tabel berbeda
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alertError("Error saat mengecek user ID.");
            return false;
        }
    }

    // Cek apakah user_id sudah dipakai oleh mahasiswa lain
    private boolean cekUserIdDipakaiMahasiswa(int userId) {
        String sql = "SELECT 1 FROM mahasiswa WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Error saat mengecek user ID di mahasiswa.");
            return false;
        }
    }

    private void clearForm() {
        tfNama.clear();
        tfNim.clear();
        tfUserId.clear();
        tableView.getSelectionModel().clearSelection();

        btnTambah.setDisable(false);
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
        selectedMahasiswa = null;
    }

    private void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void alertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

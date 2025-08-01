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

public class DataTagihanPane extends VBox {

    private TableView<Tagihan> tableView;
    private ObservableList<Tagihan> list;

    private TextField tfMahasiswaId, tfSemester, tfTotal;
    private ComboBox<String> cbLunas;

    private Button btnTambah, btnUpdate, btnHapus;

    private Tagihan selectedTagihan = null;

    public DataTagihanPane() {
        setPadding(new Insets(10));
        setSpacing(10);

        // TableView setup
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Tagihan, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Tagihan, Integer> colMahasiswaId = new TableColumn<>("Mahasiswa ID");
        colMahasiswaId.setCellValueFactory(new PropertyValueFactory<>("mahasiswaId"));

        TableColumn<Tagihan, String> colSemester = new TableColumn<>("Semester");
        colSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));

        TableColumn<Tagihan, Integer> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<Tagihan, String> colLunas = new TableColumn<>("Lunas");
        colLunas.setCellValueFactory(new PropertyValueFactory<>("lunas"));

        tableView.getColumns().addAll(colId, colMahasiswaId, colSemester, colTotal, colLunas);
        tableView.setPrefHeight(250);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedTagihan = newSel;
                tfMahasiswaId.setText(String.valueOf(selectedTagihan.getMahasiswaId()));
                tfSemester.setText(selectedTagihan.getSemester());
                tfTotal.setText(String.valueOf(selectedTagihan.getTotal()));
                cbLunas.setValue(selectedTagihan.getLunas());

                btnTambah.setDisable(true);
                btnUpdate.setDisable(false);
                btnHapus.setDisable(false);
            }
        });

        // Form input
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        tfMahasiswaId = new TextField();
        tfSemester = new TextField();
        tfTotal = new TextField();

        cbLunas = new ComboBox<>();
        cbLunas.getItems().addAll("Belum Lunas", "Lunas");
        cbLunas.setValue("Belum Lunas");

        form.add(new Label("Mahasiswa ID:"), 0, 0);
        form.add(tfMahasiswaId, 1, 0);
        form.add(new Label("Semester:"), 0, 1);
        form.add(tfSemester, 1, 1);
        form.add(new Label("Total:"), 0, 2);
        form.add(tfTotal, 1, 2);
        form.add(new Label("Lunas:"), 0, 3);
        form.add(cbLunas, 1, 3);

        // Buttons
        btnTambah = new Button("Tambah");
        btnUpdate = new Button("Update");
        btnHapus = new Button("Hapus");

        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);

        btnTambah.setOnAction(e -> tambahTagihan());
        btnUpdate.setOnAction(e -> updateTagihan());
        btnHapus.setOnAction(e -> hapusTagihan());

        HBox buttonBox = new HBox(10, btnTambah, btnUpdate, btnHapus);

        getChildren().addAll(tableView, form, buttonBox);

        loadData();
    }

    private void loadData() {
        list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM tagihan";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Tagihan(
                        rs.getInt("id"),
                        rs.getInt("mahasiswa_id"),
                        rs.getString("semester"),
                        rs.getInt("total"),
                        rs.getString("lunas")
                ));
            }

            tableView.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
            alertError("Gagal mengambil data tagihan.");
        }
    }

    private void tambahTagihan() {
        String mahasiswaIdStr = tfMahasiswaId.getText().trim();
        String semester = tfSemester.getText().trim();
        String totalStr = tfTotal.getText().trim();
        String lunas = cbLunas.getValue();

        if (mahasiswaIdStr.isEmpty() || semester.isEmpty() || totalStr.isEmpty() || lunas == null) {
            alertError("Semua field harus diisi.");
            return;
        }

        try {
            int mahasiswaId = Integer.parseInt(mahasiswaIdStr);
            int total = Integer.parseInt(totalStr);

            String sql = "INSERT INTO tagihan (mahasiswa_id, semester, total, lunas) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, mahasiswaId);
                ps.setString(2, semester);
                ps.setInt(3, total);
                ps.setString(4, lunas);

                if (ps.executeUpdate() > 0) {
                    alertInfo("Tagihan berhasil ditambahkan.");
                    clearForm();
                    loadData();
                } else {
                    alertError("Gagal menambahkan tagihan.");
                }

            }

        } catch (NumberFormatException ex) {
            alertError("Mahasiswa ID dan Total harus berupa angka.");
        } catch (Exception ex) {
            ex.printStackTrace();
            alertError("Terjadi kesalahan saat menambah tagihan.");
        }
    }

    private void updateTagihan() {
        if (selectedTagihan == null) return;

        String mahasiswaIdStr = tfMahasiswaId.getText().trim();
        String semester = tfSemester.getText().trim();
        String totalStr = tfTotal.getText().trim();
        String lunas = cbLunas.getValue();

        if (mahasiswaIdStr.isEmpty() || semester.isEmpty() || totalStr.isEmpty() || lunas == null) {
            alertError("Semua field harus diisi.");
            return;
        }

        try {
            int mahasiswaId = Integer.parseInt(mahasiswaIdStr);
            int total = Integer.parseInt(totalStr);

            String sql = "UPDATE tagihan SET mahasiswa_id = ?, semester = ?, total = ?, lunas = ? WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, mahasiswaId);
                ps.setString(2, semester);
                ps.setInt(3, total);
                ps.setString(4, lunas);
                ps.setInt(5, selectedTagihan.getId());

                if (ps.executeUpdate() > 0) {
                    alertInfo("Tagihan berhasil diupdate.");
                    clearForm();
                    loadData();
                } else {
                    alertError("Gagal mengupdate tagihan.");
                }

            }

        } catch (NumberFormatException ex) {
            alertError("Mahasiswa ID dan Total harus berupa angka.");
        } catch (Exception ex) {
            ex.printStackTrace();
            alertError("Terjadi kesalahan saat mengupdate tagihan.");
        }
    }

    private void hapusTagihan() {
        if (selectedTagihan == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText(null);
        confirm.setContentText("Yakin ingin menghapus data ini?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String sql = "DELETE FROM tagihan WHERE id = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setInt(1, selectedTagihan.getId());

                    if (ps.executeUpdate() > 0) {
                        alertInfo("Tagihan berhasil dihapus.");
                        clearForm();
                        loadData();
                    } else {
                        alertError("Gagal menghapus tagihan.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    alertError("Terjadi kesalahan saat menghapus tagihan.");
                }
            }
        });
    }

    private void clearForm() {
        tfMahasiswaId.clear();
        tfSemester.clear();
        tfTotal.clear();
        cbLunas.setValue("Belum Lunas");
        tableView.getSelectionModel().clearSelection();
        selectedTagihan = null;
        btnTambah.setDisable(false);
        btnUpdate.setDisable(true);
        btnHapus.setDisable(true);
    }

    private void alertError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void alertInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

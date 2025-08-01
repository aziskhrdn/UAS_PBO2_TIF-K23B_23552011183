package com.mycompany.kasirakademik_noer_azis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataTransaksiScene {
    private Scene scene;
    private TableView<Transaksi> tableView;
    private App mainApp;

    public DataTransaksiScene(App mainApp) {
        this.mainApp = mainApp;

        Label titleLabel = new Label("Data Transaksi");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        tableView = new TableView<>();
        tableView.setPlaceholder(new Label("Tidak ada data transaksi."));
        setupTableColumns();

        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(10));
        topBox.getChildren().addAll(titleLabel, tableView);

        Button backButton = new Button("Kembali ke Menu");
        backButton.setOnAction(e -> mainApp.showKasirMenu());

        BorderPane layout = new BorderPane();
        layout.setTop(topBox);
        layout.setBottom(backButton);
        BorderPane.setMargin(backButton, new Insets(10));
        BorderPane.setAlignment(backButton, javafx.geometry.Pos.CENTER);

        scene = new Scene(layout, 800, 400);

        loadDataFromDatabase();
    }

    private void setupTableColumns() {
        TableColumn<Transaksi, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaksi, Integer> tagihanIdCol = new TableColumn<>("Tagihan ID");
        tagihanIdCol.setCellValueFactory(new PropertyValueFactory<>("tagihanId"));

        TableColumn<Transaksi, Double> jumlahBayarCol = new TableColumn<>("Jumlah Bayar");
        jumlahBayarCol.setCellValueFactory(new PropertyValueFactory<>("jumlahBayar"));

        TableColumn<Transaksi, LocalDate> tanggalCol = new TableColumn<>("Tanggal Bayar");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggalBayar"));

        // Tampilkan tanggal dengan format dd/MM/yyyy di tabel
        tanggalCol.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        tableView.getColumns().addAll(idCol, tagihanIdCol, jumlahBayarCol, tanggalCol);
    }

    private void loadDataFromDatabase() {
        ObservableList<Transaksi> transaksiList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transaksi")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int tagihanId = rs.getInt("tagihan_id");
                double jumlahBayar = rs.getDouble("jumlah_bayar");
                java.sql.Date sqlDate = rs.getDate("tanggal_bayar");
                LocalDate tanggalBayar = null;

                if (sqlDate != null) {
                    tanggalBayar = sqlDate.toLocalDate();
                }

                transaksiList.add(new Transaksi(id, tagihanId, (int) jumlahBayar, tanggalBayar));
            }

            tableView.setItems(transaksiList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}

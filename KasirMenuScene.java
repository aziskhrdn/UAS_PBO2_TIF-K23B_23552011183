package com.mycompany.kasirakademik_noer_azis;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class KasirMenuScene {

    private App app;
    private Scene scene;
    private BorderPane root;

    public KasirMenuScene(App app) {
        this.app = app;

        Label welcomeLabel = new Label("Selamat datang di Administrasi Akademik!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2E7D32;");

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #A5D6A7;");

        Button btnDataMahasiswa = new Button("Data Mahasiswa");
        Button btnDataTagihan = new Button("Data Tagihan");
        Button btnDataTransaksi = new Button("Data Transaksi");
        Button btnDataNilaiJadwal = new Button("Data Nilai & Jadwal");  // tombol baru
        Button btnLogout = new Button("Logout");

        Button[] buttons = { btnDataMahasiswa, btnDataTagihan, btnDataTransaksi, btnDataNilaiJadwal, btnLogout };
        for (Button btn : buttons) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle(
                "-fx-background-color: #388E3C;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 8 10;"
            );
        }

        VBox contentArea = new VBox();
        contentArea.setPadding(new Insets(15));
        contentArea.setStyle("-fx-background-color: #E8F5E9; -fx-border-color: #C8E6C9;");

        Label placeholder = new Label("Pilih menu di samping.");
        placeholder.setStyle("-fx-font-size: 16px;");
        contentArea.getChildren().add(placeholder);

        btnDataMahasiswa.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(new DataMahasiswaPane());
        });

        btnDataTagihan.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(new DataTagihanPane());
        });

        btnDataTransaksi.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(new DataTransaksiPane());
        });

        // Event tombol baru untuk Data Nilai & Jadwal
        btnDataNilaiJadwal.setOnAction(e -> {
            contentArea.getChildren().clear();
            // Contoh mahasiswaId = 1, sesuaikan jika ada user session atau parameter lain
            contentArea.getChildren().add(new DataNilaiDanJadwalPane(1));
        });

        btnLogout.setOnAction(e -> {
            app.showLogin();
        });

        sidebar.getChildren().addAll(buttons);

        root = new BorderPane();
        root.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        BorderPane.setMargin(welcomeLabel, new Insets(10));
        root.setLeft(sidebar);
        root.setCenter(contentArea);
        root.setPadding(new Insets(5));

        scene = new Scene(root, 850, 500);
    }

    public Scene getScene() {
        return scene;
    }
}

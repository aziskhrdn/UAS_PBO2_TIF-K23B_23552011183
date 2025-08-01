package com.mycompany.kasirakademik_noer_azis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Kasir Akademik");

        showLogin();
        stage.show();
    }

    public void showLogin() {
        LoginScene loginScene = new LoginScene(this);
        primaryStage.setScene(loginScene.getScene());
    }

    public void showRegister() {
        RegisterScene registerScene = new RegisterScene(this);
        primaryStage.setScene(registerScene.getScene());
    }

    public void showKasirMenu() {
        KasirMenuScene kasirMenuScene = new KasirMenuScene(this);
        primaryStage.setScene(kasirMenuScene.getScene());
    }

    public void showDataTransaksi() {
        DataTransaksiScene dataTransaksiScene = new DataTransaksiScene(this);
        primaryStage.setScene(dataTransaksiScene.getScene());
    }

    // 🔽 Tambahkan ini: Navigasi ke DataTagihanPane
    public void showDataTagihan() {
        DataTagihanPane dataTagihanPane = new DataTagihanPane();
        Scene scene = new Scene(dataTagihanPane, 800, 600);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

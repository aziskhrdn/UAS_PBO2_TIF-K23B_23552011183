package com.mycompany.kasirakademik_noer_azis;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginScene {
    private Scene scene;
    private App app;

    public LoginScene(App app) {
        this.app = app;
        createScene();
    }

    private void createScene() {
        // Root HBox, 2 kolom
        HBox root = new HBox();
        root.setPrefSize(800, 600);

        // Kiri: info panel
        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(40));
        leftPane.setPrefWidth(400);
        // Gradient background hijau to white
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#2E7D32")), new Stop(1, Color.WHITE)};
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        leftPane.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));

        Text welcomeText = new Text("Selamat Datang di Administrasi Akademik");
        welcomeText.setFill(Color.WHITE);
        welcomeText.setFont(Font.font("Arial", 28));
        welcomeText.setWrappingWidth(350);

        Text infoText = new Text("Silakan login menggunakan username dan password Anda, atau daftar jika belum punya akun.");
        infoText.setFill(Color.web("#DFF0D8")); // hijau muda lembut
        infoText.setFont(Font.font("Verdana", 16));
        infoText.setWrappingWidth(350);

        leftPane.getChildren().addAll(welcomeText, infoText);

        // Kanan: form login
        VBox rightPane = new VBox(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(40));
        rightPane.setPrefWidth(400);
        rightPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        rightPane.setEffect(new DropShadow(10, Color.gray(0.3)));

        Text title = new Text("Login Kasir Akademik");
        title.setFill(Color.web("#2E7D32"));
        title.setFont(Font.font("Arial Bold", 24));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-background-radius: 8; -fx-padding: 10px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-background-radius: 8; -fx-padding: 10px;");

        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(300);
        loginBtn.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 12 0 12 0;" +
            "-fx-cursor: hand;"
        );
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
            "-fx-background-color: #388E3C;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 12 0 12 0;" +
            "-fx-cursor: hand;"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 12 0 12 0;" +
            "-fx-cursor: hand;"
        ));

        Button gotoRegisterBtn = new Button("Register Akun Baru");
        gotoRegisterBtn.setMaxWidth(300);
        gotoRegisterBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: #2E7D32;" +
            "-fx-text-fill: #2E7D32;" +
            "-fx-font-size: 16px;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-padding: 10 0 10 0;" +
            "-fx-cursor: hand;"
        );
        gotoRegisterBtn.setOnMouseEntered(e -> gotoRegisterBtn.setStyle(
            "-fx-background-color: #2E7D32;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-padding: 10 0 10 0;" +
            "-fx-cursor: hand;"
        ));
        gotoRegisterBtn.setOnMouseExited(e -> gotoRegisterBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: #2E7D32;" +
            "-fx-text-fill: #2E7D32;" +
            "-fx-font-size: 16px;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-padding: 10 0 10 0;" +
            "-fx-cursor: hand;"
        ));

        Text message = new Text();
        message.setFill(Color.web("#D32F2F"));
        message.setFont(Font.font(14));

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                message.setText("Username dan password tidak boleh kosong!");
                return;
            }

            if (KasirMenu.loginUser(username, password)) {
                message.setText("");
                app.showKasirMenu();
            } else {
                message.setText("Username atau password salah.");
            }
        });

        gotoRegisterBtn.setOnAction(e -> app.showRegister());

        rightPane.getChildren().addAll(title, usernameField, passwordField, loginBtn, gotoRegisterBtn, message);

        root.getChildren().addAll(leftPane, rightPane);

        scene = new Scene(root);
    }

    public Scene getScene() {
        return scene;
    }
}

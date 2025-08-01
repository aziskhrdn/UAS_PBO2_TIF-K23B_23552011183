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

public class  RegisterScene {
    private Scene scene;
    private App app;

    public RegisterScene(App app) {
        this.app = app;
        createScene();
    }

    private void createScene() {
        HBox root = new HBox();
        root.setPrefSize(800, 600);

        // Kiri: info panel dengan gradasi hijau ke putih
        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(40));
        leftPane.setPrefWidth(400);
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#2E7D32")), new Stop(1, Color.WHITE)};
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        leftPane.setBackground(new Background(new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY)));

        Text welcomeText = new Text("Buat Akun Kasir Akademik");
        welcomeText.setFill(Color.WHITE);
        welcomeText.setFont(Font.font("Arial", 28));
        welcomeText.setWrappingWidth(350);

        Text infoText = new Text(
            "Isi form berikut untuk mendaftar sebagai user akademik.\n" +
            "Gunakan username dan password yang mudah diingat."
        );
        infoText.setFill(Color.web("#DFF0D8"));
        infoText.setFont(Font.font("Verdana", 16));
        infoText.setWrappingWidth(350);

        leftPane.getChildren().addAll(welcomeText, infoText);

        // Kanan: form register dengan latar putih, shadow, dan padding
        VBox rightPane = new VBox(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(40));
        rightPane.setPrefWidth(400);
        rightPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        rightPane.setEffect(new DropShadow(10, Color.gray(0.3)));

        Text title = new Text("Register Akun Baru");
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

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Konfirmasi Password");
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setStyle("-fx-background-radius: 8; -fx-padding: 10px;");

        Button registerBtn = new Button("Register");
        registerBtn.setMaxWidth(300);
        registerBtn.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 12 0 12 0;" +
            "-fx-cursor: hand;"
        );
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle(
            "-fx-background-color: #388E3C;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 12 0 12 0;" +
            "-fx-cursor: hand;"
        ));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 12 0 12 0;" +
            "-fx-cursor: hand;"
        ));

        Button gotoLoginBtn = new Button("Sudah punya akun? Login");
        gotoLoginBtn.setMaxWidth(300);
        gotoLoginBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: #2E7D32;" +
            "-fx-text-fill: #2E7D32;" +
            "-fx-font-size: 16px;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-padding: 10 0 10 0;" +
            "-fx-cursor: hand;"
        );
        gotoLoginBtn.setOnMouseEntered(e -> gotoLoginBtn.setStyle(
            "-fx-background-color: #2E7D32;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-padding: 10 0 10 0;" +
            "-fx-cursor: hand;"
        ));
        gotoLoginBtn.setOnMouseExited(e -> gotoLoginBtn.setStyle(
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

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                message.setFill(Color.web("#D32F2F"));
                message.setText("Semua field harus diisi.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                message.setFill(Color.web("#D32F2F"));
                message.setText("Password dan konfirmasi tidak sama.");
                return;
            }
            boolean registered = KasirMenu.registerUser(username, password);
            if (registered) {
                message.setFill(Color.web("#388E3C"));
                message.setText("Registrasi berhasil! Silakan login.");
            } else {
                message.setFill(Color.web("#D32F2F"));
                message.setText("Username sudah digunakan.");
            }
        });

        gotoLoginBtn.setOnAction(e -> app.showLogin());

        rightPane.getChildren().addAll(title, usernameField, passwordField, confirmPasswordField,
                registerBtn, gotoLoginBtn, message);

        root.getChildren().addAll(leftPane, rightPane);

        scene = new Scene(root);
    }

    public Scene getScene() {
        return scene;
    }
}

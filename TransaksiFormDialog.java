package com.mycompany.kasirakademik_noer_azis;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class TransaksiFormDialog extends Dialog<Double> {

    private final TextField tfJumlahBayar;

    public TransaksiFormDialog(String tagihanId) {
        setTitle("Pembayaran Tagihan");
        setHeaderText("Masukkan jumlah pembayaran untuk Tagihan ID: " + tagihanId);

        // Tombol Bayar dan Cancel
        ButtonType bayarButtonType = new ButtonType("Bayar", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(bayarButtonType, ButtonType.CANCEL);

        // Input field untuk jumlah bayar
        tfJumlahBayar = new TextField();
        tfJumlahBayar.setPromptText("Jumlah Bayar");

        // Layout grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Jumlah Bayar:"), 0, 0);
        grid.add(tfJumlahBayar, 1, 0);

        getDialogPane().setContent(grid);

        // Validasi input
        Node bayarButton = getDialogPane().lookupButton(bayarButtonType);
        bayarButton.setDisable(true);

        tfJumlahBayar.textProperty().addListener((obs, oldVal, newVal) -> {
            bayarButton.setDisable(!isValidDouble(newVal));
        });

        // Konversi hasil dialog ke nilai Double
        setResultConverter(dialogButton -> {
            if (dialogButton == bayarButtonType) {
                try {
                    return Double.parseDouble(tfJumlahBayar.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });
    }

    private boolean isValidDouble(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            double d = Double.parseDouble(str);
            return d > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

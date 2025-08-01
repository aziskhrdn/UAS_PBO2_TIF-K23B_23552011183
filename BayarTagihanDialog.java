package com.mycompany.kasirakademik_noer_azis;

import javafx.scene.control.*;
import javafx.util.Callback;

public class BayarTagihanDialog extends Dialog<Double> {

    public BayarTagihanDialog() {
        setTitle("Bayar Tagihan");
        setHeaderText("Masukkan jumlah pembayaran:");

        ButtonType bayarButtonType = new ButtonType("Bayar", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(bayarButtonType, ButtonType.CANCEL);

        TextField bayarField = new TextField();
        bayarField.setPromptText("Jumlah bayar");

        getDialogPane().setContent(bayarField);

        setResultConverter(new Callback<ButtonType, Double>() {
            @Override
            public Double call(ButtonType buttonType) {
                if (buttonType == bayarButtonType) {
                    try {
                        return Double.parseDouble(bayarField.getText());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                return null;
            }
        });
    }
}

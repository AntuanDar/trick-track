package ru.spb.taranenkoant.trick.track.client.fx.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class BaseAlert extends Alert {
    public BaseAlert(Alert.AlertType alertType) {
        this(alertType, "");
    }

    public BaseAlert(Alert.AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        this.getDialogPane().addEventFilter(KeyEvent.ANY, (e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                if (e.getEventType() == KeyEvent.KEY_RELEASED) {
                    this.close();
                }
                e.consume();
            }
        });
    }
}

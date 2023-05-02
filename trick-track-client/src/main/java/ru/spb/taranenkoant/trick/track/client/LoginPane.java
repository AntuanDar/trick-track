package ru.spb.taranenkoant.trick.track.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.spb.taranenkoant.trick.track.client.fx.IFxmlPane;
import ru.spb.taranenkoant.trick.track.client.utils.StringUtils;

public class LoginPane implements IFxmlPane {
    @FXML private Button btnCancel;
    @FXML private Button btnOk;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML
    private AnchorPane rootPane;
    private Runnable onOk;
    private LoginStatus loginStatus;

    @Override
    public AnchorPane getRootPane() {
        return rootPane;
    }

    @FXML
    public void initialize() {
        initializeField(loginField,"Введите логин");
        initializeField(passwordField,"Введите пароль");

        Platform.runLater(this::initializeLater);

        btnOk.setOnAction(e -> login());
        btnCancel.setOnAction(e -> System.exit(1));
    }

    private void initializeLater() {
        if (StringUtils.isNullOrEmpty(loginField.getText())) {
            loginField.requestFocus();
        } else if (StringUtils.isNullOrEmpty(passwordField.getText())) {
            passwordField.requestFocus();
        }
    }
    private void initializeField(TextField field, String text) {
        field.setText(text);
    }

    /**
     * Тут будет вся магия
     */
    private void login() {
        if(loginField.getText().equalsIgnoreCase("admin") && passwordField.getText().equalsIgnoreCase("admin")) {
            loginStatus = LoginStatus.SUCCESS;
            onOk.run();
            return;
        }
        //todo пока так
        loginStatus = LoginStatus.INVALID_USER;
    }

    public void setOnOk(Runnable onOk) {
        this.onOk = onOk;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }
}

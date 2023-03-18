package ru.spb.taranenkoant.trick.track.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataResource;

public class LoginPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignCancelButton;

    @FXML
    private Button SignCancelLogin;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField signUpLogin;

    @FXML
    private PasswordField signUpPass;

    @FXML
    void cancel(ActionEvent event) {
        SignCancelLogin.setOnAction(event1 ->  {
            System.out.println("Вы нажали cancel");
        });

        }


    @FXML
    void login(ActionEvent event) {

    }

    /**
     * Обработка событий кнопка Логин для перехода на файл MainPane
     */
    @FXML
    void initialize() {
        signUpLogin.setOnAction(event -> {
            signUpLogin.getScene().getWindow().hide();

            /**
             * После нажатия кнопки Login открыть окно MainPane
             */

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("resources/ru.spb.taranenkoant.trick.track.client.MainPane/MainPane.fxml"));

            try {
                loader.load();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }) ;

    }


}
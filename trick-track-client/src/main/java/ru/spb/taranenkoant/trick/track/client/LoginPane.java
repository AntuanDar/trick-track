package ru.spb.taranenkoant.trick.track.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataResource;
import ru.spb.taranenkoant.trick.track.client.fx.IFxmlPane;

public class LoginPane implements IFxmlPane {

    //убрать,это что-то левое
    @FXML private ResourceBundle resources;
    //убрать,это что-то левое
    @FXML private URL location;
    @FXML private Button SignCancelButton;
    @FXML private Button SignCancelLogin;
    @FXML private AnchorPane rootPane;
    //логин должен быть простым текстфилдом, а не парольным
    @FXML private PasswordField signUpLogin;
    @FXML private PasswordField signUpPass;


    // cancel(ActionEvent event) и login(ActionEvent event) лучше убери, какая-то фигня, видимо билдер нагенерил
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

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("resources/ru.spb.taranenkoant.trick.track.client.MainPane/MainPane.fxml"));

            try {
                //у тебя loader  написан с большой буквы, поэтому здесь идея подсвечивает как ошибку
                //loader сделал с маленькой буквой
                loader.load();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            //Scene надо просто импорт сделать нажми alt + enter на слове
            // сцену импортировал
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }) ;

    }


    @Override
    public AnchorPane getRootPane() {
        return rootPane;
    }

    //метод будет возвращать статус
    public LoginStatus getLoginStatus() {
        return null;
    }
}

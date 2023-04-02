package ru.spb.taranenkoant.trick.track.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


    private URL location;
    @FXML
    private Button SignCancelButton;
    @FXML
    private Button SignCancelLogin;
    @FXML
    private AnchorPane rootPane;
    //логин должен быть простым текстфилдом, а не парольным
    @FXML
    private PasswordField signUpLogin;
    @FXML
    private PasswordField signUpPass;




    /**
     * Обработка событий кнопка Логин для перехода на файл MainPane
     */

    /**
     * Пытался прописать логику что если не успешно то ноль
     * Иначе успешно и сообщение об успешности авторизации и загрузить панель.
     */

    @FXML
    void login(ActionEvent event) {
        Login.setOnAction(new ActionEvent());{
            public void showLogin()  {
                LoginStage loginStage = new LoginStage();
                loginStage.setOnOk(() -> {
                    if (loginStage.getLoginStatus() != LoginStatus.SUCCESS)
                        System.exit(0);
                    else(LoginStage.getLoginStatus() = LoginStatus.SUCCESS)
                            System.out.println("Вы успешно авторизированы");
                    loader.setLocation(getClass().getResource("resources/ru.spb.taranenkoant.trick.track.client.MainPane/MainPane.fxml"));


                    showModuleContainer();
                });
                loginStage.showAndWait();
            }
            }
        }


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

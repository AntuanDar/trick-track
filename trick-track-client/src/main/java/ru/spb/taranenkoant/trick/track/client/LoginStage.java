package ru.spb.taranenkoant.trick.track.client;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.spb.taranenkoant.trick.track.client.fx.FxmlPaneFactory;

import java.awt.*;

//унаследуемся от Stage, чтобы стать оберткой для сцены и удобно внести настройки, как нам нравится
public class LoginStage extends Stage {

    //панель которую мы будем показывать на нашей сцене LoginStage
    private final LoginPane loginPane;

    public LoginStage() {
        initStyle(StageStyle.TRANSPARENT);
        setOnCloseRequest(e -> System.exit(0));
        setTitle("Trick-Track");

        loginPane = new LoginPane();
        FxmlPaneFactory.loadPane(LoginPane.class, loginPane);

        AnchorPane rootPane = loginPane.getRootPane();

        //Зададим размеры по окну

        setWidth(rootPane.getPrefWidth());
        setHeight(rootPane.getPrefHeight());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setX((screenSize.width - rootPane.getPrefWidth()) / 2.0);
        setY((screenSize.height - rootPane.getPrefHeight()) / 2.0);

        Scene scene = new Scene(rootPane, rootPane.getWidth(), rootPane.getPrefHeight());
        setScene(scene);
    }

    public LoginStatus getLoginStatus() {
        return loginPane.getLoginStatus();
    }

    public void setOnOk(Runnable onOk) {
    }
}

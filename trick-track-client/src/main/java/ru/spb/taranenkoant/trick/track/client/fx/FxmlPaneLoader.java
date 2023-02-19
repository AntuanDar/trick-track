package ru.spb.taranenkoant.trick.track.client.fx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class FxmlPaneLoader {
    public static final PrintStream DUMMY_STREAM = new PrintStream(new ByteArrayOutputStream());
    private static final FxmlPaneLoader INSTANCE = new FxmlPaneLoader();

    public FxmlPaneLoader() {
    }

    public static <T extends IFxmlPane> T loadPane(Class<T> controllerType) {
        return INSTANCE.load(controllerType, null);
    }

    public static <T extends IFxmlPane> T loadPane(Class<T> controllerType, T controller) {
        return INSTANCE.load(controllerType, controller);
    }

    private <T extends IFxmlPane> T load(Class<T> controllerType, T controller) {
        FXMLLoader loader = new FXMLLoader();
        if (controller != null) {
            loader.setControllerFactory((param) -> {
                return controller;
            });
        }

        String resourcePath = '/' + controllerType.getName().replace('.', '/') + ".fxml";
        URL url = (URL)Objects.requireNonNull(controllerType.getResource(resourcePath), "Resource not found: " + resourcePath);
        loader.setLocation(url);
        PrintStream err = System.err;
        System.setErr(DUMMY_STREAM);

        IFxmlPane var8;
        try {
            Pane rootPane = (Pane)loader.load();
            IFxmlPane.setController(rootPane, (IFxmlPane)loader.getController());
            var8 = (IFxmlPane)loader.getController();
        } catch (IOException var12) {
            throw new RuntimeException(var12);
        } finally {
            System.setErr(err);
        }

        return (T) var8;
    }
}

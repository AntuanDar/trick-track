package ru.spb.taranenkoant.trick.track.client.fx;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public interface IFxmlPane {
    Object CONTROLLER_KEY = new Object();

    Pane getRootPane();

    static IFxmlPane getController(Node rootPane) {
        return (IFxmlPane)rootPane.getProperties().get(CONTROLLER_KEY);
    }

    static void setController(Node rootPane, IFxmlPane controller) {
        rootPane.getProperties().put(CONTROLLER_KEY, controller);
    }

    default void requestFocus() {
    }

    default void setVisible(boolean visible) {
        this.getRootPane().setVisible(visible);
    }
}

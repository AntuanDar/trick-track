package ru.spb.taranenkoant.trick.track.client.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.spb.taranenkoant.trick.track.client.fx.alert.Alerts;

import java.awt.*;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public abstract class FxApplication extends Application {
    private static final Dimension DEFAULT_SIZE = new Dimension(1024, 768);
    protected Stage primaryStage;

    public FxApplication() {
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    protected Stage createStage(Class<? extends IFxmlPane> controllerType) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType);
        return this.createStage(rootPane);
    }

    protected <T extends IFxmlPane> Stage createStage(T rootPane) {
        Stage stage = new Stage();
        this.initializeStage(stage, rootPane.getRootPane());
        return stage;
    }

    protected void initializeStage(Class<? extends IFxmlPane> controllerType) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType);
        this.initializeStage(this.primaryStage, rootPane.getRootPane(), true);
    }

    protected void initializeStage(Stage stage, Class<? extends IFxmlPane> controllerType) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType);
        this.initializeStage(stage, rootPane.getRootPane(), stage == this.primaryStage);
    }

    protected void initializeStage(Stage stage, Class<? extends IFxmlPane> controllerType, boolean isPrimary) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType);
        this.initializeStage(stage, rootPane.getRootPane(), isPrimary);
    }

    protected <T extends IFxmlPane> void initializeStage(Class<T> controllerType, T controller) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType, controller);
        this.initializeStage(this.primaryStage, rootPane.getRootPane(), true);
    }

    protected <T extends IFxmlPane> void initializeStage(Stage stage, Class<T> controllerType, T controller) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType, controller);
        this.initializeStage(stage, rootPane.getRootPane(), stage == this.primaryStage);
    }

    protected <T extends IFxmlPane> void initializeStage(Stage stage, Class<T> controllerType, T controller, boolean isPrimary) {
        IFxmlPane rootPane = FxmlPaneFactory.loadPane(controllerType, controller);
        this.initializeStage(stage, rootPane.getRootPane(), isPrimary);
    }

    protected void initializeStage(Pane rootPane) {
        this.initializeStage(this.primaryStage, rootPane);
    }

    protected void initializeStage(Stage stage, Pane rootPane) {
        this.initializeStage(stage, rootPane, stage == this.primaryStage);
    }

    protected void initializeStage(Stage stage, Pane rootPane, boolean isPrimary) {
        Dimension size = this.getSize(rootPane);
        if (isPrimary) {
            stage.setOnCloseRequest(this::onClose);
        }

        stage.setMinWidth(size.getWidth());
        stage.setMinHeight(size.getHeight());
        stage.setScene(new Scene(rootPane, size.getWidth(), size.getHeight()));
        stage.centerOnScreen();
    }

    protected void onClose(WindowEvent e) {
        if (Alerts.confirm("Закрыть приложение?")) {
            System.exit(0);
        } else {
            e.consume();
        }

    }

    private Dimension getSize(Pane rootPane) {
        Dimension size = new Dimension(DEFAULT_SIZE);
        double width = rootPane.getPrefWidth();
        double height = rootPane.getPrefHeight();
        if (width >= 0.0 && height >= 0.0) {
            size.setSize(width, height);
        }

        return size;
    }
}

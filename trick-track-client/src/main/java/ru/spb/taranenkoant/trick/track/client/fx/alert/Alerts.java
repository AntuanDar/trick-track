package ru.spb.taranenkoant.trick.track.client.fx.alert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.spb.taranenkoant.trick.track.client.utils.GuiUtils;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class Alerts {
    public static final ButtonType BUTTON_TYPE_YES;
    public static final ButtonType BUTTON_TYPE_NO;
    private static Image windowIcon;
    private static Stage mainStage;

    public Alerts() {
    }

    public static void setWindowIcon(Image windowIcon) {
        Alerts.windowIcon = windowIcon;
    }

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static boolean confirm(String message) {
        Alert alert = createAlert(AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(BUTTON_TYPE_YES, BUTTON_TYPE_NO);
        alert.setTitle("");
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().getButtonData() == ButtonData.YES;
    }

    private static BaseAlert createAlert(Alert.AlertType alertType) {
        BaseAlert alert = new BaseAlert(alertType);
        Window window = alert.getDialogPane().getScene().getWindow();
        if (window instanceof Stage && windowIcon != null) {
            ((Stage)window).getIcons().setAll(windowIcon);
        }

        if (mainStage != null && mainStage.getScene() != null) {
            alert.initOwner(mainStage);
        }

        return alert;
    }

    public static void error(String message) {
        alert(AlertType.ERROR, message);
    }

    public static void info(String message) {
        alert(AlertType.INFORMATION, message);
    }

    public static void warning(String message) {
        alert(AlertType.WARNING, message);
    }

    public static void alert(Alert.AlertType alertType, String message) {
        Alert alert = createAlert(alertType);
        alert.setContentText(message);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static void error(String header, String message) {
        alert(AlertType.ERROR, header, message);
    }

    public static void info(String header, String message) {
        alert(AlertType.INFORMATION, header, message);
    }

    public static void warning(String header, String message) {
        alert(AlertType.WARNING, header, message);
    }

    public static void error(Throwable e) {
        error(getCause(e).getLocalizedMessage(), getStackTrace(e));
    }

    public static void error(String header, Throwable e) {
        error(header, getStackTrace(e));
    }

    public static void alert(Alert.AlertType alertType, String header, String message) {
        Alert alert = createAlert(alertType);
        alert.setTitle("");
        alert.setHeaderText(header);
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText(message);
        textArea.setPrefHeight(300.0);
        Text text = new Text("Подробности");
        VBox content = new VBox(text);
        content.setSpacing(5.0);
        text.setCursor(Cursor.HAND);
        text.setUnderline(true);
        text.setFill(Paint.valueOf("blue"));
        int prefWidth = 500;
        int prefHeight = 140;
        text.setOnMouseClicked((e) -> {
            boolean isVisible = !content.getChildren().contains(textArea);
            GuiUtils.setVisible(content, textArea, isVisible);
            alert.setHeight(isVisible ? (double)prefHeight + textArea.getPrefHeight() : (double)(prefHeight + 40));
        });
        alert.getDialogPane().setPrefWidth(prefWidth);
        alert.getDialogPane().setPrefHeight(prefHeight);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private static Throwable getCause(Throwable e) {
        Throwable cause;
        for(cause = e; cause.getCause() != null; cause = cause.getCause()) {
        }

        return cause;
    }

    private static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    static {
        BUTTON_TYPE_YES = new ButtonType("Да", ButtonData.YES);
        BUTTON_TYPE_NO = new ButtonType("Нет", ButtonData.NO);
    }
}

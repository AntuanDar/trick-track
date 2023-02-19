package ru.spb.taranenkoant.trick.track.client.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ru.spb.taranenkoant.trick.track.client.fx.FxProperties;
import ru.spb.taranenkoant.trick.track.client.fx.IFxmlPane;

import javax.swing.Timer;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class GuiUtils {
    private static final FxProperties properties = new FxProperties();

    public GuiUtils() {
    }

    public static ImageView createImageView(Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public static MenuItem createMenuItem(String name, Runnable onAction) {
        MenuItem item = new MenuItem(name);
        item.setOnAction((e) -> {
            onAction.run();
        });
        return item;
    }

    public static ContextMenu setContextMenu(Control control, MenuItem... items) {
        return setContextMenu(control, new ContextMenu(items));
    }

    public static ContextMenu setContextMenu(Control control, ContextMenu menu) {
        control.setContextMenu(menu);
        control.setOnContextMenuRequested((e) -> {
            menu.show(control, e.getScreenX(), e.getScreenY());
        });
        control.addEventFilter(MouseEvent.MOUSE_PRESSED, (e) -> {
            menu.hide();
        });
        return menu;
    }

    public static void setToAnchorPane(AnchorPane parent, Node child) {
        setAnchors(child);
        parent.getChildren().setAll(child);
    }

    public static void addToAnchorPane(AnchorPane parent, Node child) {
        setAnchors(child);
        parent.getChildren().add(child);
    }

    public static void setAnchors(Node child) {
        AnchorPane.setTopAnchor(child, 0.0);
        AnchorPane.setRightAnchor(child, 0.0);
        AnchorPane.setBottomAnchor(child, 0.0);
        AnchorPane.setLeftAnchor(child, 0.0);
    }

    public static <T extends IFxmlPane> T getAncestorControllerOfType(Node child, Class<T> type) {
        return getAncestorControllerOfType(child, type, false);
    }

    public static <T extends IFxmlPane> T getAncestorControllerOfType(Node child, Class<T> type, boolean isNullable) {
        for(Parent parent = child.getParent(); parent != null; parent = parent.getParent()) {
            IFxmlPane controller = IFxmlPane.getController(parent);
            if (type.isInstance(controller)) {
                return (T) type.cast(controller);
            }
        }

        if (isNullable) {
            return null;
        } else {
            String message = "Ancestor of type: %s\nnot found for child: %s";
            throw new NullPointerException(String.format(message, type, child));
        }
    }

    public static <T extends Parent> T getAncestorOfClass(Node child, Class<T> type) {
        Parent parent;
        for(parent = child.getParent(); parent != null && !type.isInstance(parent); parent = parent.getParent()) {
        }

        return (T) type.cast(parent);
    }

    public static Set<IFxmlPane> getChildrenControllers(Node parent, String selector) {
        return getChildrenControllersOfType(parent, selector, IFxmlPane.class);
    }

    public static <T extends IFxmlPane> Set<T> getChildrenControllersOfType(Node parent, String selector, Class<T> type) {
        Set<T> controllers = new HashSet();

        for (Node row : parent.lookupAll(selector)) {
            controllers.add(type.cast(IFxmlPane.getController(row)));
        }

        return controllers;
    }

    public static void setChildVisible(Pane container, Node child, boolean isVisible) {
        ObservableList<Node> children = container.getChildren();
        if (isVisible) {
            if (!children.contains(child)) {
                children.add(child);
            }
        } else children.remove(child);

    }

    public static void setVisible(Pane parent, Node child, boolean isVisible) {
        List<Node> children = parent.getChildren();
        if (isVisible) {
            if (!children.contains(child)) {
                children.add(child);
            }
        } else children.remove(child);
    }

    public static void setEnabled(Pane pane, boolean isEnabled) {
        pane.setDisable(!isEnabled);
        if (properties.isEffectsEnabled()) {
            pane.setEffect(isEnabled ? null : new GaussianBlur(15.0));
        }

    }

    public static void setVisibleMainPane(Pane pane, boolean isVisible) {
        pane.setVisible(isVisible);
        if (properties.isEffectsEnabled()) {
            pane.setEffect(isVisible ? null : new GaussianBlur(15.0));
        }

    }

    public static void runLater(int delay, Runnable action) {
        Timer timer = new Timer(delay, (e) -> {
            Platform.runLater(action);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static <T> Set<T> lookupAll(Node parent, String selector, Class<T> type) {
        Stream var10000 = parent.lookupAll(selector).stream();
        type.getClass();
        return (Set)var10000.map(type::cast).collect(Collectors.toSet());
    }
}

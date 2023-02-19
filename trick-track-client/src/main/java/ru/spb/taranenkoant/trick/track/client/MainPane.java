package ru.spb.taranenkoant.trick.track.client;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spb.taranenkoant.trick.track.client.fx.IFxmlPane;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class MainPane implements IFxmlPane {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @FXML private AnchorPane rootPane;

    @FXML
    public void initialize() {
        logger.info("Inside main pane!");
    }

    @Override
    public Pane getRootPane() {
        return rootPane;
    }
}

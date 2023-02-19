package ru.spb.taranenkoant.trick.track.client;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spb.taranenkoant.trick.track.client.fx.FxApplication;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class Client extends FxApplication {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    static {
        printHeader();
    }

    protected static void printHeader() {
        logger.info("/*********************************************************************************************/");
        logger.info("/******************                                                         ******************/");
        logger.info("/******************                   Application started                   ******************/");
        logger.info("/******************                                                         ******************/");
        logger.info("/*********************************************************************************************/");
    }

    @Override
    protected void initializeStage(Stage stage, Pane rootPane, boolean isPrimary) {
        super.initializeStage(stage, rootPane, isPrimary);
        stage.setTitle(" Trick-Track");
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        showModuleContainer();
    }

    private void showModuleContainer() {
        MainPane mainPane = new MainPane();
        initializeStage(MainPane.class, mainPane);
        primaryStage.show();
    }

}

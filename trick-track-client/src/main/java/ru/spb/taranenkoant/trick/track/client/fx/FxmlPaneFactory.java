package ru.spb.taranenkoant.trick.track.client.fx;


import ru.spb.taranenkoant.trick.track.client.utils.ObjectFactory;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class FxmlPaneFactory<T extends IFxmlPane> extends ObjectFactory<T> {
    public FxmlPaneFactory(Class<T> paneType) {
        super(() -> FxmlPaneLoader.loadPane(paneType));
    }

    public static <T extends IFxmlPane> T loadPane(Class<T> controllerType) {
        return FxmlPaneLoader.loadPane(controllerType, null);
    }

    public static <T extends IFxmlPane> T loadPane(Class<T> controllerType, T controller) {
        return FxmlPaneLoader.loadPane(controllerType, controller);
    }
}

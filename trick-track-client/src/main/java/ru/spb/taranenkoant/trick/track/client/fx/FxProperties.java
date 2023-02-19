package ru.spb.taranenkoant.trick.track.client.fx;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class FxProperties {
    public FxProperties() {
    }

    public boolean isEffectsEnabled() {
        String value = System.getProperty("effects.enabled", "true");
        return Boolean.parseBoolean(value);
    }
}

package ru.spb.taranenkoant.trick.track.client.utils;

import java.util.function.Supplier;

/**
 * Created by Anton on 19.02.2023
 * description:
 */
public class ObjectFactory<T> implements Supplier<T> {
    private final Supplier<T> factory;
    private T object;

    public ObjectFactory(T object) {
        this.factory = () -> {
            return object;
        };
    }

    public ObjectFactory(Supplier<T> factory) {
        this.factory = factory;
    }

    public final T get() {
        if (this.object == null) {
            this.object = this.factory.get();
        }

        return this.object;
    }

    public void clear() {
        this.object = null;
    }
}

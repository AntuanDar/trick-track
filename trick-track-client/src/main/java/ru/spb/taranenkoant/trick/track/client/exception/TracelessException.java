package ru.spb.taranenkoant.trick.track.client.exception;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class TracelessException extends RuntimeException {

    public TracelessException() {
        this("");
    }

    public TracelessException(String message) {
        super(message);
        setStackTrace(new StackTraceElement[0]);
    }

}

package ru.spb.taranenkoant.trick.track.client;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class ConnectionSettings {

    public static final String DEFAULT_SERVER_URL = "http://localhost:8080/trick-track-server";
    private String serverUrl = DEFAULT_SERVER_URL;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String url) {
        this.serverUrl = url;
    }

    @Override
    public String toString() {
        return String.format("ConnectionSettings{serverUrl='%s'}", serverUrl);
    }
}

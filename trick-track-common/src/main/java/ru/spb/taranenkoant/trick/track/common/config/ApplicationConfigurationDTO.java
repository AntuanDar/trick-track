package ru.spb.taranenkoant.trick.track.common.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Anton on 01.04.2023
 * description:
 */
public class ApplicationConfigurationDTO {

    private Map<String, Object> spring = new HashMap<>();
    private Map<String, Object> logging = new HashMap<>();
    // костыль для YAML
    @JsonProperty("logging.file.name")
    private String loggingFile;

    public ApplicationConfigurationDTO() {
    }

    public ApplicationConfigurationDTO(Map<String, Object> spring, Map<String, Object> logging, String loggingFile) {
        this.spring = spring;
        this.logging = logging;
        this.loggingFile = loggingFile;
    }

    public Map<String, Object> getLogging() {
        return logging;
    }

    public void setLogging(Map<String, Object> logging) {
        this.logging = logging;
    }

    public Map<String, Object> getSpring() {
        return spring;
    }

    public void setSpring(Map<String, Object> spring) {
        this.spring = spring;
    }

    public String getLoggingFile() {
        return loggingFile;
    }

    public void setLoggingFile(String loggingFile) {
        this.loggingFile = loggingFile;
    }
}

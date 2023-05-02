package ru.spb.taranenkoant.trick.track.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Anton on 01.04.2023
 * description:
 */
@Component
public class ApplicationConfiguration extends AbstractConfiguration<ApplicationConfigurationDTO> {

    @Value("${" + MODULE_CONFIG_LOCATION_PROPERTY + "}")
    private String configLocation;
    @Value("${" + MODULE_NAME_PROPERTY + ":undefined}")
    private String moduleName;

    public ApplicationConfiguration() {
        super(ApplicationConfigurationDTO.class);
    }

    public ApplicationConfiguration(String configLocation, String moduleName) {
        super(ApplicationConfigurationDTO.class);
        this.configLocation = configLocation;
        this.moduleName = moduleName;
    }

    @Override
    public String getName() {
        String name = "application";
        return name;
    }

    @Override
    public String getConfigLocation() {
        return configLocation;
    }

    @Override
    public ApplicationConfigurationDTO getDefault() {

        Map<String, Object> loggingLevels = new HashMap<String, Object>() {{
            put("root", "INFO");
            put("org.springframework.web.client.RestTemplate", "DEBUG");
            put("org.apache.http.wire", "DEBUG");
            put("org.hibernate.SQL", "TRACE");
            put("org.hibernate.type.descriptor.sql.BasicBinder", "TRACE");
        }};


        Map<String, Object> logFileProperties = new HashMap<String, Object>() {{
            // максимум 30 архивных файлов (архивация каждый день)
            put("max-history", "30");
            put("max-size", "10MB");
        }};
        Map<String, Object> logging = new HashMap<String, Object>() {{
            put("level", loggingLevels);
            put("file", logFileProperties);
        }};
        String logFile = "logs/" + moduleName + ".log";
        return new ApplicationConfigurationDTO(new HashMap<>(), logging, logFile);
    }
}

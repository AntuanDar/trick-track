package ru.spb.taranenkoant.trick.track.common.config;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
/**
 * Created by Anton on 01.04.2023
 * description:
 */
@Order(ConfigFileApplicationListener.DEFAULT_ORDER - 1)
public class ConfigurationSystemListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private final String moduleName;

    public ConfigurationSystemListener(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        HashMap<String, Object> defaultProperties = getDefaultProperties();
        environment.getPropertySources().addLast(new MapPropertySource("configurationSystemDefaults", defaultProperties));
        checkApplicationConfigurationExistence(environment);
    }

    /*
     *
     * Перед запуском парсера Спринговых настроек проверяем их наличие.
     *
     * */
    private void checkApplicationConfigurationExistence(ConfigurableEnvironment environment) {
        String moduleName = environment.getProperty(AbstractConfiguration.MODULE_NAME_PROPERTY);
        String configLocation = environment.getProperty(AbstractConfiguration.MODULE_CONFIG_LOCATION_PROPERTY);
        ApplicationConfiguration configuration = new ApplicationConfiguration(configLocation, moduleName);
        configuration.postConstruct();
    }

    /*
     *
     * Дефолтные параметры хранения конфигов
     *
     * */
    private HashMap<String, Object> getDefaultProperties() {
        HashMap<String, Object> defaultProperties = new HashMap<>();
        defaultProperties.put(
                AbstractConfiguration.MODULE_NAME_PROPERTY,
                moduleName
        );
        defaultProperties.put(
                AbstractConfiguration.CONFIG_ROOT_LOCATION_PROPERTY,
                AbstractConfiguration.DEFAULT_CONFIG_ROOT_LOCATION
        );
        defaultProperties.put(
                AbstractConfiguration.MODULE_CONFIG_LOCATION_PROPERTY,
                AbstractConfiguration.DEFAULT_MODULE_CONFIG_LOCATION
        );
        defaultProperties.put(
                ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY,
                AbstractConfiguration.DEFAULT_MODULE_CONFIG_LOCATION
        );
        defaultProperties.put(
                AbstractConfiguration.CONFIG_COMMON_LOCATION_PROPERTY,
                AbstractConfiguration.DEFAULT_COMMON_CONFIG_LOCATION
        );
        return defaultProperties;
    }
}

package ru.spb.taranenkoant.trick.track.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Anton on 31.03.2023
 * description:
 */
public abstract class AbstractConfiguration<T> {

    public static final String MODULE_NAME_PROPERTY = "module.name";
    public static final String CONFIG_ROOT_LOCATION_PROPERTY = "config.location.root";
    public static final String DEFAULT_CONFIG_ROOT_LOCATION = "trick-track/config/";
    public static final String CONFIG_COMMON_LOCATION_PROPERTY = "config.location.common";
    public static final String DEFAULT_COMMON_CONFIG_LOCATION = "${" + CONFIG_ROOT_LOCATION_PROPERTY + "}" + "common/";
    public static final String MODULE_CONFIG_LOCATION_PROPERTY = "config.location.module";
    public static final String DEFAULT_MODULE_CONFIG_LOCATION = "${" + CONFIG_ROOT_LOCATION_PROPERTY + "}" + "${" + MODULE_NAME_PROPERTY + "}/";

    @JsonIgnore
    private ObjectWriter objectWriter;

    @JsonIgnore
    private ObjectReader objectReader;
    @JsonIgnore
    private File file;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(AbstractConfiguration.class);
    @JsonIgnore
    private Path path;
    @JsonIgnore
    private final Class<T> configType;
    private T config;

    public AbstractConfiguration(Class<T> configType) {
        this.configType = configType;
    }

    @PostConstruct
    protected void postConstruct() {
        YAMLFactory factory = new YAMLFactory();
        factory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(factory);
        /*
         * Конфиг имеет собственный маппер в JSON, который может видеть только поля.
         *
         * */
        initializeObjectMapper(objectMapper);

        path = Paths.get(getConfigLocation());
        file = setupConfigFile(path);
        objectWriter = objectMapper.writer();
        objectReader = objectMapper.readerFor(configType);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Error creating configuration directory");
            }
        }
        fetchProperties();
    }

    private void initializeObjectMapper(ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(
                objectMapper.getVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
        );
        objectMapper.findAndRegisterModules();
    }

    public abstract String getConfigLocation();

    public abstract String getName();

    private void fetchProperties() {
        T config = load();
        if (config == null) {
            this.config = getDefault();
            save();
            return;
        }
        this.config = config;
    }

    public T getConfig() {
        return config;
    }

    public void setConfig(T config) {
        this.config = config;
        save();
    }

    private File setupConfigFile(Path path) {
        File configFile = path.resolve(getName() + ".yaml").toFile();
        if (!configFile.exists()) {
            try {
                logger.info("Config '" + getName() + "' not found. Creating with empty data.");
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error creating configuration file", e);
            }
        }
        return configFile;
    }

    public void save() {
        try {
            File configFile = file;
            objectWriter.writeValue(configFile, config);
        } catch (IOException e) {
            logger.error("Error saving config '" + getName() + "'", e);
        }
    }

    protected T load() {
        if (file.length() == 0) {
            logger.info("Config '" + getName() + "' is empty. Default settings applied.");
            return null;
        }

        try {
            T t = objectReader.readValue(file);
            logger.info("Loaded config '" + getName() + "' with data: \r\n" + objectWriter.writeValueAsString(t));
            return t;
        } catch (IOException e) {
            Path filePath = file.toPath();
            Path backupPath = filePath.getParent().resolve(getName() + '_'
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy_HH-mm-ss")) + ".yaml.backup");
            try {
                Files.copy(filePath, backupPath);
            } catch (IOException ex) {
                logger.error("Error during broken configuration backup", ex);
            }
            logger.warn("Error loading config '" + getName() + "'. Default settings applied.", e);
            return null;
        }
    }

    public Path getPath() {
        return path;
    }

    public abstract T getDefault();

    protected void assertPropertyNonEmpty(String name, Object value) {
        if (value == null || "".equals(value))
            throw new InvalidConfigurationPropertyValueException(name, null, "empty value");
    }
}

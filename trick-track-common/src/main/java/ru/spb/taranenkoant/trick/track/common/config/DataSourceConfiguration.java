package ru.spb.taranenkoant.trick.track.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Anton on 01.04.2023
 * description:
 */
@Component
public class DataSourceConfiguration extends AbstractConfiguration<DataSourceConfigurationDTO> {

    private static final String pgDriver = "org.postgresql.Driver";
    private static final String pgDialect = "org.hibernate.dialect.PostgreSQL9Dialect";
    private static final String localPgUrl = "jdbc:postgresql://localhost:5432/DB";

    @Value("${" + AbstractConfiguration.CONFIG_COMMON_LOCATION_PROPERTY + "}")
    private String configLocation;

    @Autowired
    private Profiles profiles;

    public DataSourceConfiguration() {
        super(DataSourceConfigurationDTO.class);
    }

    @Override
    public String getConfigLocation() {
        return configLocation;
    }

    @Override
    public String getName() {
        if (profiles.isDefault())
            return "dataSource";
        return "dataSource-" + profiles.first();
    }

    @Override
    public DataSourceConfigurationDTO getDefault() {

        if (profiles.isDevPg())
            return new DataSourceConfigurationDTO(localPgUrl, "trick", "track", pgDriver);

        String url = "jdbc:postgresql://localhost:5432/DB";
        String username = "trick";
        String password = "track";
        String driverClassName = "org.hibernate.dialect.PostgreSQL9Dialect";
        return new DataSourceConfigurationDTO(url, username, password, pgDriver);
    }
}

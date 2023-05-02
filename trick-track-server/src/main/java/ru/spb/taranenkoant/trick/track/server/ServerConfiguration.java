package ru.spb.taranenkoant.trick.track.server;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.spb.taranenkoant.trick.track.common.config.DataSourceConfiguration;
import ru.spb.taranenkoant.trick.track.common.config.DataSourceConfigurationDTO;

import javax.sql.DataSource;

/**
 * Created by Anton on 01.04.2023
 * description:
 */
@Configuration
public class ServerConfiguration implements WebMvcConfigurer {

    @Autowired
    private DataSourceConfiguration dataSourceConfiguration;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        DataSourceConfigurationDTO config = dataSourceConfiguration.getConfig();
        dataSourceBuilder.url(config.getUrl());
        dataSourceBuilder.username(config.getUsername());
        dataSourceBuilder.password(config.getPassword());
        dataSourceBuilder.driverClassName(config.getDriverClassName());
        return dataSourceBuilder.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Module jacksonDatatypeHibernateModule() {
        return new Hibernate5Module();
    }
}

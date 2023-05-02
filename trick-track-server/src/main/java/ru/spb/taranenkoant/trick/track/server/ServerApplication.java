package ru.spb.taranenkoant.trick.track.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.spb.taranenkoant.trick.track.common.config.ConfigurationSystemListener;
/**
 * Created by Anton on 01.04.2023
 * description:
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan({"ru.spb.taranenkoant.trick.track.common.config", "ru.spb.taranenkoant.trick.track.server"})
@PropertySource(name = "default", value = "classpath:application.properties")
public class ServerApplication extends SpringBootServletInitializer {

    private static final String MODULE_NAME = "trick-track-server";

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
        application.addListeners(new ConfigurationSystemListener(MODULE_NAME));
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        SpringApplicationBuilder applicationBuilder = super.configure(builder);
        return applicationBuilder.listeners(new ConfigurationSystemListener(MODULE_NAME));
    }
}
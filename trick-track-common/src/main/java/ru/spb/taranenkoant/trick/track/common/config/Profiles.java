package ru.spb.taranenkoant.trick.track.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Anton on 01.04.2023
 * description:
 */
@Component
public class Profiles {

    @Autowired
    private Environment env;

    private List<String> profiles;

    @PostConstruct
    public void init() {
        profiles = Arrays.asList(env.getActiveProfiles());
    }

    public boolean isActive(String profile) {
        return profiles.contains(profile);
    }

    public boolean isDev() {
        return isActive("dev");
    }

    public boolean isTest() {
        return isActive("test");
    }

    public boolean isRelease() {
        return isActive("release");
    }

    public boolean isProd() {
        return isActive("prod");
    }

    public boolean isDefault() {
        return profiles.size() == 0 || isActive("default");
    }

    public String first() {
        if (profiles.size() == 0)
            return "default";
        return profiles.get(0);
    }

    public boolean isDevPg() {
        return isActive("dev_pg");
    }
}

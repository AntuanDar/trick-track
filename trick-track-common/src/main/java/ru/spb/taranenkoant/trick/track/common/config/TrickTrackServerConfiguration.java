package ru.spb.taranenkoant.trick.track.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Anton on 01.04.2023
 * description:
 */
@Component
public class TrickTrackServerConfiguration extends AbstractConfiguration<TrickTrackServerConfigurationDTO> {

    @Value("${" + MODULE_CONFIG_LOCATION_PROPERTY + "}")
    private String configLocation;

    public TrickTrackServerConfiguration() {
        super(TrickTrackServerConfigurationDTO.class);
    }

    @Override
    public String getConfigLocation() {
        return configLocation;
    }

    @Override
    public String getName() {
        return "trick-track-server";
    }

    @Override
    public TrickTrackServerConfigurationDTO getDefault() {
        return new TrickTrackServerConfigurationDTO();
    }
}

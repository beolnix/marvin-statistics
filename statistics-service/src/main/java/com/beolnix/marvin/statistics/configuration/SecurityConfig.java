package com.beolnix.marvin.statistics.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by beolnix on 21/02/16.
 */

@ConfigurationProperties(prefix="statistics.api.security", ignoreInvalidFields = false)
public class SecurityConfig {
    List<AccessKey> accessKeys;

    public List<AccessKey> getAccessKeys() {
        return accessKeys;
    }

    public void setAccessKeys(List<AccessKey> accessKeys) {
        this.accessKeys = accessKeys;
    }
}

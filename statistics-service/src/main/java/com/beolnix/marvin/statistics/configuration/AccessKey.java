package com.beolnix.marvin.statistics.configuration;

import java.util.List;

/**
 * Created by beolnix on 21/02/16.
 */
public class AccessKey {

    private String key;
    private String secret;
    private List<String> roles;

    public AccessKey(String key, String secret, List<String> roles) {
        this.key = key;
        this.secret = secret;
        this.roles = roles;
    }

    public AccessKey() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

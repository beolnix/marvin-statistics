package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

@ApiModel("DTO for the user specific metrics")
public class UserSpecificMetricsDTO {

    public final static String MSG_COUNT_METRIC_NAME = "msgCount";

    @ApiModelProperty(value = "User details", required = true)
    private UserDTO user;

    @ApiModelProperty(value = "Metric details", required = true)
    private Map<String, Integer> metricsMap = new HashMap<>();

    @Override
    public String toString() {
        return "UserSpecificMetricsDTO{" +
                "user=" + user +
                ", metricsMap=" + metricsMap +
                '}';
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Map<String, Integer> getMetricsMap() {
        return metricsMap;
    }

    public void setMetricsMap(Map<String, Integer> metricsMap) {
        this.metricsMap = metricsMap;
    }
}

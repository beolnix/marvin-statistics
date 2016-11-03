package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("DTO for the user specific metrics")
public class UserSpecificMetricsDTO {

    @ApiModelProperty(value = "User details", required = true)
    private UserDTO user;

    @ApiModelProperty(value = "Metric details", required = true)
    private MetricDTO metric;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public MetricDTO getMetric() {
        return metric;
    }

    public void setMetric(MetricDTO metric) {
        this.metric = metric;
    }
}

package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Metric details DTO")
public class MetricDTO {

    @ApiModelProperty(value = "Metric name", required = true)
    private String metricName;

    @ApiModelProperty(value = "Metric value", required = true)
    private Integer metricValue;

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Integer getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(Integer metricValue) {
        this.metricValue = metricValue;
    }
}

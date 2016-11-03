package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("DTO to represent statistics aggregated for a month")
public class MonthStatisticsDTO {

    @ApiModelProperty(value = "Year number", required = true)
    private Integer year;

    @ApiModelProperty(value = "Month number", required = true)
    private Integer month;

    @ApiModelProperty(value = "Total metrics aggregated for a month", required = true)
    private List<MetricDTO> totalMetrics;

    @ApiModelProperty(value = "Top user specific metrics aggregated for a month", required = true)
    private List<UserSpecificMetricsDTO> topUserSpecificMetrics;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public List<MetricDTO> getTotalMetrics() {
        return totalMetrics;
    }

    public void setTotalMetrics(List<MetricDTO> totalMetrics) {
        this.totalMetrics = totalMetrics;
    }

    public List<UserSpecificMetricsDTO> getTopUserSpecificMetrics() {
        return topUserSpecificMetrics;
    }

    public void setTopUserSpecificMetrics(List<UserSpecificMetricsDTO> topUserSpecificMetrics) {
        this.topUserSpecificMetrics = topUserSpecificMetrics;
    }
}

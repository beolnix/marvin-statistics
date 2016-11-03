package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("DTO to represent statistics for a period of a time")
public class AggregatedStatisticsPeriodDTO {

    @ApiModelProperty(value = "Period start date time", required = true)
    private LocalDateTime periodStart;

    @ApiModelProperty(value = "Period end date time", required = true)
    private LocalDateTime periodEnd;

    @ApiModelProperty(value = "Total metrics aggregated for a year", required = true)
    private List<MetricDTO> totalMetrics;

    @ApiModelProperty(value = "Top user specific metrics aggregated for a year", required = true)
    private List<UserSpecificMetricsDTO> topUserSpecificMetrics;

    public LocalDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDateTime periodEnd) {
        this.periodEnd = periodEnd;
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

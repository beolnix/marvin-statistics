package com.beolnix.marvin.statistics.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ApiModel("DTO to represent statistics for a period of a time")
public class AggregatedStatisticsPeriodDTO {

    @ApiModelProperty(value = "Period start date time", required = true)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
    private LocalDateTime periodStart;

    @ApiModelProperty(value = "Period end date time", required = true)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
    private LocalDateTime periodEnd;

    @ApiModelProperty(value = "Top user specific metrics aggregated for a year", required = true)
    private Map<String, UserSpecificMetricsDTO> userSpecificMetricsMap;

    @ApiModelProperty(value = "Total metrics aggregated for a year", required = true)
    private Map<String, Integer> totalMetricsMap = new HashMap<>();

    @Override
    public String toString() {
        return "AggregatedStatisticsPeriodDTO{" +
                "periodStart=" + periodStart +
                ", periodEnd=" + periodEnd +
                ", userSpecificMetricsMap=" + userSpecificMetricsMap +
                ", totalMetricsMap=" + totalMetricsMap +
                '}';
    }

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

    public Map<String, UserSpecificMetricsDTO> getUserSpecificMetricsMap() {
        return userSpecificMetricsMap;
    }

    public void setUserSpecificMetricsMap(Map<String, UserSpecificMetricsDTO> userSpecificMetricsMap) {
        this.userSpecificMetricsMap = userSpecificMetricsMap;
    }

    public Map<String, Integer> getTotalMetricsMap() {
        return totalMetricsMap;
    }

    public void setTotalMetricsMap(Map<String, Integer> totalMetricsMap) {
        this.totalMetricsMap = totalMetricsMap;
    }
}

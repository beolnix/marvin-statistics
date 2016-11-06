package com.beolnix.marvin.statistics.statistics.domain.model;

import java.time.LocalDateTime;

public class AggregatedUserSpecificMetric {
    private String username;
    private LocalDateTime periodStart;
    private Integer count;
    private String metricName;

    @Override
    public String toString() {
        return "AggregatedUserSpecificMetric{" +
                "username='" + username + '\'' +
                ", periodStart=" + periodStart +
                ", count=" + count +
                ", metricName='" + metricName + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
}

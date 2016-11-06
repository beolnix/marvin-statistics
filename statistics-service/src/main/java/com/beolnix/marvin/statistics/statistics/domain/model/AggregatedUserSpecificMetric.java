package com.beolnix.marvin.statistics.statistics.domain.model;

import java.time.LocalDateTime;

public class AggregatedUserSpecificMetric {
    private String username;
    private LocalDateTime periodStart;
    private Integer count;

    @Override
    public String toString() {
        return "AggregatedUserSpecificMetric{" +
                "username='" + username + '\'' +
                ", periodStart=" + periodStart +
                ", count=" + count +
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
}

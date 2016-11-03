package com.beolnix.marvin.statistics.statistics.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class UserSpecificMetric {

    @Id
    private String id;

    @Indexed
    private String chatId;

    @Indexed
    private String metricName;

    @Indexed
    private String username;

    private Integer value;

    @Indexed
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "UserSpecificMetric{" +
                "id='" + id + '\'' +
                ", chatId='" + chatId + '\'' +
                ", metricName='" + metricName + '\'' +
                ", username='" + username + '\'' +
                ", value=" + value +
                ", dateTime=" + dateTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

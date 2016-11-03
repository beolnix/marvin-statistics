package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("DTO for the chat statistics")
public class StatisticsDTO {

    @ApiModelProperty(value = "Chat, from where statistics came from", required = true)
    private ChatDTO chat;

    @ApiModelProperty(value = "List of a user specific metrics", required = true)
    private List<UserSpecificMetricsDTO> userSpecificMetrics;

    public List<UserSpecificMetricsDTO> getUserSpecificMetrics() {
        return userSpecificMetrics;
    }

    public void setUserSpecificMetrics(List<UserSpecificMetricsDTO> userSpecificMetrics) {
        this.userSpecificMetrics = userSpecificMetrics;
    }

    public ChatDTO getChat() {
        return chat;
    }

    public void setChat(ChatDTO chat) {
        this.chat = chat;
    }
}

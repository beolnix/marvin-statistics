package com.beolnix.marvin.statistics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("DTO to represent statistics aggregated by specified periods")
public class AggregatedStatisticsDTO {

    @ApiModelProperty(value = "Chat where statistics was gathered", required = true)
    private ChatDTO chat;

    @ApiModelProperty(value = "Requested periods of a statistics", required = true)
    private List<AggregatedStatisticsPeriodDTO> periods;

    public ChatDTO getChat() {
        return chat;
    }

    public void setChat(ChatDTO chat) {
        this.chat = chat;
    }

    public List<AggregatedStatisticsPeriodDTO> getPeriods() {
        return periods;
    }

    public void setPeriods(List<AggregatedStatisticsPeriodDTO> periods) {
        this.periods = periods;
    }
}

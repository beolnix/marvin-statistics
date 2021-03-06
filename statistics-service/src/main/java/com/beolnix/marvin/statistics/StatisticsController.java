package com.beolnix.marvin.statistics;

import com.beolnix.marvin.statistics.api.StatisticsApi;
import com.beolnix.marvin.statistics.api.model.AggregatedStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import com.beolnix.marvin.statistics.error.BadRequest;
import com.beolnix.marvin.statistics.statistics.StatisticsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class StatisticsController implements StatisticsApi {

    private final StatisticsService service;

    @Autowired
    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @Override
    public void postStatistics(@RequestBody StatisticsDTO statisticsDTO) {
        service.saveStatistics(statisticsDTO);
    }

    @Override
    public AggregatedStatisticsDTO getAggregatedStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = true) Integer periodLengthInHours,
            @RequestParam(required = true) String chatId,
            @RequestParam(required = false) String metricName) {

        if (start == null) {
            throw new BadRequest("Start date time must be provided in ISO-8601 format.");
        } else if (end == null) {
            throw new BadRequest("End date time must be provided in ISO-8601 format.");
        } else if (end.isBefore(start)) {
            throw new BadRequest("Start must be before End.");
        } else if (periodLengthInHours == null || periodLengthInHours < 1) {
            throw new BadRequest("periodLengthInHours must be provided as a positive integer.");
        } else if (StringUtils.isBlank(chatId)) {
            throw new BadRequest("ChatId must be provided as non-empty string.");
        }

        return service.getAggregatedStatistics(start, end, periodLengthInHours, chatId, Optional.ofNullable(metricName));
    }

}

package com.beolnix.marvin.statistics.statistics;

import com.beolnix.marvin.statistics.api.model.AggregatedStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.ChatDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import com.beolnix.marvin.statistics.chats.ChatService;
import com.beolnix.marvin.statistics.statistics.domain.dao.UserSpecificMetricDAO;
import com.beolnix.marvin.statistics.statistics.domain.model.AggregatedUserSpecificMetric;
import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import com.beolnix.marvin.statistics.statistics.mongo.MetricsAggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class StatisticsService {

    private final static Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    private final StatisticsConverter util;
    private final UserSpecificMetricDAO metricDAO;
    private final MetricsAggregationService aggregationService;
    private final ChatService chatService;


    @Autowired
    public StatisticsService(
            StatisticsConverter util,
            UserSpecificMetricDAO metricDAO,
            MetricsAggregationService aggregationService,
            ChatService chatService) {
        this.util = util;
        this.metricDAO = metricDAO;
        this.aggregationService = aggregationService;
        this.chatService = chatService;
    }

    public void saveStatistics(StatisticsDTO statisticsDTO) {
        List<UserSpecificMetric> newMetrics = util.convert(statisticsDTO);
        metricDAO.save(newMetrics);
        logger.debug("New metrics are persisted: {}", newMetrics);
    }

    public AggregatedStatisticsDTO getAggregatedStatistics(LocalDateTime start,
                                                           LocalDateTime end,
                                                           Integer periodLengthInHours,
                                                           String chatId,
                                                           Optional<String> metricName) {

        List<AggregatedUserSpecificMetric> result = aggregationService.aggregateForTimePeriod(
                start,
                end,
                periodLengthInHours,
                chatId,
                metricName
        );

        ChatDTO chatDTO = chatService.getChatById(chatId);
        return util.convert(result, chatDTO, end, periodLengthInHours);
    }


}

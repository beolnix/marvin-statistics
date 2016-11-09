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
import java.util.*;


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

        List<AggregatedUserSpecificMetric> dbResponse = aggregationService.aggregateForTimePeriod(
                start,
                end,
                periodLengthInHours,
                chatId,
                metricName
        );

        Map<String, List<AggregatedUserSpecificMetric>> metricMap = convertDbResponseToMap(dbResponse);
        List<AggregatedUserSpecificMetric> result = enrichResultWithZeroMetrics(metricMap, start, end, periodLengthInHours);
        addAnonymousMetricsIfNeeded(result, start, end, periodLengthInHours);

        ChatDTO chatDTO = chatService.getChatById(chatId);
        return util.convert(result, chatDTO, end, periodLengthInHours);
    }

    private void addAnonymousMetricsIfNeeded(
            List<AggregatedUserSpecificMetric> result,
            LocalDateTime start,
            LocalDateTime end,
            Integer periodLengthInHours) {
        if (result.isEmpty()) {
            getAllPeriods(start, end, periodLengthInHours).forEach(period -> {
                AggregatedUserSpecificMetric metric = new AggregatedUserSpecificMetric();
                metric.setCount(0);
                metric.setUsername("Anonymous");
                metric.setMetricName("msgCount");
                metric.setPeriodStart(period);

                result.add(metric);
            });
        }
    }

    private List<AggregatedUserSpecificMetric> enrichResultWithZeroMetrics(
            Map<String, List<AggregatedUserSpecificMetric>> metricMap,
            LocalDateTime start,
            LocalDateTime end,
            Integer periodLengthInHours) {
        List<AggregatedUserSpecificMetric> result = new LinkedList<>();
        for (Map.Entry<String, List<AggregatedUserSpecificMetric>> entry : metricMap.entrySet()) {
            result.addAll(entry.getValue());
            findMissedPeriods(entry.getValue(), start, end, periodLengthInHours).forEach(period -> {
                AggregatedUserSpecificMetric metric = new AggregatedUserSpecificMetric();
                metric.setCount(0);
                metric.setUsername(entry.getKey());
                metric.setMetricName("msgCount");
                metric.setPeriodStart(period);

                result.add(metric);
            });
        }

        return result;
    }

    private Map<String, List<AggregatedUserSpecificMetric>> convertDbResponseToMap(List<AggregatedUserSpecificMetric> dbResponse) {
        Map<String, List<AggregatedUserSpecificMetric>> metricMap = new HashMap<>();
        dbResponse.forEach(metric -> {
            List<AggregatedUserSpecificMetric> metricList = metricMap.get(metric.getUsername());
            if (metricList == null) {
                metricList = new LinkedList<>();
                metricMap.put(metric.getUsername(), metricList);
            }
            metricList.add(metric);
        });

        return metricMap;
    }

    private Set<LocalDateTime> findMissedPeriods(List<AggregatedUserSpecificMetric> result,
                                                 LocalDateTime start,
                                                 LocalDateTime end,
                                                 Integer periodLengthInHours) {
        Set<LocalDateTime> allPeriods = getAllPeriods(start, end, periodLengthInHours);
        result.forEach(metric -> {
            allPeriods.remove(metric.getPeriodStart());
        });

        return allPeriods;
    }

    private Set<LocalDateTime> getAllPeriods(LocalDateTime start,
                                             LocalDateTime end,
                                             Integer periodLengthInHours) {
        Set<LocalDateTime> result = new HashSet<>();

        LocalDateTime currentStart = start;
        while (currentStart.isBefore(end)) {
            result.add(currentStart);
            currentStart = currentStart.plusHours(periodLengthInHours);
        }

        return result;
    }


}

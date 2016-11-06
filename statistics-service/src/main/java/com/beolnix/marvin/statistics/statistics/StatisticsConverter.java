package com.beolnix.marvin.statistics.statistics;

import com.beolnix.marvin.statistics.api.model.*;
import com.beolnix.marvin.statistics.statistics.domain.model.AggregatedUserSpecificMetric;
import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class StatisticsConverter {

    public List<UserSpecificMetric> convert(StatisticsDTO statisticsDTO) {
        String chatId = statisticsDTO.getChat().getId();
        LocalDateTime now = LocalDateTime.now();
        List<UserSpecificMetric> result = new LinkedList<>();

        for (UserSpecificMetricsDTO metricsDTO : statisticsDTO.getUserSpecificMetrics()) {
            for (Map.Entry<String, Integer> entry : metricsDTO.getMetricsMap().entrySet()) {

                UserSpecificMetric userSpecificMetric = new UserSpecificMetric();
                userSpecificMetric.setChatId(chatId);
                userSpecificMetric.setDateTime(now);
                userSpecificMetric.setMetricName(entry.getKey());
                userSpecificMetric.setValue(entry.getValue());
                userSpecificMetric.setUsername(metricsDTO.getUser().getUsername());

                result.add(userSpecificMetric);
            }
        }

        return result;
    }

    public AggregatedStatisticsDTO convert(
            List<AggregatedUserSpecificMetric> chatMetrics,
            ChatDTO chatDTO,
            LocalDateTime end,
            Integer periodLengthInHours) {

        AggregatedStatisticsDTO result = new AggregatedStatisticsDTO();
        result.setChat(chatDTO);

        Map<Period, Map<String, UserSpecificMetricsDTO>> userSpecificMetrics = new HashMap<>();
        Map<Period, Map<String, Integer>> totalMetricsMap = new HashMap<>();
        Set<Period> periods = new HashSet<>();

        for (AggregatedUserSpecificMetric metric : chatMetrics) {
            Period period = new Period(metric.getPeriodStart(), metric.getPeriodStart().plusHours(periodLengthInHours));
            accumulateUserSpecificMetric(metric, userSpecificMetrics, period);
            accumulateTotalMetric(metric, totalMetricsMap, period);
            periods.add(period);
        }

        for (Period period : periods) {
            Map<String, UserSpecificMetricsDTO> userMetricsMap = userSpecificMetrics.get(period);
            Map<String, Integer> totalsMap = totalMetricsMap.get(period);

            AggregatedStatisticsPeriodDTO periodDTO = new AggregatedStatisticsPeriodDTO();
            periodDTO.setPeriodStart(period.getStart());
            if (end.isBefore(period.getEnd())) {
                periodDTO.setPeriodEnd(end);
            } else {
                periodDTO.setPeriodEnd(period.getEnd());
            }
            periodDTO.setTotalMetricsMap(totalsMap);
            periodDTO.setUserSpecificMetricsMap(userMetricsMap);

            result.getPeriods().add(periodDTO);
        }

        return result;
    }

    private void accumulateTotalMetric(
            AggregatedUserSpecificMetric metric,
            Map<Period, Map<String, Integer>> accumulator,
            Period period) {

        Map<String, Integer> totalMetricsMap = accumulator.get(period);
        if (totalMetricsMap == null) {
            totalMetricsMap = new HashMap<>();
            accumulator.put(period, totalMetricsMap);
        }

        Integer value = totalMetricsMap.get(metric.getMetricName());
        if (value == null) {
            value = 0;
        }

        Integer newValue = value + metric.getCount();
        totalMetricsMap.put(metric.getMetricName(), newValue);
    }

    private void accumulateUserSpecificMetric(
            AggregatedUserSpecificMetric metric,
            Map<Period, Map<String, UserSpecificMetricsDTO>> accumulator,
            Period period) {

        Map<String, UserSpecificMetricsDTO> userMetricsMap = accumulator.get(period);
        if (userMetricsMap == null) {
            userMetricsMap = new HashMap<>();
            accumulator.put(period, userMetricsMap);
        }

        UserSpecificMetricsDTO userMetricsDTO = userMetricsMap.get(metric.getUsername());
        if (userMetricsDTO == null) {
            userMetricsDTO = new UserSpecificMetricsDTO();
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(metric.getUsername());
            userMetricsDTO.setUser(userDTO);

            userMetricsMap.put(metric.getUsername(), userMetricsDTO);
        }

        Integer metricValue = userMetricsDTO.getMetricsMap().get(metric.getMetricName());
        if (metricValue == null) {
            metricValue = 0;
        }

        Integer newMetricValue = metricValue + metric.getCount();
        userMetricsDTO.getMetricsMap().put(metric.getMetricName(), newMetricValue);
    }

    private static class Period {
        private final LocalDateTime start;
        private final LocalDateTime end;

        public Period(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!start.equals(period.start)) return false;
            return end.equals(period.end);

        }

        @Override
        public int hashCode() {
            int result = start.hashCode();
            result = 31 * result + end.hashCode();
            return result;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalDateTime getEnd() {
            return end;
        }
    }
}

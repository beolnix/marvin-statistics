package com.beolnix.marvin.statistics.statistics;

import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import com.beolnix.marvin.statistics.api.model.UserSpecificMetricsDTO;
import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class StatisticsUtil {

    public List<UserSpecificMetric> convert(StatisticsDTO statisticsDTO) {
        String chatId = statisticsDTO.getChat().getId();
        LocalDateTime now = LocalDateTime.now();
        List<UserSpecificMetric> result = new LinkedList<>();

        for (UserSpecificMetricsDTO metricsDTO : statisticsDTO.getUserSpecificMetrics()) {
            UserSpecificMetric userSpecificMetric = new UserSpecificMetric();
            userSpecificMetric.setChatId(chatId);
            userSpecificMetric.setDateTime(now);
            userSpecificMetric.setMetricName(metricsDTO.getMetric().getMetricName());
            userSpecificMetric.setValue(metricsDTO.getMetric().getMetricValue());
            userSpecificMetric.setUsername(metricsDTO.getUser().getUsername());

            result.add(userSpecificMetric);
        }

        return result;
    }
}

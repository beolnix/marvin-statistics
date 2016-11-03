package com.beolnix.marvin.statistics.statistics;

import com.beolnix.marvin.statistics.api.model.AggregatedStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import com.beolnix.marvin.statistics.statistics.domain.dao.UserSpecificMetricDAO;
import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import com.mongodb.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class StatisticsService {

    private final static Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    private final StatisticsUtil util;
    private final UserSpecificMetricDAO metricDAO;
    private final MongoTemplate mongo;

    @Autowired
    public StatisticsService(StatisticsUtil util, UserSpecificMetricDAO metricDAO, MongoTemplate mongo) {
        this.util = util;
        this.metricDAO = metricDAO;
        this.mongo = mongo;
    }

    public void saveStatistics(StatisticsDTO statisticsDTO) {
        List<UserSpecificMetric> newMetrics = util.convert(statisticsDTO);
        metricDAO.save(newMetrics);
        logger.debug("New metrics are persisted: {}", newMetrics);
    }

    public AggregatedStatisticsDTO getAggregatedStatistics(LocalDateTime start,
                                                           LocalDateTime end,
                                                           Integer periodLengthInMinutes,
                                                           String chatId) {
        Criteria matchCriteria = Criteria.where("chatId").is(chatId);

        CommandResult result = mongo.executeCommand("");

        return null;
    }


}

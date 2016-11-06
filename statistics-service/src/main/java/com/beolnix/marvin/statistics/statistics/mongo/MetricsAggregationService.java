package com.beolnix.marvin.statistics.statistics.mongo;

import com.beolnix.marvin.statistics.statistics.domain.model.AggregatedUserSpecificMetric;
import com.beolnix.marvin.statistics.statistics.domain.model.UserSpecificMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricsAggregationService {

    @Autowired
    private MongoTemplate mongo;

    public List<AggregatedUserSpecificMetric> aggregateForTimePeriod(
            LocalDateTime start,
            LocalDateTime end,
            Integer periodInHours) {

        Aggregation aggregation = Aggregation.newAggregation(
                new CustomMatch(start, end), new CustomAggregation(start, periodInHours)
        );

        AggregationResults<AggregatedUserSpecificMetric> result = mongo.aggregate(
                aggregation,
                UserSpecificMetric.class,
                AggregatedUserSpecificMetric.class
        );

        return result.getMappedResults();
    }


}

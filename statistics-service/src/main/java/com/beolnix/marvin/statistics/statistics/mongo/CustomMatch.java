package com.beolnix.marvin.statistics.statistics.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class CustomMatch implements AggregationOperation {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String chatId;
    private final Optional<String> metricName;

    public CustomMatch(LocalDateTime start, LocalDateTime end, String chatId, Optional<String> metricName) {
        this.start = start;
        this.end = end;
        this.chatId = chatId;
        this.metricName = metricName;
    }

    @Override
    public DBObject toDBObject(AggregationOperationContext context) {

        BasicDBObject dateConditions = new BasicDBObject();
        dateConditions.put("$lt", Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));
        dateConditions.put("$gt", Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));

        BasicDBObject chatConditions = new BasicDBObject();
        chatConditions.put("$eq", chatId);

        BasicDBObject match = new BasicDBObject();
        match.put("dateTime", dateConditions);
        match.put("chatId", chatConditions);

        metricName.ifPresent(metricNameValue -> {
            BasicDBObject metricNameConditions = new BasicDBObject();
            metricNameConditions.put("$eq", metricNameValue);
            match.put("metricName", metricNameConditions);
        });

        return new BasicDBObject("$match", match);
    }
}

package com.beolnix.marvin.statistics.statistics.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CustomMatch implements AggregationOperation {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public CustomMatch(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public DBObject toDBObject(AggregationOperationContext context) {
//        BasicDBObject matchConditions = new BasicDBObject();
//        matchConditions.put("$eq", "testUser9");

        BasicDBObject dateConditions = new BasicDBObject();
        dateConditions.put("$lt", Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));
        dateConditions.put("$gt", Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));


        BasicDBObject match = new BasicDBObject();
//        match.put("username", matchConditions);
        match.put("dateTime", dateConditions);

        return new BasicDBObject("$match", match);
    }
}

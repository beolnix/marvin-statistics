package com.beolnix.marvin.statistics.statistics.mongo;

import com.google.gag.annotation.disclaimer.AnimalsHarmedDuringTheMaking;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CustomAggregation implements AggregationOperation {

    private final LocalDateTime start;
    private final Integer periodInHours;

    public CustomAggregation(LocalDateTime start, Integer periodInHours) {
        this.start = start;
        this.periodInHours = periodInHours;
    }



//      The shitty java method below is just to implement this mongo aggregation
//
//    {
//        "$group": {
//            "_id": {
//                "userName" : "$username",
//                        "periodStart" : {
//                    "$add": [
//                    { "$subtract": [
//                        { "$subtract": [ "$dateTime", ISODate("2016-11-01T00:00:00.000Z") ] },
//                        { "$mod": [
//                            { "$subtract": [ "$dateTime", ISODate("2016-11-01T00:00:00.000Z") ] },
//                            1000 * 60 * 60 * 4
//                            ]}
//                        ]},
//                    ISODate("2016-11-01T00:00:00.000Z")
//                    ]
//                }
//            },
//            "count": { "$sum": "$value" }
//        }
//    }
//
//     This aggregation splits the time range for the time periods and calculates metrics per each user independently
//     in a resultes time periods.

    @AnimalsHarmedDuringTheMaking(animal = "duke", number = 1)
    @Override
    public DBObject toDBObject(AggregationOperationContext context) {

        Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());

        BasicDBList subtractArgs = new BasicDBList();
        subtractArgs.add("$dateTime");
        subtractArgs.add(startDate);

        LinkedList<Object> modArgs = new LinkedList();
        modArgs.add(new BasicDBObject("$subtract", subtractArgs));
        modArgs.add(1000 * 60 * 60 * periodInHours);

        LinkedList<DBObject> subtract = new LinkedList();
        subtract.add(new BasicDBObject("$subtract", subtractArgs));
        subtract.add(new BasicDBObject("$mod", modArgs));

        List<Object> addArgs = new LinkedList<>();
        addArgs.add(new BasicDBObject("$subtract", subtract));
        addArgs.add(startDate);

        BasicDBObject id = new BasicDBObject();
        id.put("username", "$username");
        id.put("periodStart", new BasicDBObject("$add", addArgs));

        BasicDBObject group = new BasicDBObject();
        group.put("_id", id);
        group.put("count", new BasicDBObject("$sum", "$value"));


        return new BasicDBObject("$group", group);
    }
}

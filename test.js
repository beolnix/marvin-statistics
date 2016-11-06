db.getCollection('userSpecificMetric').aggregate([
    { "$match" :
            {"dateTime" : {
                "$lt" : ISODate("2016-11-04T05:00:00.000Z"),
                "$gt" : ISODate("2016-11-04T00:00:00.000Z")
                }
            }
    },
    { "$group": {
        "_id": {
        "userName" : "$username",
        "periodStart" : {"$add": [
                { "$subtract": [
                    { "$subtract": [ "$dateTime", ISODate("2016-11-01T00:00:00.000Z") ] },
                    { "$mod": [
                        { "$subtract": [ "$dateTime", ISODate("2016-11-01T00:00:00.000Z") ] },
                        1000 * 60 * 60
                    ]}
                ] },
                ISODate("2016-11-01T00:00:00.000Z")
            ]}
        },
        "count": { "$sum": "$value" }
    }},
    {
	$sort : {"_id.periodStart" : 1}
    }]
);

{
    "$group": {
        "_id": {
            "userName" : "$username",
            "periodStart" : {
                "$add": [
                    { "$subtract": [
                        { "$subtract": [ "$dateTime", ISODate("2016-11-01T00:00:00.000Z") ] },
                        { "$mod": [
                                { "$subtract": [ "$dateTime", ISODate("2016-11-01T00:00:00.000Z") ] },
                                1000 * 60 * 60 * 4
                        ]}
                    ]},
                    ISODate("2016-11-01T00:00:00.000Z")
                ]
            }
        },
        "count": { "$sum": "$value" }
    }
}
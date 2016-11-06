package com.beolnix.marvin.statistics.configuration;

import com.beolnix.marvin.statistics.configuration.converter.DateToLocalDateTimeConverter;
import com.beolnix.marvin.statistics.configuration.converter.LocalDateTimeToDateConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.TimeZone;


/**
 * Created by beolnix on 18/01/16.
 */

@Configuration
@EnableMongoRepositories("com.beolnix.marvin")
@EnableConfigurationProperties(SecurityConfig.class)
public class StatisticsConfiguration extends AbstractMongoConfiguration {

    @Value("${statistics.mongo.host}")
    private String mongoHost;

    @Value("${statistics.mongo.database}")
    private String mongoDatabase;

    @Value("${statistics.timeZone}")
    private String timezone;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String getDatabaseName() {
        return mongoDatabase;
    }

    @Override
    public MongoClient mongo() throws Exception {

        MongoClient client = new MongoClient(mongoHost);

        return client;
    }

    @PostConstruct
    public void initTimeZone() {
        TimeZone dateTimeZone = TimeZone.getTimeZone(timezone);
        TimeZone.setDefault(dateTimeZone);
    }

    @Override
    public CustomConversions customConversions() {
        return new CustomConversions(Arrays.asList(
                new DateToLocalDateTimeConverter(),
                new LocalDateTimeToDateConverter()
            )
        );
    }




}

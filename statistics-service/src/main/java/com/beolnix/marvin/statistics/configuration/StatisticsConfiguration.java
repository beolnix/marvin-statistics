package com.beolnix.marvin.statistics.configuration;

import com.beolnix.marvin.statistics.configuration.converter.DateToLocalDateTimeConverter;
import com.beolnix.marvin.statistics.configuration.converter.LocalDateTimeToDateConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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

    @Override
    protected String getDatabaseName() {
        return mongoDatabase;
    }

    @Override
    public MongoClient mongo() throws Exception {

        MongoClient client = new MongoClient(mongoHost);

        return client;
    }

    @Bean
    public ObjectMapper jsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        //Fully qualified path shows I am using latest enum
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS , false);
        objectMapper.getSerializationConfig().with(new ISO8601DateFormat());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TimeZone dateTimeZone = TimeZone.getTimeZone(timezone);
        TimeZone.setDefault(dateTimeZone);
        objectMapper.setTimeZone(dateTimeZone);
        return objectMapper;
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

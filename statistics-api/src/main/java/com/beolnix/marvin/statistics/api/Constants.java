package com.beolnix.marvin.statistics.api;

/**
 * Created by beolnix on 24/01/16.
 */
public class Constants {
    public static final String FEIGN_SERVICE = "statistics";
    public static final String FEIGN_CLIENT_NAME = "https://" + FEIGN_SERVICE;
    public static final String STATISTICS_URL_ROOT = "/api/v1";

    public static final String API_KEY_HEADER = "X-KEY";
    public static final String API_SECRET_HEADER = "X-SECRET";
    public static final String API_DATE_HEADER = "X-DATE";
}

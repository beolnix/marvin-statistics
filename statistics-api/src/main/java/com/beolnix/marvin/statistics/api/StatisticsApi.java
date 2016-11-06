package com.beolnix.marvin.statistics.api;

import com.beolnix.marvin.statistics.api.model.AggregatedStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Api(value = "statistics", description = "Statistics API")
@FeignClient(Constants.FEIGN_CLIENT_NAME)
@RequestMapping(Constants.STATISTICS_URL_ROOT)
public interface StatisticsApi {

    @ApiOperation(value = "Method persists passed statistics.", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request - if the request body is in a wrong format.")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "statistics data transfer object",
                    name = "statisticsDTO",
                    dataType = "StatisticsDTO",
                    required = true,
                    paramType = "body"),
    })
    @RequestMapping(method = RequestMethod.POST, value = "/statistics", consumes = MediaType.APPLICATION_JSON_VALUE)
    void postStatistics(@RequestBody StatisticsDTO statisticsDTO);


    /**
     * Usage example
     *
     * curl -H 'X-KEY: test_read_key' -H 'X-SECRET: test_read_auth' 'http://localhost:51463/api/v1/aggregated-statistics?start=2016-11-04T00:00:00Z&end=2016-11-04T09:00:00Z&periodLengthInHours=2&chatId=581f05db8d6aa9f2b2651749'
     */
    @ApiOperation(value = "Method returns statistics aggretated for requested periods.", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request - if parameters are provided in a wrong format.")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Start date time of a requested statistics in ISO-8601 format.",
                    name = "start",
                    dataType = "DateTime",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "End date time of a requested statistics in ISO-8601 format.",
                    name = "end",
                    dataType = "DateTime",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Length of the aggregation time periods within the given start and end limits in hours.",
                    name = "periodLengthInHours",
                    dataType = "Integer",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Chat id.",
                    name = "chatId",
                    dataType = "String",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Metric name.",
                    name = "metricName",
                    dataType = "String",
                    required = false,
                    paramType = "query")
    })

    @RequestMapping(method = RequestMethod.GET, value = "/aggregated-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    AggregatedStatisticsDTO getAggregatedStatistics(
            @RequestParam(value="start", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(value="end", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(value="periodLengthInHours", required = true) Integer periodLengthInHours,
            @RequestParam(value="chatId", required = true) String chatId,
            @RequestParam(value="metricName", required = false) String metricName
    );
}
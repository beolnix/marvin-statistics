package com.beolnix.marvin.statistics.api;

import com.beolnix.marvin.statistics.api.model.AggregatedStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Api(value = "statistics", description = "Statistics API")
@FeignClient(Constants.FEIGN_CLIENT_NAME)
@RequestMapping(Constants.STATISTICS_URL_ROOT)
public interface StatisticsApi {

    @ApiOperation(value = "Method persists passed statistics.", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request - if the request body is in a wrong format.")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/statistics", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "statistics data transfer object",
                    name = "statisticsDTO",
                    dataType = "StatisticsDTO",
                    required = true,
                    paramType = "body"),
    })
    void postStatistics(@RequestBody StatisticsDTO statisticsDTO);


    @ApiOperation(value = "Method returns statistics aggretated for requested periods.", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request - if parameters are provided in a wrong format.")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/aggregated-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
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
    AggregatedStatisticsDTO getAggregatedStatistics(
            LocalDateTime start,
            LocalDateTime end,
            Integer periodLengthInHours,
            String chatId,
            String metricName
    );
}
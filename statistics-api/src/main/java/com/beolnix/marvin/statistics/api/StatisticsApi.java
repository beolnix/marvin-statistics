package com.beolnix.marvin.statistics.api;

import com.beolnix.marvin.statistics.api.model.DayStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.MonthStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import com.beolnix.marvin.statistics.api.model.YearStatisticsDTO;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

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

    @ApiOperation(value = "Method returns statistics aggretated for requested years.", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.GET, value = "/yearly-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Years list of a requested statistics",
                    name = "years",
                    dataType = "Array",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Id of the chat",
                    name = "chatId",
                    dataType = "String",
                    required = true,
                    paramType = "query")
    })
    YearStatisticsDTO yearlyStatistics(
            @RequestParam Set<Integer> years,
            @RequestParam String chatId
    );

    @ApiOperation(value = "Method returns statistics aggretated for requested months.", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.GET, value = "/monthly-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Year of a requested statistics",
                    name = "year",
                    dataType = "Int",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Months list of a requested statistics",
                    name = "months",
                    dataType = "Array",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Id of the chat",
                    name = "chatId",
                    dataType = "String",
                    required = true,
                    paramType = "query")
    })
    MonthStatisticsDTO monthlyStatistics(
            @RequestParam Integer year,
            @RequestParam Set<Integer> months,
            @RequestParam String chatId
    );

    @ApiOperation(value = "Method returns statistics aggretated for requested days of a month.", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.GET, value = "/daily-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "Year of a requested statistics",
                    name = "year",
                    dataType = "Int",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Month of a requested statistics",
                    name = "month",
                    dataType = "Int",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Days list of a requested statistics",
                    name = "days",
                    dataType = "Array",
                    required = true,
                    paramType = "query"),
            @ApiImplicitParam(
                    value = "Id of the chat",
                    name = "chatId",
                    dataType = "String",
                    required = true,
                    paramType = "query")
    })
    DayStatisticsDTO dailyStatistics(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Set<Integer> days,
            @RequestParam String chatId
    );
}

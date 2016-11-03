package com.beolnix.marvin.statistics.statistics;

import com.beolnix.marvin.statistics.api.StatisticsApi;
import com.beolnix.marvin.statistics.api.model.DayStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.MonthStatisticsDTO;
import com.beolnix.marvin.statistics.api.model.StatisticsDTO;
import com.beolnix.marvin.statistics.api.model.YearStatisticsDTO;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

@RestController
public class StatisticsController implements StatisticsApi {
    @Override
    public void postStatistics(StatisticsDTO statisticsDTO) {
        throw new NotImplementedException();
    }

    @Override
    public YearStatisticsDTO yearlyStatistics(Set<Integer> years, String chatId) {
        throw new NotImplementedException();
    }

    @Override
    public MonthStatisticsDTO monthlyStatistics(Integer year, Set<Integer> months, String chatId) {
        throw new NotImplementedException();
    }

    @Override
    public DayStatisticsDTO dailyStatistics( Integer year, Integer month, Set<Integer> days, String chatId) {
        throw new NotImplementedException();
    }
}

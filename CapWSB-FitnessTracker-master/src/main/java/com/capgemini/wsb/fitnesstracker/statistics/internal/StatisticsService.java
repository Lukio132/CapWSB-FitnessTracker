package com.capgemini.wsb.fitnesstracker.statistics.internal;



import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;

import java.util.List;

public interface StatisticsService {
    List<StatisticsDto> getAllStatistics();
    StatisticsDto createStatistics(StatisticsDto statisticsDto);
    StatisticsDto updateStatistics(Long id, StatisticsDto statisticsDto);
    StatisticsDto getStatisticsByUserId(Long userId);
    void deleteStatistics(Long id);
}

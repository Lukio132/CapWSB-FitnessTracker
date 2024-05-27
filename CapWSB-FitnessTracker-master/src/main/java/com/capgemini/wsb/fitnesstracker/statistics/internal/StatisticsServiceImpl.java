package com.capgemini.wsb.fitnesstracker.statistics.internal;



import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final StatisticsMapper statisticsMapper;

    @Override
    public List<StatisticsDto> getAllStatistics() {
        return statisticsRepository.findAll().stream()
                .map(statisticsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StatisticsDto createStatistics(StatisticsDto statisticsDto) {
        Statistics statistics = statisticsMapper.toEntity(statisticsDto);
        Statistics savedStatistics = statisticsRepository.save(statistics);
        return statisticsMapper.toDto(savedStatistics);
    }

    @Override
    public StatisticsDto updateStatistics(Long id, StatisticsDto statisticsDto) {
        Statistics existingStatistics = statisticsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistics not found"));
        existingStatistics.setTotalTrainings(statisticsDto.getTotalTrainings());
        existingStatistics.setTotalDistance(statisticsDto.getTotalDistance());
        existingStatistics.setTotalCaloriesBurned(statisticsDto.getTotalCaloriesBurned());
        Statistics updatedStatistics = statisticsRepository.save(existingStatistics);
        return statisticsMapper.toDto(updatedStatistics);
    }

    @Override
    public StatisticsDto getStatisticsByUserId(Long userId) {
        Statistics statistics = statisticsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Statistics not found"));
        return statisticsMapper.toDto(statistics);
    }

    @Override
    public void deleteStatistics(Long id) {
        statisticsRepository.deleteById(id);
    }
}

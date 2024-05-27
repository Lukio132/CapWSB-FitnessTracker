package com.capgemini.wsb.fitnesstracker.statistics.internal;



import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsServiceImpl statisticsService;

    @GetMapping
    public List<StatisticsDto> getAllStatistics() {
        return statisticsService.getAllStatistics();
    }

    @PostMapping
    public StatisticsDto createStatistics(@RequestBody StatisticsDto statisticsDto) {
        return statisticsService.createStatistics(statisticsDto);
    }

    @PutMapping("/{id}")
    public StatisticsDto updateStatistics(@PathVariable Long id, @RequestBody StatisticsDto statisticsDto) {
        return statisticsService.updateStatistics(id, statisticsDto);
    }

    @GetMapping("/user/{userId}")
    public StatisticsDto getStatisticsByUserId(@PathVariable Long userId) {
        return statisticsService.getStatisticsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteStatistics(@PathVariable Long id) {
        statisticsService.deleteStatistics(id);
    }
}

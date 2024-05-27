package com.capgemini.wsb.fitnesstracker.statistics.internal;



import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {

    @Autowired
    private UserRepository userRepository;

    public StatisticsDto toDto(Statistics statistics) {
        StatisticsDto dto = new StatisticsDto();
        dto.setId(statistics.getId());
        dto.setUserId(statistics.getUser().getId());
        dto.setTotalTrainings(statistics.getTotalTrainings());
        dto.setTotalDistance(statistics.getTotalDistance());
        dto.setTotalCaloriesBurned(statistics.getTotalCaloriesBurned());
        return dto;
    }

    public Statistics toEntity(StatisticsDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        return new Statistics(user, dto.getTotalTrainings(), dto.getTotalDistance(), dto.getTotalCaloriesBurned());
    }
}

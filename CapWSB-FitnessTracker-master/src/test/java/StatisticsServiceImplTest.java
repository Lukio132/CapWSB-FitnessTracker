
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsMapper;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsRepository;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticsServiceImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private StatisticsMapper statisticsMapper;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    private User user;
    private Statistics statistics;
    private StatisticsDto statisticsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);

        statistics = new Statistics(user, 10, 100.0, 2000);
        statistics.setId(1L);

        statisticsDto = new StatisticsDto();
        statisticsDto.setId(1L);
        statisticsDto.setUserId(1L);
        statisticsDto.setTotalTrainings(10);
        statisticsDto.setTotalDistance(100.0);
        statisticsDto.setTotalCaloriesBurned(2000);
    }



    @Test
    void shouldCreateStatistics() {
        when(statisticsMapper.toEntity(statisticsDto)).thenReturn(statistics);
        when(statisticsRepository.save(statistics)).thenReturn(statistics);
        when(statisticsMapper.toDto(statistics)).thenReturn(statisticsDto);

        StatisticsDto createdStatistics = statisticsService.createStatistics(statisticsDto);

        assertNotNull(createdStatistics);
        assertEquals(statisticsDto, createdStatistics);

        verify(statisticsMapper, times(1)).toEntity(statisticsDto);
        verify(statisticsRepository, times(1)).save(statistics);
        verify(statisticsMapper, times(1)).toDto(statistics);
    }

    @Test
    void shouldUpdateStatistics() {
        when(statisticsRepository.findById(1L)).thenReturn(Optional.of(statistics));
        when(statisticsRepository.save(statistics)).thenReturn(statistics);
        when(statisticsMapper.toDto(statistics)).thenReturn(statisticsDto);

        StatisticsDto updatedStatistics = statisticsService.updateStatistics(1L, statisticsDto);

        assertNotNull(updatedStatistics);
        assertEquals(statisticsDto, updatedStatistics);

        verify(statisticsRepository, times(1)).findById(1L);
        verify(statisticsRepository, times(1)).save(statistics);
        verify(statisticsMapper, times(1)).toDto(statistics);
    }

    @Test
    void shouldGetStatisticsByUserId() {
        when(statisticsRepository.findByUserId(1L)).thenReturn(Optional.of(statistics));
        when(statisticsMapper.toDto(statistics)).thenReturn(statisticsDto);

        StatisticsDto foundStatistics = statisticsService.getStatisticsByUserId(1L);

        assertNotNull(foundStatistics);
        assertEquals(statisticsDto, foundStatistics);

        verify(statisticsRepository, times(1)).findByUserId(1L);
        verify(statisticsMapper, times(1)).toDto(statistics);
    }

    @Test
    void shouldDeleteStatistics() {
        doNothing().when(statisticsRepository).deleteById(1L);

        statisticsService.deleteStatistics(1L);

        verify(statisticsRepository, times(1)).deleteById(1L);
    }
}

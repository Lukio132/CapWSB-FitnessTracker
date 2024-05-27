

import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsDto;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsController;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StatisticsControllerTest {

    @Mock
    private StatisticsServiceImpl statisticsService;

    @InjectMocks
    private StatisticsController statisticsController;

    private MockMvc mockMvc;

    private StatisticsDto statisticsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();

        statisticsDto = new StatisticsDto();
        statisticsDto.setId(1L);
        statisticsDto.setUserId(1L);
        statisticsDto.setTotalTrainings(10);
        statisticsDto.setTotalDistance(100.0);
        statisticsDto.setTotalCaloriesBurned(2000);
    }

    @Test
    void shouldGetAllStatistics() throws Exception {
        when(statisticsService.getAllStatistics()).thenReturn(List.of(statisticsDto));

        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(statisticsService, times(1)).getAllStatistics();
    }

    @Test
    void shouldCreateStatistics() throws Exception {
        when(statisticsService.createStatistics(any(StatisticsDto.class))).thenReturn(statisticsDto);

        mockMvc.perform(post("/api/statistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"totalTrainings\":10,\"totalDistance\":100.0,\"totalCaloriesBurned\":2000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(statisticsService, times(1)).createStatistics(any(StatisticsDto.class));
    }

    @Test
    void shouldUpdateStatistics() throws Exception {
        when(statisticsService.updateStatistics(any(Long.class), any(StatisticsDto.class))).thenReturn(statisticsDto);

        mockMvc.perform(put("/api/statistics/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"totalTrainings\":10,\"totalDistance\":100.0,\"totalCaloriesBurned\":2000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(statisticsService, times(1)).updateStatistics(any(Long.class), any(StatisticsDto.class));
    }

    @Test
    void shouldGetStatisticsByUserId() throws Exception {
        when(statisticsService.getStatisticsByUserId(1L)).thenReturn(statisticsDto);

        mockMvc.perform(get("/api/statistics/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(statisticsService, times(1)).getStatisticsByUserId(1L);
    }

    @Test
    void shouldDeleteStatistics() throws Exception {
        doNothing().when(statisticsService).deleteStatistics(1L);

        mockMvc.perform(delete("/api/statistics/1"))
                .andExpect(status().isOk());

        verify(statisticsService, times(1)).deleteStatistics(1L);
    }
}

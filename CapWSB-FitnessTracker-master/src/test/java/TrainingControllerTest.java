

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingController;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TrainingControllerTest {

    @Mock
    private TrainingServiceImpl trainingService;

    @InjectMocks
    private TrainingController trainingController;

    private MockMvc mockMvc;

    private TrainingDto trainingDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();

        trainingDto = new TrainingDto();
        trainingDto.setId(1L);
        trainingDto.setUserId(1L);
        trainingDto.setStartTime(new Date());
        trainingDto.setEndTime(new Date());
        trainingDto.setActivityType("RUNNING");
        trainingDto.setDistance(10.0);
        trainingDto.setAverageSpeed(5.0);
    }

    @Test
    void shouldGetAllTrainings() throws Exception {
        when(trainingService.getAllTrainings()).thenReturn(List.of(trainingDto));

        mockMvc.perform(get("/api/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(trainingService, times(1)).getAllTrainings();
    }

    @Test
    void shouldGetTrainingsByUser() throws Exception {
        when(trainingService.getTrainingsByUser(1L)).thenReturn(List.of(trainingDto));

        mockMvc.perform(get("/api/trainings/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(trainingService, times(1)).getTrainingsByUser(1L);
    }

    @Test
    void shouldGetTrainingsByDate() throws Exception {
        when(trainingService.getTrainingsByDate(any(Date.class))).thenReturn(List.of(trainingDto));

        mockMvc.perform(get("/api/trainings/date/2023-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(trainingService, times(1)).getTrainingsByDate(any(Date.class));
    }

    @Test
    void shouldGetTrainingsByActivityType() throws Exception {
        when(trainingService.getTrainingsByActivityType("RUNNING")).thenReturn(List.of(trainingDto));

        mockMvc.perform(get("/api/trainings/activity/RUNNING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(trainingService, times(1)).getTrainingsByActivityType("RUNNING");
    }

    @Test
    void shouldCreateTraining() throws Exception {
        when(trainingService.createTraining(any(TrainingDto.class))).thenReturn(trainingDto);

        mockMvc.perform(post("/api/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"startTime\":\"2023-01-01T08:00:00\",\"endTime\":\"2023-01-01T09:00:00\",\"activityType\":\"RUNNING\",\"distance\":10.0,\"averageSpeed\":5.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(trainingService, times(1)).createTraining(any(TrainingDto.class));
    }

    @Test
    void shouldUpdateTraining() throws Exception {
        when(trainingService.updateTraining(any(Long.class), any(TrainingDto.class))).thenReturn(trainingDto);

        mockMvc.perform(put("/api/trainings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"startTime\":\"2023-01-01T08:00:00\",\"endTime\":\"2023-01-01T09:00:00\",\"activityType\":\"RUNNING\",\"distance\":10.0,\"averageSpeed\":5.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(trainingService, times(1)).updateTraining(any(Long.class), any(TrainingDto.class));
    }
}

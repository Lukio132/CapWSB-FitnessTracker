

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private User user;
    private Training training;
    private TrainingDto trainingDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);

        training = new Training(user, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        training.setId(1L);

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
    void shouldGetAllTrainings() {
        when(trainingRepository.findAll()).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.getAllTrainings();

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        assertEquals(trainingDto, trainings.get(0));

        verify(trainingRepository, times(1)).findAll();
        verify(trainingMapper, times(1)).toDto(training);
    }

    @Test
    void shouldGetTrainingsByUser() {
        when(trainingRepository.findByUserId(1L)).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.getTrainingsByUser(1L);

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        assertEquals(trainingDto, trainings.get(0));

        verify(trainingRepository, times(1)).findByUserId(1L);
        verify(trainingMapper, times(1)).toDto(training);
    }

    @Test
    void shouldGetTrainingsByDate() {
        Date date = new Date();
        when(trainingRepository.findByEndTimeBefore(date)).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.getTrainingsByDate(date);

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        assertEquals(trainingDto, trainings.get(0));

        verify(trainingRepository, times(1)).findByEndTimeBefore(date);
        verify(trainingMapper, times(1)).toDto(training);
    }

    @Test
    void shouldGetTrainingsByActivityType() {
        when(trainingRepository.findByActivityType(ActivityType.RUNNING)).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.getTrainingsByActivityType("RUNNING");

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        assertEquals(trainingDto, trainings.get(0));

        verify(trainingRepository, times(1)).findByActivityType(ActivityType.RUNNING);
        verify(trainingMapper, times(1)).toDto(training);
    }

    @Test
    void shouldCreateTraining() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingMapper.toEntity(trainingDto)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        TrainingDto createdTraining = trainingService.createTraining(trainingDto);

        assertNotNull(createdTraining);
        assertEquals(trainingDto, createdTraining);

        verify(trainingMapper, times(1)).toEntity(trainingDto);
        verify(trainingRepository, times(1)).save(training);
        verify(trainingMapper, times(1)).toDto(training);
    }

    @Test
    void shouldUpdateTraining() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        TrainingDto updatedTraining = trainingService.updateTraining(1L, trainingDto);

        assertNotNull(updatedTraining);
        assertEquals(trainingDto, updatedTraining);

        verify(trainingRepository, times(1)).findById(1L);
        verify(trainingRepository, times(1)).save(training);
        verify(trainingMapper, times(1)).toDto(training);
    }
}

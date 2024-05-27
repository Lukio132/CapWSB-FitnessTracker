package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByUser(Long userId) {
        return trainingRepository.findByUserId(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByDate(Date date) {
        return trainingRepository.findByEndTimeBefore(date).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(String activityType) {
        return trainingRepository.findByActivityType(ActivityType.valueOf(activityType.toUpperCase())).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toDto(savedTraining);
    }

    @Override
    public TrainingDto updateTraining(Long id, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(id).orElseThrow(() -> new RuntimeException("Training not found"));
        existingTraining.setStartTime(trainingDto.getStartTime());
        existingTraining.setEndTime(trainingDto.getEndTime());
        existingTraining.setActivityType(ActivityType.valueOf(trainingDto.getActivityType().toUpperCase()));
        existingTraining.setDistance(trainingDto.getDistance());
        existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());
        Training updatedTraining = trainingRepository.save(existingTraining);
        return trainingMapper.toDto(updatedTraining);
    }
}

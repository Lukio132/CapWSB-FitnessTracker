package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<TrainingDto> getAllTrainings();
    List<TrainingDto> getTrainingsByUser(Long userId);
    List<TrainingDto> getTrainingsByDate(Date date);
    List<TrainingDto> getTrainingsByActivityType(String activityType);
    TrainingDto createTraining(TrainingDto trainingDto);
    TrainingDto updateTraining(Long id, TrainingDto trainingDto);
}

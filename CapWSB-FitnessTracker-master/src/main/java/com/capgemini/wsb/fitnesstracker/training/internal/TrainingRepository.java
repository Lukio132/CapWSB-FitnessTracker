package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserId(Long userId);

    List<Training> findByEndTimeBefore(Date date);

    List<Training> findByActivityType(ActivityType activityType);
}

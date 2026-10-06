package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.GoalRequest;
import com.example.fitnessapp.dto.request.GoalUpdateRequest;
import com.example.fitnessapp.dto.response.GoalDto;
import com.example.fitnessapp.entity.Goal;
import com.example.fitnessapp.entity.Notification;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.enums.GoalStatus;
import com.example.fitnessapp.enums.NotificationType;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.GoalRepository;
import com.example.fitnessapp.repository.NotificationRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.service.GoalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public GoalServiceImpl(
            GoalRepository goalRepository,
            UserRepository userRepository,
            NotificationRepository notificationRepository
    ) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public GoalDto createGoal(Long userId, GoalRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Double currentVal = request.getCurrentValue() != null ? request.getCurrentValue() : request.getStartValue();

        Goal goal = new Goal(
                user,
                request.getTitle(),
                request.getGoalType(),
                request.getStartValue(),
                request.getTargetValue(),
                currentVal,
                request.getTargetDate()
        );

        if (goal.getProgressPercentage() >= 100.0) {
            goal.setStatus(GoalStatus.ACHIEVED);
        }

        Goal saved = goalRepository.save(goal);
        return new GoalDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDto> getUserGoals(Long userId, GoalStatus status) {
        List<Goal> goals;
        if (status != null) {
            goals = goalRepository.findByUserIdAndStatus(userId, status);
        } else {
            goals = goalRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }
        return goals.stream().map(GoalDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDto getGoalById(Long userId, Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> g.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", goalId));
        return new GoalDto(goal);
    }

    @Override
    @Transactional
    public GoalDto updateGoal(Long userId, Long goalId, GoalUpdateRequest request) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> g.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", goalId));

        if (request.getCurrentValue() != null) {
            goal.setCurrentValue(request.getCurrentValue());
            if (goal.getProgressPercentage() >= 100.0 && goal.getStatus() != GoalStatus.ACHIEVED) {
                goal.setStatus(GoalStatus.ACHIEVED);
                Notification notification = new Notification(
                        goal.getUser(),
                        "Goal Achieved! 🏆",
                        "Congratulations! You reached your goal: " + goal.getTitle() + "!",
                        NotificationType.CONGRATULATIONS
                );
                notificationRepository.save(notification);
            }
        }

        if (request.getStatus() != null) {
            goal.setStatus(request.getStatus());
        }

        Goal updated = goalRepository.save(goal);
        return new GoalDto(updated);
    }

    @Override
    @Transactional
    public void deleteGoal(Long userId, Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> g.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", goalId));
        goalRepository.delete(goal);
    }
}

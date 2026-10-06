package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.DailyProgressRequest;
import com.example.fitnessapp.dto.response.*;
import com.example.fitnessapp.entity.DailyProgress;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.UserProfile;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.DailyProgressRepository;
import com.example.fitnessapp.repository.UserProfileRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.service.DietService;
import com.example.fitnessapp.service.ProgressService;
import com.example.fitnessapp.service.WaterService;
import com.example.fitnessapp.service.WorkoutService;
import com.example.fitnessapp.util.FitnessCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final DailyProgressRepository dailyProgressRepository;
    private final WorkoutService workoutService;
    private final DietService dietService;
    private final WaterService waterService;

    public ProgressServiceImpl(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            DailyProgressRepository dailyProgressRepository,
            WorkoutService workoutService,
            DietService dietService,
            WaterService waterService
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.dailyProgressRepository = dailyProgressRepository;
        this.workoutService = workoutService;
        this.dietService = dietService;
        this.waterService = waterService;
    }

    @Override
    @Transactional
    public DailyProgressDto logProgress(Long userId, DailyProgressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        LocalDate date = request.getLogDate() != null ? request.getLogDate() : LocalDate.now();

        DailyProgress progress = dailyProgressRepository.findByUserIdAndLogDate(userId, date)
                .orElseGet(() -> new DailyProgress(user, date));

        if (request.getWeightKg() != null && request.getWeightKg() > 0) {
            progress.setWeightKg(request.getWeightKg());

            // Synchronize with UserProfile and recalculate metrics
            UserProfile profile = user.getProfile();
            if (profile != null) {
                profile.setWeightKg(request.getWeightKg());
                double bmi = FitnessCalculator.calculateBmi(profile.getHeightCm(), profile.getWeightKg());
                profile.setBmi(bmi);
                profile.setBmiCategory(FitnessCalculator.determineBmiCategory(bmi));

                double bmr = FitnessCalculator.calculateBmr(profile.getGender(), profile.getWeightKg(), profile.getHeightCm(), profile.getAge());
                profile.setBmr(bmr);

                double tdee = FitnessCalculator.calculateTdee(bmr, profile.getActivityLevel());
                profile.setTdee(tdee);

                int calorieTarget = FitnessCalculator.calculateDailyCalorieTarget(tdee, profile.getFitnessGoal());
                profile.setDailyCalorieTarget(calorieTarget);

                Map<String, Integer> macros = FitnessCalculator.calculateMacros(calorieTarget, profile.getWeightKg(), profile.getFitnessGoal());
                profile.setProteinTargetG(macros.get("protein"));
                profile.setCarbsTargetG(macros.get("carbs"));
                profile.setFatTargetG(macros.get("fat"));

                profile.setWaterTargetMl(FitnessCalculator.calculateWaterTarget(profile.getWeightKg(), profile.getActivityLevel()));

                userProfileRepository.save(profile);
            }
        }

        if (request.getSleepHours() != null) {
            progress.setSleepHours(request.getSleepHours());
        }

        if (request.getStepsCount() != null) {
            progress.setStepsCount(request.getStepsCount());
        }

        if (request.getNotes() != null) {
            progress.setNotes(request.getNotes());
        }

        DailyProgress saved = dailyProgressRepository.save(progress);
        return new DailyProgressDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyProgressDto getTodayProgress(Long userId) {
        return getProgressByDate(userId, LocalDate.now());
    }

    @Override
    @Transactional(readOnly = true)
    public DailyProgressDto getProgressByDate(Long userId, LocalDate date) {
        LocalDate targetDate = date != null ? date : LocalDate.now();
        return dailyProgressRepository.findByUserIdAndLogDate(userId, targetDate)
                .map(DailyProgressDto::new)
                .orElseGet(() -> {
                    DailyProgressDto empty = new DailyProgressDto();
                    empty.setLogDate(targetDate);
                    return empty;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyProgressDto> getProgressHistory(Long userId, int days) {
        int duration = days > 0 ? days : 30;
        LocalDate startDate = LocalDate.now().minusDays(duration - 1);
        List<DailyProgress> records = dailyProgressRepository.findByUserIdAndLogDateBetweenOrderByLogDateAsc(
                userId, startDate, LocalDate.now()
        );
        return records.stream().map(DailyProgressDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardOverviewDto getDashboardOverview(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfile profile = user.getProfile();
        DashboardOverviewDto dto = new DashboardOverviewDto();
        dto.setFullName(user.getFullName());

        if (profile != null) {
            dto.setCurrentWeightKg(profile.getWeightKg());
            dto.setTargetWeightKg(profile.getTargetWeightKg());
            dto.setBmi(profile.getBmi());
            dto.setBmiCategory(profile.getBmiCategory());
            dto.setFitnessGoalDisplay(profile.getFitnessGoal() != null ? profile.getFitnessGoal().getDisplayName() : "");
            dto.setDailyCalorieTarget(profile.getDailyCalorieTarget());
            dto.setProteinTargetG(profile.getProteinTargetG());
            dto.setWaterTargetMl(profile.getWaterTargetMl());
        }

        LocalDate today = LocalDate.now();

        // 1. Today's Workout
        try {
            TodayWorkoutDto todayWorkout = workoutService.getTodayWorkout(userId);
            if (todayWorkout != null) {
                dto.setTodayWorkoutTitle(todayWorkout.getTitle());
                dto.setWorkoutCompleted(todayWorkout.getStatus() == com.example.fitnessapp.enums.WorkoutStatus.COMPLETED);
                dto.setWorkoutCompletionPercentage(todayWorkout.getCompletionPercentage());
                dto.setCaloriesBurned(todayWorkout.getTotalCaloriesBurned() != null ? todayWorkout.getTotalCaloriesBurned() : 0.0);
            }
        } catch (Exception e) {
            // Keep default zeros if no workout plan exists yet
        }

        // 2. Today's Diet / Calories Consumed
        try {
            DailyDietSummaryDto dietSummary = dietService.getDailyDietSummary(userId, today);
            if (dietSummary != null) {
                double consumed = dietSummary.getCaloriesConsumed() != null ? dietSummary.getCaloriesConsumed() : 0.0;
                dto.setCaloriesConsumed(consumed);
                if (dto.getDailyCalorieTarget() != null && dto.getDailyCalorieTarget() > 0) {
                    double remaining = dto.getDailyCalorieTarget() - consumed;
                    dto.setRemainingCalories(Math.max(0.0, Math.round(remaining * 10.0) / 10.0));
                }
            }
        } catch (Exception e) {
            // Keep default zeros
        }

        // 3. Today's Hydration
        try {
            WaterSummaryDto waterSummary = waterService.getWaterSummary(userId, today);
            if (waterSummary != null) {
                dto.setWaterConsumedMl(waterSummary.getConsumedMl());
                dto.setWaterPercentage(waterSummary.getProgressPercentage());
            }
        } catch (Exception e) {
            // Keep default zeros
        }

        // 4. Daily Progress record (Sleep & Steps)
        dailyProgressRepository.findByUserIdAndLogDate(userId, today).ifPresent(p -> {
            dto.setSleepHours(p.getSleepHours() != null ? p.getSleepHours() : 0.0);
            dto.setStepsCount(p.getStepsCount() != null ? p.getStepsCount() : 0);
            if (p.isWorkoutCompleted()) {
                dto.setWorkoutCompleted(true);
            }
        });

        // 5. Workout Streak
        try {
            dto.setCurrentStreakDays(workoutService.getWorkoutStreak(userId));
        } catch (Exception e) {
            dto.setCurrentStreakDays(0);
        }

        // 6. Weekly Metrics (Past 7 Days)
        LocalDate weekStart = today.minusDays(6);
        List<DailyProgress> weekList = dailyProgressRepository.findByUserIdAndLogDateBetweenOrderByLogDateAsc(
                userId, weekStart, today
        );

        int weeklyWorkouts = (int) weekList.stream().filter(DailyProgress::isWorkoutCompleted).count();
        double weeklyCaloriesBurned = weekList.stream().mapToDouble(DailyProgress::getCaloriesBurned).sum();
        double avgCaloriesConsumed = weekList.isEmpty() ? 0.0 :
                weekList.stream().mapToDouble(DailyProgress::getCaloriesConsumed).average().orElse(0.0);

        dto.setWeeklyWorkoutsCompleted(weeklyWorkouts);
        dto.setWeeklyCaloriesBurned(Math.round(weeklyCaloriesBurned * 10.0) / 10.0);
        dto.setWeeklyAvgCaloriesConsumed(Math.round(avgCaloriesConsumed * 10.0) / 10.0);

        // Calculate weight change across weekly/historical entries
        List<DailyProgress> weightRecords = weekList.stream()
                .filter(p -> p.getWeightKg() != null && p.getWeightKg() > 0)
                .sorted(Comparator.comparing(DailyProgress::getLogDate))
                .toList();

        if (weightRecords.size() >= 2) {
            double startWeight = weightRecords.get(0).getWeightKg();
            double endWeight = weightRecords.get(weightRecords.size() - 1).getWeightKg();
            dto.setWeightChangeKg(Math.round((endWeight - startWeight) * 10.0) / 10.0);
        } else {
            dto.setWeightChangeKg(0.0);
        }

        // 7. Recent Progress list (for charts)
        dto.setRecentProgress(weekList.stream().map(DailyProgressDto::new).collect(Collectors.toList()));

        return dto;
    }
}

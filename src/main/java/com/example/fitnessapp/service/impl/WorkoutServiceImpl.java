package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.ExerciseStatusUpdateRequest;
import com.example.fitnessapp.dto.response.*;
import com.example.fitnessapp.entity.*;
import com.example.fitnessapp.enums.*;
import com.example.fitnessapp.exception.BadRequestException;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.*;
import com.example.fitnessapp.service.WorkoutService;
import com.example.fitnessapp.util.FitnessCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutExerciseLogRepository workoutExerciseLogRepository;
    private final DailyProgressRepository dailyProgressRepository;
    private final NotificationRepository notificationRepository;

    public WorkoutServiceImpl(
            UserRepository userRepository,
            ExerciseRepository exerciseRepository,
            WorkoutPlanRepository workoutPlanRepository,
            WorkoutLogRepository workoutLogRepository,
            WorkoutExerciseLogRepository workoutExerciseLogRepository,
            DailyProgressRepository dailyProgressRepository,
            NotificationRepository notificationRepository
    ) {
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.workoutLogRepository = workoutLogRepository;
        this.workoutExerciseLogRepository = workoutExerciseLogRepository;
        this.dailyProgressRepository = dailyProgressRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public WorkoutPlanDto generatePersonalizedWorkoutPlan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfile profile = user.getProfile();
        if (profile == null) {
            throw new BadRequestException("Please complete your fitness profile before generating a workout plan.");
        }

        // Deactivate previous active plans
        workoutPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .ifPresent(p -> {
                    p.setActive(false);
                    workoutPlanRepository.save(p);
                });

        FitnessGoal goal = profile.getFitnessGoal() != null ? profile.getFitnessGoal() : FitnessGoal.GENERAL_FITNESS;
        FitnessExperience exp = profile.getFitnessExperience() != null ? profile.getFitnessExperience() : FitnessExperience.BEGINNER;
        double weight = profile.getWeightKg() != null ? profile.getWeightKg() : 70.0;

        WorkoutPlan plan = new WorkoutPlan();
        plan.setUser(user);
        plan.setGoal(goal);
        plan.setExperienceLevel(exp);
        plan.setActive(true);

        String planTitle = switch (goal) {
            case MUSCLE_GAIN -> exp == FitnessExperience.BEGINNER ? "Beginner Muscle Foundation Split" : "4-Day Hypertrophy & Overload Split";
            case FAT_LOSS -> "5-Day High Intensity & Resistance Burner";
            case WEIGHT_LOSS -> "Calorie-Burn & Functional Conditioning Routine";
            case MAINTENANCE -> "Balanced Strength & Cardiovascular Maintenance";
            case GENERAL_FITNESS -> "Total Body Mobility, Endurance & Strength";
        };
        plan.setTitle(planTitle);
        plan.setDescription("Tailored specifically for " + goal.getDisplayName() + " at " + exp.name().toLowerCase() + " level.");

        // Retrieve exercises from database
        Map<String, Exercise> exMap = exerciseRepository.findAll().stream()
                .collect(Collectors.toMap(Exercise::getName, e -> e, (e1, e2) -> e1));

        buildWorkoutDays(plan, goal, exp, weight, exMap);

        WorkoutPlan savedPlan = workoutPlanRepository.save(plan);

        // Send confirmation notification
        Notification notification = new Notification(
                user,
                "Personalized Workout Plan Ready! 💪",
                "Your " + planTitle + " is active. Open today's workout to start crushing your goals!",
                NotificationType.GENERAL
        );
        notificationRepository.save(notification);

        return new WorkoutPlanDto(savedPlan);
    }

    private void buildWorkoutDays(WorkoutPlan plan, FitnessGoal goal, FitnessExperience exp, double weight, Map<String, Exercise> exMap) {
        if (exp == FitnessExperience.BEGINNER) {
            // Day 1: Full Body Strength (Mon)
            WorkoutDay day1 = new WorkoutDay(1, "Full Body Foundation A", "Compound Strength", false);
            addExercise(day1, exMap.get("Barbell Back Squat"), 3, 10, 90, 0, 10, weight);
            addExercise(day1, exMap.get("Barbell Bench Press"), 3, 10, 90, 1, 10, weight);
            addExercise(day1, exMap.get("Lat Pulldown"), 3, 12, 60, 2, 8, weight);
            addExercise(day1, exMap.get("Overhead Shoulder Press"), 3, 10, 75, 3, 8, weight);
            addExercise(day1, exMap.get("Plank Hold"), 3, 45, 60, 4, 5, weight);
            plan.addWorkoutDay(day1);

            // Day 2: Rest & Recovery (Tue)
            plan.addWorkoutDay(new WorkoutDay(2, "Rest & Light Mobility", "Active Recovery", true));

            // Day 3: Full Body Strength B (Wed)
            WorkoutDay day3 = new WorkoutDay(3, "Full Body Foundation B", "Compound Strength", false);
            addExercise(day3, exMap.get("Leg Press"), 3, 12, 75, 0, 10, weight);
            addExercise(day3, exMap.get("Bent-Over Barbell Row"), 3, 10, 75, 1, 10, weight);
            addExercise(day3, exMap.get("Push-Ups"), 3, 12, 60, 2, 6, weight);
            addExercise(day3, exMap.get("Dumbbell Lateral Raise"), 3, 12, 60, 3, 6, weight);
            addExercise(day3, exMap.get("Barbell Biceps Curl"), 3, 12, 60, 4, 6, weight);
            plan.addWorkoutDay(day3);

            // Day 4: Rest Day (Thu)
            plan.addWorkoutDay(new WorkoutDay(4, "Rest Day", "Full Recovery", true));

            // Day 5: Full Body & Core (Fri)
            WorkoutDay day5 = new WorkoutDay(5, "Full Body & Conditioning", "Endurance & Core", false);
            addExercise(day5, exMap.get("Romanian Deadlift"), 3, 10, 90, 0, 10, weight);
            addExercise(day5, exMap.get("Incline Dumbbell Press"), 3, 10, 75, 1, 8, weight);
            addExercise(day5, exMap.get("Triceps Rope Pushdown"), 3, 12, 60, 2, 6, weight);
            addExercise(day5, exMap.get("Treadmill Incline Jogging"), 1, 20, 0, 3, 20, weight);
            plan.addWorkoutDay(day5);

            // Day 6 & 7: Weekend Rest
            plan.addWorkoutDay(new WorkoutDay(6, "Weekend Active Walk", "Light Cardio / Stretch", true));
            plan.addWorkoutDay(new WorkoutDay(7, "Rest & Nutrition Reset", "Full Rest", true));

        } else if (goal == FitnessGoal.MUSCLE_GAIN) {
            // 4-5 Day Hypertrophy Split
            // Day 1: Chest & Triceps
            WorkoutDay day1 = new WorkoutDay(1, "Chest & Triceps Hypertrophy", "Upper Push", false);
            addExercise(day1, exMap.get("Barbell Bench Press"), 4, 8, 90, 0, 12, weight);
            addExercise(day1, exMap.get("Incline Dumbbell Press"), 3, 10, 75, 1, 10, weight);
            addExercise(day1, exMap.get("Cable Chest Fly"), 3, 12, 60, 2, 8, weight);
            addExercise(day1, exMap.get("Triceps Rope Pushdown"), 3, 12, 60, 3, 6, weight);
            addExercise(day1, exMap.get("Overhead Dumbbell Triceps Extension"), 3, 10, 60, 4, 6, weight);
            plan.addWorkoutDay(day1);

            // Day 2: Back & Biceps
            WorkoutDay day2 = new WorkoutDay(2, "Back & Biceps Hypertrophy", "Upper Pull", false);
            addExercise(day2, exMap.get("Barbell Deadlift"), 4, 6, 120, 0, 14, weight);
            addExercise(day2, exMap.get("Pull-Ups"), 3, 8, 90, 1, 8, weight);
            addExercise(day2, exMap.get("Bent-Over Barbell Row"), 3, 10, 75, 2, 8, weight);
            addExercise(day2, exMap.get("Barbell Biceps Curl"), 3, 10, 60, 3, 6, weight);
            addExercise(day2, exMap.get("Dumbbell Hammer Curl"), 3, 12, 60, 4, 6, weight);
            plan.addWorkoutDay(day2);

            // Day 3: Mid-week Rest
            plan.addWorkoutDay(new WorkoutDay(3, "Rest & Protein Replenishment", "Muscle Growth", true));

            // Day 4: Quads & Calves
            WorkoutDay day4 = new WorkoutDay(4, "Quad Dominance & Core", "Lower Body", false);
            addExercise(day4, exMap.get("Barbell Back Squat"), 4, 8, 90, 0, 14, weight);
            addExercise(day4, exMap.get("Leg Press"), 3, 12, 75, 1, 10, weight);
            addExercise(day4, exMap.get("Walking Dumbbell Lunges"), 3, 12, 60, 2, 8, weight);
            addExercise(day4, exMap.get("Standing Calf Raises"), 4, 15, 45, 3, 6, weight);
            addExercise(day4, exMap.get("Hanging Leg Raise"), 3, 15, 60, 4, 6, weight);
            plan.addWorkoutDay(day4);

            // Day 5: Shoulders & Hamstrings
            WorkoutDay day5 = new WorkoutDay(5, "Shoulders & Posterior Chain", "Delts & Hamstrings", false);
            addExercise(day5, exMap.get("Overhead Shoulder Press"), 4, 8, 90, 0, 10, weight);
            addExercise(day5, exMap.get("Romanian Deadlift"), 3, 10, 90, 1, 10, weight);
            addExercise(day5, exMap.get("Dumbbell Lateral Raise"), 4, 12, 45, 2, 6, weight);
            addExercise(day5, exMap.get("Face Pulls"), 3, 15, 45, 3, 6, weight);
            addExercise(day5, exMap.get("Plank Hold"), 3, 60, 60, 4, 5, weight);
            plan.addWorkoutDay(day5);

            // Day 6 & 7: Rest
            plan.addWorkoutDay(new WorkoutDay(6, "Active Recovery & Mobility", "Flexibility", true));
            plan.addWorkoutDay(new WorkoutDay(7, "Rest Day", "Full Rest", true));

        } else {
            // Fat Loss / General Fitness (High Calorie Burn + Strength)
            WorkoutDay day1 = new WorkoutDay(1, "Upper Body Burn + Cardio", "Chest, Back, Cardio", false);
            addExercise(day1, exMap.get("Barbell Bench Press"), 3, 12, 60, 0, 8, weight);
            addExercise(day1, exMap.get("Lat Pulldown"), 3, 12, 60, 1, 8, weight);
            addExercise(day1, exMap.get("Push-Ups"), 3, 15, 45, 2, 6, weight);
            addExercise(day1, exMap.get("HIIT Sprint Intervals"), 1, 15, 30, 3, 15, weight);
            plan.addWorkoutDay(day1);

            WorkoutDay day2 = new WorkoutDay(2, "Lower Body Metabolic Circuit", "Legs & Core", false);
            addExercise(day2, exMap.get("Barbell Back Squat"), 3, 12, 60, 0, 10, weight);
            addExercise(day2, exMap.get("Walking Dumbbell Lunges"), 3, 14, 45, 1, 8, weight);
            addExercise(day2, exMap.get("Romanian Deadlift"), 3, 12, 60, 2, 8, weight);
            addExercise(day2, exMap.get("Jump Rope"), 3, 60, 45, 3, 10, weight);
            plan.addWorkoutDay(day2);

            plan.addWorkoutDay(new WorkoutDay(3, "Active Recovery Walk", "Low Impact", true));

            WorkoutDay day4 = new WorkoutDay(4, "Full Body High Density", "Shoulders & Core", false);
            addExercise(day4, exMap.get("Overhead Shoulder Press"), 3, 12, 60, 0, 8, weight);
            addExercise(day4, exMap.get("Bent-Over Barbell Row"), 3, 12, 60, 1, 8, weight);
            addExercise(day4, exMap.get("Burpees"), 3, 12, 45, 2, 6, weight);
            addExercise(day4, exMap.get("Cable Woodchoppers"), 3, 15, 45, 3, 6, weight);
            plan.addWorkoutDay(day4);

            WorkoutDay day5 = new WorkoutDay(5, "Cardio & Core Conditioning", "Metabolic Engine", false);
            addExercise(day5, exMap.get("Treadmill Incline Jogging"), 1, 25, 0, 0, 25, weight);
            addExercise(day5, exMap.get("Hanging Leg Raise"), 3, 15, 45, 1, 6, weight);
            addExercise(day5, exMap.get("Plank Hold"), 3, 60, 45, 2, 5, weight);
            plan.addWorkoutDay(day5);

            plan.addWorkoutDay(new WorkoutDay(6, "Outdoor Activity / Sports", "Recreational", true));
            plan.addWorkoutDay(new WorkoutDay(7, "Rest & Reset", "Full Rest", true));
        }
    }

    private void addExercise(WorkoutDay day, Exercise exercise, int sets, int reps, int rest, int order, int durationMin, double weight) {
        if (exercise == null) return;
        double calories = FitnessCalculator.calculateCaloriesBurned(exercise.getMetValue(), weight, durationMin);
        WorkoutExercise we = new WorkoutExercise(exercise, sets, reps, rest, order, durationMin, calories);
        day.addWorkoutExercise(we);
    }

    @Override
    @Transactional
    public WorkoutPlanDto getActiveWorkoutPlan(Long userId) {
        WorkoutPlan plan = workoutPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> {
                    // Auto-generate if not yet existing
                    generatePersonalizedWorkoutPlan(userId);
                    return workoutPlanRepository.findByUserIdAndIsActiveTrue(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "userId", userId));
                });
        return new WorkoutPlanDto(plan);
    }

    @Override
    @Transactional
    public TodayWorkoutDto getTodayWorkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Get or generate active plan
        WorkoutPlan plan = workoutPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> {
                    generatePersonalizedWorkoutPlan(userId);
                    return workoutPlanRepository.findByUserIdAndIsActiveTrue(userId).orElse(null);
                });

        if (plan == null) {
            throw new BadRequestException("Could not create or find an active workout plan for user.");
        }

        LocalDate today = LocalDate.now();
        int dayOfWeekInt = today.getDayOfWeek().getValue(); // 1 = Mon ... 7 = Sun
        String dayName = today.getDayOfWeek().name();

        WorkoutDay scheduledDay = plan.getWorkoutDays().stream()
                .filter(d -> d.getDayOfWeek().equals(dayOfWeekInt))
                .findFirst()
                .orElse(null);

        // Check if workout log exists for today
        WorkoutLog log = workoutLogRepository.findByUserIdAndLogDate(userId, today)
                .orElse(null);

        if (log == null) {
            log = new WorkoutLog();
            log.setUser(user);
            log.setLogDate(today);

            if (scheduledDay != null) {
                log.setWorkoutDay(scheduledDay);
                log.setWorkoutTitle(scheduledDay.getTitle());

                if (!scheduledDay.isRestDay()) {
                    for (WorkoutExercise we : scheduledDay.getWorkoutExercises()) {
                        WorkoutExerciseLog el = new WorkoutExerciseLog(
                                we.getExercise(),
                                we.getSets(),
                                we.getReps(),
                                we.getEstimatedCalories()
                        );
                        log.addExerciseLog(el);
                    }
                    log.setStatus(WorkoutStatus.NOT_STARTED);
                } else {
                    log.setStatus(WorkoutStatus.COMPLETED); // Rest days automatically marked
                }
            } else {
                log.setWorkoutTitle("Active Rest / Custom Session");
                log.setStatus(WorkoutStatus.COMPLETED);
            }

            log = workoutLogRepository.save(log);
        }

        boolean isRest = scheduledDay != null && scheduledDay.isRestDay();
        String focus = scheduledDay != null ? scheduledDay.getFocusArea() : "General";
        return new TodayWorkoutDto(log, dayOfWeekInt, dayName, focus, isRest);
    }

    @Override
    @Transactional
    public WorkoutExerciseLogDto updateExerciseStatus(Long userId, Long exerciseLogId, ExerciseStatusUpdateRequest request) {
        WorkoutExerciseLog exLog = workoutExerciseLogRepository.findById(exerciseLogId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutExerciseLog", "id", exerciseLogId));

        WorkoutLog workoutLog = exLog.getWorkoutLog();
        if (!workoutLog.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized access to workout log.");
        }

        exLog.setStatus(request.getStatus());
        if (request.getSetsCompleted() != null) exLog.setSetsCompleted(request.getSetsCompleted());
        if (request.getRepsCompleted() != null) exLog.setRepsCompleted(request.getRepsCompleted());
        if (request.getWeightUsedKg() != null) exLog.setWeightUsedKg(request.getWeightUsedKg());

        // Update overall session status to IN_PROGRESS if started
        if (workoutLog.getStatus() == WorkoutStatus.NOT_STARTED && request.getStatus() != WorkoutStatus.NOT_STARTED) {
            workoutLog.setStatus(WorkoutStatus.IN_PROGRESS);
            workoutLogRepository.save(workoutLog);
        }

        WorkoutExerciseLog saved = workoutExerciseLogRepository.save(exLog);
        return new WorkoutExerciseLogDto(saved);
    }

    @Override
    @Transactional
    public TodayWorkoutDto completeTodayWorkout(Long userId) {
        LocalDate today = LocalDate.now();
        WorkoutLog log = workoutLogRepository.findByUserIdAndLogDate(userId, today)
                .orElseThrow(() -> new BadRequestException("No active workout session found for today."));

        double totalCalories = 0.0;
        int totalMinutes = 0;

        for (WorkoutExerciseLog el : log.getExerciseLogs()) {
            el.setStatus(WorkoutStatus.COMPLETED);
            if (el.getSetsCompleted() == null || el.getSetsCompleted() == 0) {
                el.setSetsCompleted(el.getSetsPlanned());
            }
            if (el.getRepsCompleted() == null || el.getRepsCompleted() == 0) {
                el.setRepsCompleted(el.getRepsPlanned());
            }
            totalCalories += el.getCaloriesBurned() != null ? el.getCaloriesBurned() : 40.0;
            totalMinutes += 10;
        }

        log.setStatus(WorkoutStatus.COMPLETED);
        log.setCompletedAt(LocalDateTime.now());
        log.setTotalCaloriesBurned(Math.round(totalCalories * 10.0) / 10.0);
        log.setTotalDurationMin(Math.max(totalMinutes, 45));

        WorkoutLog savedLog = workoutLogRepository.save(log);

        // Update DailyProgress
        DailyProgress progress = dailyProgressRepository.findByUserIdAndLogDate(userId, today)
                .orElseGet(() -> new DailyProgress(log.getUser(), today));
        progress.setWorkoutCompleted(true);
        progress.setCaloriesBurned(savedLog.getTotalCaloriesBurned());
        dailyProgressRepository.save(progress);

        // Calculate Streak
        long streak = getWorkoutStreak(userId);

        // Send celebration notification
        Notification celebration = new Notification(
                log.getUser(),
                "Workout Completed! 🔥 Streak: " + streak + " Days!",
                "Awesome effort! You burned approximately " + savedLog.getTotalCaloriesBurned() + " estimated kcal today.",
                NotificationType.CONGRATULATIONS
        );
        notificationRepository.save(celebration);

        int dayOfWeekInt = today.getDayOfWeek().getValue();
        return new TodayWorkoutDto(savedLog, dayOfWeekInt, today.getDayOfWeek().name(), "Completed Session", false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutLogSummaryDto> getWorkoutHistory(Long userId, String filter, LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        List<WorkoutLog> logs;

        if ("today".equalsIgnoreCase(filter)) {
            logs = workoutLogRepository.findByUserIdAndLogDateBetweenOrderByLogDateDesc(userId, now, now);
        } else if ("week".equalsIgnoreCase(filter)) {
            LocalDate startOfWeek = now.minusDays(7);
            logs = workoutLogRepository.findByUserIdAndLogDateBetweenOrderByLogDateDesc(userId, startOfWeek, now);
        } else if ("month".equalsIgnoreCase(filter)) {
            LocalDate startOfMonth = now.minusDays(30);
            logs = workoutLogRepository.findByUserIdAndLogDateBetweenOrderByLogDateDesc(userId, startOfMonth, now);
        } else if (startDate != null && endDate != null) {
            logs = workoutLogRepository.findByUserIdAndLogDateBetweenOrderByLogDateDesc(userId, startDate, endDate);
        } else {
            logs = workoutLogRepository.findByUserIdOrderByLogDateDesc(userId);
        }

        return logs.stream().map(WorkoutLogSummaryDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getWorkoutStreak(Long userId) {
        // Calculate consecutive completed workout days counting back from today or yesterday
        LocalDate checkDate = LocalDate.now();
        long streak = 0;

        Optional<WorkoutLog> todayLog = workoutLogRepository.findByUserIdAndLogDate(userId, checkDate);
        if (todayLog.isPresent() && todayLog.get().getStatus() == WorkoutStatus.COMPLETED) {
            streak++;
            checkDate = checkDate.minusDays(1);
        } else {
            checkDate = checkDate.minusDays(1);
        }

        while (true) {
            Optional<WorkoutLog> log = workoutLogRepository.findByUserIdAndLogDate(userId, checkDate);
            if (log.isPresent() && log.get().getStatus() == WorkoutStatus.COMPLETED) {
                streak++;
                checkDate = checkDate.minusDays(1);
            } else {
                break;
            }
        }

        return streak;
    }
}

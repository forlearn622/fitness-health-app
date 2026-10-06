package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.FoodLogRequest;
import com.example.fitnessapp.dto.response.*;
import com.example.fitnessapp.entity.*;
import com.example.fitnessapp.enums.MealType;
import com.example.fitnessapp.enums.NotificationType;
import com.example.fitnessapp.exception.BadRequestException;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.*;
import com.example.fitnessapp.service.DietService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DietServiceImpl implements DietService {

    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final DietPlanRepository dietPlanRepository;
    private final FoodLogRepository foodLogRepository;
    private final DailyProgressRepository dailyProgressRepository;
    private final NotificationRepository notificationRepository;

    public DietServiceImpl(
            UserRepository userRepository,
            FoodRepository foodRepository,
            DietPlanRepository dietPlanRepository,
            FoodLogRepository foodLogRepository,
            DailyProgressRepository dailyProgressRepository,
            NotificationRepository notificationRepository
    ) {
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
        this.dietPlanRepository = dietPlanRepository;
        this.foodLogRepository = foodLogRepository;
        this.dailyProgressRepository = dailyProgressRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public DietPlanDto generatePersonalizedDietPlan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfile profile = user.getProfile();
        if (profile == null) {
            throw new BadRequestException("Please complete your profile before generating a diet plan.");
        }

        // Archive prior active diet plans
        dietPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .ifPresent(p -> {
                    p.setActive(false);
                    dietPlanRepository.save(p);
                });

        int calorieTarget = profile.getDailyCalorieTarget() != null ? profile.getDailyCalorieTarget() : 2200;
        int proteinTarget = profile.getProteinTargetG() != null ? profile.getProteinTargetG() : 120;
        int carbsTarget = profile.getCarbsTargetG() != null ? profile.getCarbsTargetG() : 250;
        int fatTarget = profile.getFatTargetG() != null ? profile.getFatTargetG() : 60;

        DietPlan dietPlan = new DietPlan();
        dietPlan.setUser(user);
        dietPlan.setTitle("7-Day Personalized Indian Nutrition & Macro Regimen");
        dietPlan.setCalorieTarget(calorieTarget);
        dietPlan.setProteinTargetG(proteinTarget);
        dietPlan.setCarbsTargetG(carbsTarget);
        dietPlan.setFatTargetG(fatTarget);
        dietPlan.setActive(true);

        Map<String, Food> fMap = foodRepository.findAll().stream()
                .collect(Collectors.toMap(Food::getName, f -> f, (f1, f2) -> f1));

        // Generate Monday to Sunday
        for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
            DietDay dietDay = new DietDay(dayOfWeek);
            buildMealsForDay(dietDay, dayOfWeek, fMap, calorieTarget);
            dietPlan.addDietDay(dietDay);
        }

        DietPlan savedPlan = dietPlanRepository.save(dietPlan);

        // Send Notification
        Notification notification = new Notification(
                user,
                "Personalized 7-Day Indian Diet Plan Ready! 🥗",
                "Your daily target is " + calorieTarget + " kcal with " + proteinTarget + "g protein. Explore your weekly meal schedule!",
                NotificationType.GENERAL
        );
        notificationRepository.save(notification);

        return new DietPlanDto(savedPlan);
    }

    private void buildMealsForDay(DietDay day, int dayOfWeek, Map<String, Food> fMap, int calorieTarget) {
        // Scaling factor based on standard 2200 kcal baseline
        double scale = calorieTarget / 2200.0;

        // 1. Breakfast (~25%)
        Meal breakfast = new Meal(MealType.BREAKFAST);
        if (dayOfWeek % 2 == 1) {
            addMealFood(breakfast, fMap.get("Oats Porridge (with water)"), 150.0 * scale, "g", fMap);
            addMealFood(breakfast, fMap.get("Cow Milk (Toned 3% Fat)"), 200.0, "ml", fMap);
            addMealFood(breakfast, fMap.get("Banana"), 1.0, "medium", fMap);
            addMealFood(breakfast, fMap.get("Almonds"), 20.0, "g", fMap);
        } else {
            addMealFood(breakfast, fMap.get("Poha (Flattened Rice Dish)"), 150.0 * scale, "plate", fMap);
            addMealFood(breakfast, fMap.get("Whole Boiled Egg"), 2.0, "eggs", fMap);
            addMealFood(breakfast, fMap.get("Cow Milk (Toned 3% Fat)"), 150.0, "ml", fMap);
        }
        day.addMeal(breakfast);

        // 2. Morning Snack (~10%)
        Meal morningSnack = new Meal(MealType.MORNING_SNACK);
        if (dayOfWeek % 2 == 1) {
            addMealFood(morningSnack, fMap.get("Moong Sprouts (Boiled/Raw)"), 100.0 * scale, "g", fMap);
            addMealFood(morningSnack, fMap.get("Apple"), 1.0, "medium", fMap);
        } else {
            addMealFood(morningSnack, fMap.get("Greek Yogurt (Plain)"), 150.0, "cup", fMap);
            addMealFood(morningSnack, fMap.get("Walnuts"), 20.0, "g", fMap);
        }
        day.addMeal(morningSnack);

        // 3. Lunch (~35%)
        Meal lunch = new Meal(MealType.LUNCH);
        addMealFood(lunch, fMap.get("Whole Wheat Roti (Chapati)"), Math.max(1.0, Math.round(2.0 * scale)), "roti", fMap);
        addMealFood(lunch, fMap.get("Cooked Brown Rice"), 100.0 * scale, "g", fMap);
        addMealFood(lunch, fMap.get("Dal Tadka (Cooked Toor/Yellow Dal)"), 150.0, "bowl", fMap);
        if (dayOfWeek == 2 || dayOfWeek == 5) {
            addMealFood(lunch, fMap.get("Chole / Chickpea Curry"), 150.0, "bowl", fMap);
        } else if (dayOfWeek == 4 || dayOfWeek == 7) {
            addMealFood(lunch, fMap.get("Paneer (Cottage Cheese)"), 80.0 * scale, "g", fMap);
        } else {
            addMealFood(lunch, fMap.get("Chicken Breast (Grilled/Boiled)"), 100.0 * scale, "g", fMap);
        }
        addMealFood(lunch, fMap.get("Curd / Dahi (Plain)"), 100.0, "bowl", fMap);
        addMealFood(lunch, fMap.get("Cucumber & Tomato Salad"), 100.0, "plate", fMap);
        day.addMeal(lunch);

        // 4. Evening Snack (~10%)
        Meal eveningSnack = new Meal(MealType.EVENING_SNACK);
        addMealFood(eveningSnack, fMap.get("Moong Sprouts (Boiled/Raw)"), 80.0, "g", fMap);
        addMealFood(eveningSnack, fMap.get("Almonds"), 15.0, "g", fMap);
        day.addMeal(eveningSnack);

        // 5. Dinner (~20%)
        Meal dinner = new Meal(MealType.DINNER);
        addMealFood(dinner, fMap.get("Whole Wheat Roti (Chapati)"), Math.max(1.0, Math.round(2.0 * scale)), "roti", fMap);
        addMealFood(dinner, fMap.get("Mixed Vegetable Sabzi"), 150.0, "bowl", fMap);
        addMealFood(dinner, fMap.get("Dal Tadka (Cooked Toor/Yellow Dal)"), 150.0, "bowl", fMap);
        addMealFood(dinner, fMap.get("Low Fat Paneer"), 75.0 * scale, "g", fMap);
        day.addMeal(dinner);
    }

    private void addMealFood(Meal meal, Food food, double quantity, String unit, Map<String, Food> fMap) {
        if (food == null) return;
        double ratio = quantity / (food.getServingSize() != null && food.getServingSize() > 0 ? food.getServingSize() : 100.0);
        double cal = Math.round(food.getCalories() * ratio * 10.0) / 10.0;
        double pro = Math.round(food.getProtein() * ratio * 10.0) / 10.0;
        double carb = Math.round(food.getCarbs() * ratio * 10.0) / 10.0;
        double fat = Math.round(food.getFat() * ratio * 10.0) / 10.0;

        MealFood mf = new MealFood(food, quantity, unit, cal, pro, carb, fat);
        meal.addMealFood(mf);

        meal.setTotalCalories(meal.getTotalCalories() + cal);
        meal.setTotalProtein(meal.getTotalProtein() + pro);
        meal.setTotalCarbs(meal.getTotalCarbs() + carb);
        meal.setTotalFat(meal.getTotalFat() + fat);
    }

    @Override
    @Transactional
    public DietPlanDto getActiveDietPlan(Long userId) {
        DietPlan plan = dietPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> {
                    generatePersonalizedDietPlan(userId);
                    return dietPlanRepository.findByUserIdAndIsActiveTrue(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("DietPlan", "userId", userId));
                });
        return new DietPlanDto(plan);
    }

    @Override
    @Transactional
    public DietDayDto getTodayDietPlan(Long userId) {
        DietPlan plan = dietPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> {
                    generatePersonalizedDietPlan(userId);
                    return dietPlanRepository.findByUserIdAndIsActiveTrue(userId).orElse(null);
                });

        if (plan == null) {
            throw new BadRequestException("No active diet plan found.");
        }

        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        DietDay todayDietDay = plan.getDietDays().stream()
                .filter(d -> d.getDayOfWeek().equals(dayOfWeek))
                .findFirst()
                .orElse(plan.getDietDays().isEmpty() ? null : plan.getDietDays().get(0));

        return new DietDayDto(todayDietDay);
    }

    @Override
    @Transactional
    public FoodLogDto logFood(Long userId, FoodLogRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        LocalDate date = request.getLogDate() != null ? request.getLogDate() : LocalDate.now();

        FoodLog log = new FoodLog();
        log.setUser(user);
        log.setFoodName(request.getFoodName().trim());
        log.setMealType(request.getMealType());
        log.setLogDate(date);
        log.setQuantity(request.getQuantity());
        log.setUnit(request.getUnit() != null ? request.getUnit() : "g");
        log.setCalories(request.getCalories());
        log.setProtein(request.getProtein());
        log.setCarbs(request.getCarbs());
        log.setFat(request.getFat());

        if (request.getFoodId() != null) {
            foodRepository.findById(request.getFoodId()).ifPresent(log::setFood);
        }

        FoodLog saved = foodLogRepository.save(log);

        // Update DailyProgress total calories consumed
        Double totalConsumed = foodLogRepository.sumCaloriesByUserIdAndDate(userId, date);
        DailyProgress progress = dailyProgressRepository.findByUserIdAndLogDate(userId, date)
                .orElseGet(() -> new DailyProgress(user, date));
        progress.setCaloriesConsumed(totalConsumed != null ? totalConsumed : 0.0);
        dailyProgressRepository.save(progress);

        return new FoodLogDto(saved);
    }

    @Override
    @Transactional
    public void deleteFoodLog(Long userId, Long foodLogId) {
        FoodLog log = foodLogRepository.findById(foodLogId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodLog", "id", foodLogId));

        if (!log.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized access to delete food log.");
        }

        LocalDate date = log.getLogDate();
        foodLogRepository.delete(log);

        // Recalculate daily progress
        Double totalConsumed = foodLogRepository.sumCaloriesByUserIdAndDate(userId, date);
        dailyProgressRepository.findByUserIdAndLogDate(userId, date).ifPresent(p -> {
            p.setCaloriesConsumed(totalConsumed != null ? totalConsumed : 0.0);
            dailyProgressRepository.save(p);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public DailyDietSummaryDto getDailyDietSummary(Long userId, LocalDate date) {
        LocalDate targetDate = date != null ? date : LocalDate.now();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfile profile = user.getProfile();
        int calTarget = profile != null && profile.getDailyCalorieTarget() != null ? profile.getDailyCalorieTarget() : 2000;
        int proTarget = profile != null && profile.getProteinTargetG() != null ? profile.getProteinTargetG() : 120;
        int carbsTarget = profile != null && profile.getCarbsTargetG() != null ? profile.getCarbsTargetG() : 250;
        int fatTarget = profile != null && profile.getFatTargetG() != null ? profile.getFatTargetG() : 55;

        List<FoodLog> logs = foodLogRepository.findByUserIdAndLogDateOrderByLoggedAtAsc(userId, targetDate);

        double totalCal = logs.stream().mapToDouble(FoodLog::getCalories).sum();
        double totalPro = logs.stream().mapToDouble(FoodLog::getProtein).sum();
        double totalCarb = logs.stream().mapToDouble(FoodLog::getCarbs).sum();
        double totalFat = logs.stream().mapToDouble(FoodLog::getFat).sum();

        DailyDietSummaryDto summary = new DailyDietSummaryDto();
        summary.setLogDate(targetDate);
        summary.setCalorieTarget(calTarget);
        summary.setCaloriesConsumed(Math.round(totalCal * 10.0) / 10.0);
        summary.setRemainingCalories(Math.max(0.0, Math.round((calTarget - totalCal) * 10.0) / 10.0));

        summary.setProteinTargetG(proTarget);
        summary.setProteinConsumed(Math.round(totalPro * 10.0) / 10.0);
        summary.setRemainingProtein(Math.max(0.0, Math.round((proTarget - totalPro) * 10.0) / 10.0));

        summary.setCarbsTargetG(carbsTarget);
        summary.setCarbsConsumed(Math.round(totalCarb * 10.0) / 10.0);
        summary.setRemainingCarbs(Math.max(0.0, Math.round((carbsTarget - totalCarb) * 10.0) / 10.0));

        summary.setFatTargetG(fatTarget);
        summary.setFatConsumed(Math.round(totalFat * 10.0) / 10.0);
        summary.setRemainingFat(Math.max(0.0, Math.round((fatTarget - totalFat) * 10.0) / 10.0));

        summary.setLoggedFoods(logs.stream().map(FoodLogDto::new).collect(Collectors.toList()));
        return summary;
    }
}

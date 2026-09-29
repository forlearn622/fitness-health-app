package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.ProfileUpdateRequest;
import com.example.fitnessapp.dto.response.UserProfileDto;
import com.example.fitnessapp.dto.response.UserSummaryDto;
import com.example.fitnessapp.entity.DailyProgress;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.UserProfile;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.DailyProgressRepository;
import com.example.fitnessapp.repository.UserProfileRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.service.UserService;
import com.example.fitnessapp.util.FitnessCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final DailyProgressRepository dailyProgressRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            DailyProgressRepository dailyProgressRepository
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.dailyProgressRepository = dailyProgressRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile", "userId", userId));
        return new UserProfileDto(profile);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(Long userId, ProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
        }

        boolean weightChanged = !request.getWeightKg().equals(profile.getWeightKg());

        // Update basic body metrics
        profile.setAge(request.getAge());
        profile.setHeightCm(request.getHeightCm());
        profile.setWeightKg(request.getWeightKg());
        if (request.getTargetWeightKg() != null) {
            profile.setTargetWeightKg(request.getTargetWeightKg());
        }
        profile.setActivityLevel(request.getActivityLevel());
        profile.setFitnessExperience(request.getFitnessExperience());
        profile.setFitnessGoal(request.getFitnessGoal());

        // Recalculate scientific metrics
        double bmi = FitnessCalculator.calculateBmi(request.getHeightCm(), request.getWeightKg());
        String bmiCategory = FitnessCalculator.determineBmiCategory(bmi);
        double bmr = FitnessCalculator.calculateBmr(profile.getGender(), request.getWeightKg(), request.getHeightCm(), request.getAge());
        double tdee = FitnessCalculator.calculateTdee(bmr, request.getActivityLevel());
        int dailyCalorieTarget = FitnessCalculator.calculateDailyCalorieTarget(tdee, request.getFitnessGoal());
        Map<String, Integer> macros = FitnessCalculator.calculateMacros(dailyCalorieTarget, request.getWeightKg(), request.getFitnessGoal());
        int waterTarget = FitnessCalculator.calculateWaterTarget(request.getWeightKg(), request.getActivityLevel());

        profile.setBmi(bmi);
        profile.setBmiCategory(bmiCategory);
        profile.setBmr(bmr);
        profile.setTdee(tdee);
        profile.setDailyCalorieTarget(dailyCalorieTarget);
        profile.setProteinTargetG(macros.get("protein"));
        profile.setCarbsTargetG(macros.get("carbs"));
        profile.setFatTargetG(macros.get("fat"));
        profile.setWaterTargetMl(waterTarget);

        UserProfile savedProfile = userProfileRepository.save(profile);

        // If weight changed, automatically update/create today's DailyProgress record
        if (weightChanged) {
            LocalDate today = LocalDate.now();
            DailyProgress progress = dailyProgressRepository.findByUserIdAndLogDate(userId, today)
                    .orElseGet(() -> new DailyProgress(user, today));
            progress.setWeightKg(request.getWeightKg());
            dailyProgressRepository.save(progress);
        }

        return new UserProfileDto(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummaryDto getUserSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfile profile = user.getProfile();
        UserSummaryDto summary = new UserSummaryDto();
        summary.setUserId(user.getId());
        summary.setFullName(user.getFullName());
        summary.setEmail(user.getEmail());

        if (profile != null) {
            summary.setCurrentWeightKg(profile.getWeightKg());
            summary.setTargetWeightKg(profile.getTargetWeightKg());
            summary.setFitnessGoal(profile.getFitnessGoal() != null ? profile.getFitnessGoal().name() : null);
            summary.setFitnessGoalDisplay(profile.getFitnessGoal() != null ? profile.getFitnessGoal().getDisplayName() : null);
            summary.setBmi(profile.getBmi());
            summary.setBmiCategory(profile.getBmiCategory());
            summary.setBmr(profile.getBmr());
            summary.setTdee(profile.getTdee());
            summary.setDailyCalorieTarget(profile.getDailyCalorieTarget());
            summary.setProteinTargetG(profile.getProteinTargetG());
            summary.setCarbsTargetG(profile.getCarbsTargetG());
            summary.setFatTargetG(profile.getFatTargetG());
            summary.setWaterTargetMl(profile.getWaterTargetMl());
        }

        return summary;
    }
}

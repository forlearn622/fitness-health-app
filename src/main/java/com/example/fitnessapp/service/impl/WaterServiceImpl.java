package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.WaterLogRequest;
import com.example.fitnessapp.dto.response.WaterLogDto;
import com.example.fitnessapp.dto.response.WaterSummaryDto;
import com.example.fitnessapp.entity.DailyProgress;
import com.example.fitnessapp.entity.Notification;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.WaterLog;
import com.example.fitnessapp.enums.NotificationType;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.DailyProgressRepository;
import com.example.fitnessapp.repository.NotificationRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.repository.WaterLogRepository;
import com.example.fitnessapp.service.WaterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaterServiceImpl implements WaterService {

    private final UserRepository userRepository;
    private final WaterLogRepository waterLogRepository;
    private final DailyProgressRepository dailyProgressRepository;
    private final NotificationRepository notificationRepository;

    public WaterServiceImpl(
            UserRepository userRepository,
            WaterLogRepository waterLogRepository,
            DailyProgressRepository dailyProgressRepository,
            NotificationRepository notificationRepository
    ) {
        this.userRepository = userRepository;
        this.waterLogRepository = waterLogRepository;
        this.dailyProgressRepository = dailyProgressRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public WaterSummaryDto logWater(Long userId, WaterLogRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        LocalDate date = request.getLogDate() != null ? request.getLogDate() : LocalDate.now();

        WaterLog waterLog = new WaterLog();
        waterLog.setUser(user);
        waterLog.setAmountMl(request.getAmountMl());
        waterLog.setLogDate(date);
        waterLogRepository.save(waterLog);

        Integer totalConsumed = waterLogRepository.sumAmountByUserIdAndLogDate(userId, date);
        int consumed = totalConsumed != null ? totalConsumed : 0;

        // Update DailyProgress
        DailyProgress progress = dailyProgressRepository.findByUserIdAndLogDate(userId, date)
                .orElseGet(() -> new DailyProgress(user, date));
        progress.setWaterConsumedMl(consumed);
        dailyProgressRepository.save(progress);

        int target = user.getProfile() != null && user.getProfile().getWaterTargetMl() != null
                ? user.getProfile().getWaterTargetMl()
                : 3000;

        // Check if just reached target
        if (consumed >= target && (consumed - request.getAmountMl()) < target) {
            Notification notification = new Notification(
                    user,
                    "Hydration Goal Achieved! 💧",
                    "Great job! You reached your daily hydration target of " + (target / 1000.0) + "L today!",
                    NotificationType.CONGRATULATIONS
            );
            notificationRepository.save(notification);
        }

        return getWaterSummary(userId, date);
    }

    @Override
    @Transactional(readOnly = true)
    public WaterSummaryDto getWaterSummary(Long userId, LocalDate date) {
        LocalDate targetDate = date != null ? date : LocalDate.now();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        int target = user.getProfile() != null && user.getProfile().getWaterTargetMl() != null
                ? user.getProfile().getWaterTargetMl()
                : 3000;

        Integer totalConsumed = waterLogRepository.sumAmountByUserIdAndLogDate(userId, targetDate);
        int consumed = totalConsumed != null ? totalConsumed : 0;

        List<WaterLog> logs = waterLogRepository.findByUserIdAndLogDateOrderByLoggedAtDesc(userId, targetDate);

        WaterSummaryDto summary = new WaterSummaryDto();
        summary.setLogDate(targetDate);
        summary.setTargetMl(target);
        summary.setConsumedMl(consumed);
        summary.setRemainingMl(Math.max(0, target - consumed));
        summary.setProgressPercentage(Math.min(100.0, Math.round(((double) consumed / target) * 1000.0) / 10.0));
        summary.setLogs(logs.stream().map(WaterLogDto::new).collect(Collectors.toList()));

        return summary;
    }
}

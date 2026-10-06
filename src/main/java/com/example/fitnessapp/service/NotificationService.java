package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.response.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getUserNotifications(Long userId, boolean unreadOnly);
    long getUnreadCount(Long userId);
    void markAsRead(Long userId, Long notificationId);
    void markAllAsRead(Long userId);
}

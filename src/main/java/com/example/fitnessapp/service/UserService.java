package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.ProfileUpdateRequest;
import com.example.fitnessapp.dto.response.UserProfileDto;
import com.example.fitnessapp.dto.response.UserSummaryDto;

public interface UserService {
    UserProfileDto getUserProfile(Long userId);
    UserProfileDto updateUserProfile(Long userId, ProfileUpdateRequest request);
    UserSummaryDto getUserSummary(Long userId);
}

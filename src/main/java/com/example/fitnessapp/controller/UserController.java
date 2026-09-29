package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.request.ProfileUpdateRequest;
import com.example.fitnessapp.dto.response.ApiResponse;
import com.example.fitnessapp.dto.response.UserProfileDto;
import com.example.fitnessapp.dto.response.UserSummaryDto;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> getProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserProfileDto profile = userService.getUserProfile(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Fitness profile retrieved", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ProfileUpdateRequest request
    ) {
        UserProfileDto updatedProfile = userService.updateUserProfile(currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Fitness profile and health targets updated successfully!", updatedProfile));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<UserSummaryDto>> getSummary(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserSummaryDto summary = userService.getUserSummary(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("User summary retrieved", summary));
    }
}

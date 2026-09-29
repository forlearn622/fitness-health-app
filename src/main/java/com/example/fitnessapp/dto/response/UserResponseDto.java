package com.example.fitnessapp.dto.response;

import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.enums.Role;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long id;
    private String email;
    private String fullName;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private UserProfileDto profile;

    public UserResponseDto() {}

    public UserResponseDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.fullName = user.getFullName();
            this.role = user.getRole();
            this.isActive = user.isActive();
            this.createdAt = user.getCreatedAt();
            if (user.getProfile() != null) {
                this.profile = new UserProfileDto(user.getProfile());
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UserProfileDto getProfile() { return profile; }
    public void setProfile(UserProfileDto profile) { this.profile = profile; }
}

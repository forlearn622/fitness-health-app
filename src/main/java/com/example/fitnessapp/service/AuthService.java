package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.request.LoginRequest;
import com.example.fitnessapp.dto.request.RegisterRequest;
import com.example.fitnessapp.dto.response.AuthResponse;
import com.example.fitnessapp.dto.response.UserResponseDto;
import com.example.fitnessapp.security.UserPrincipal;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponseDto getCurrentUser(UserPrincipal currentUser);
}

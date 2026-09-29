package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.request.LoginRequest;
import com.example.fitnessapp.dto.request.RegisterRequest;
import com.example.fitnessapp.dto.response.AuthResponse;
import com.example.fitnessapp.dto.response.UserResponseDto;
import com.example.fitnessapp.entity.Notification;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.UserProfile;
import com.example.fitnessapp.enums.NotificationType;
import com.example.fitnessapp.enums.Role;
import com.example.fitnessapp.exception.BadRequestException;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.repository.NotificationRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.security.JwtTokenProvider;
import com.example.fitnessapp.security.UserPrincipal;
import com.example.fitnessapp.service.AuthService;
import com.example.fitnessapp.util.FitnessCalculator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(
            UserRepository userRepository,
            NotificationRepository notificationRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider
    ) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 1. Check if email already in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("An account with email " + request.getEmail() + " is already registered.");
        }

        // 2. Create User entity with hashed password
        User user = new User();
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName().trim());
        user.setRole(Role.ROLE_USER);
        user.setActive(true);

        // 3. Calculate scientific fitness metrics
        double bmi = FitnessCalculator.calculateBmi(request.getHeightCm(), request.getWeightKg());
        String bmiCategory = FitnessCalculator.determineBmiCategory(bmi);
        double bmr = FitnessCalculator.calculateBmr(request.getGender(), request.getWeightKg(), request.getHeightCm(), request.getAge());
        double tdee = FitnessCalculator.calculateTdee(bmr, request.getActivityLevel());
        int dailyCalorieTarget = FitnessCalculator.calculateDailyCalorieTarget(tdee, request.getFitnessGoal());
        Map<String, Integer> macros = FitnessCalculator.calculateMacros(dailyCalorieTarget, request.getWeightKg(), request.getFitnessGoal());
        int waterTarget = FitnessCalculator.calculateWaterTarget(request.getWeightKg(), request.getActivityLevel());

        // 4. Create UserProfile
        UserProfile profile = new UserProfile();
        profile.setAge(request.getAge());
        profile.setGender(request.getGender());
        profile.setHeightCm(request.getHeightCm());
        profile.setWeightKg(request.getWeightKg());
        profile.setTargetWeightKg(request.getTargetWeightKg() != null ? request.getTargetWeightKg() : request.getWeightKg());
        profile.setActivityLevel(request.getActivityLevel());
        profile.setFitnessExperience(request.getFitnessExperience());
        profile.setFitnessGoal(request.getFitnessGoal());

        profile.setBmi(bmi);
        profile.setBmiCategory(bmiCategory);
        profile.setBmr(bmr);
        profile.setTdee(tdee);
        profile.setDailyCalorieTarget(dailyCalorieTarget);
        profile.setProteinTargetG(macros.get("protein"));
        profile.setCarbsTargetG(macros.get("carbs"));
        profile.setFatTargetG(macros.get("fat"));
        profile.setWaterTargetMl(waterTarget);

        user.setProfile(profile);

        // 5. Save User (cascades to profile)
        User savedUser = userRepository.save(user);

        // 6. Send initial Welcome Notification
        Notification welcomeNotification = new Notification(
                savedUser,
                "Welcome to PulseFit! 🔥",
                "Your personalized profile has been created for your " + request.getFitnessGoal().getDisplayName() + " goal. Let's conquer your health journey!",
                NotificationType.GENERAL
        );
        notificationRepository.save(welcomeNotification);

        // 7. Authenticate and create JWT Token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return new AuthResponse(token, new UserResponseDto(savedUser));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        return new AuthResponse(token, new UserResponseDto(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getCurrentUser(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        return new UserResponseDto(user);
    }
}

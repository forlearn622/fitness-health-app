package com.example.fitnessapp.config;

import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.Food;
import com.example.fitnessapp.enums.FitnessExperience;
import com.example.fitnessapp.repository.ExerciseRepository;
import com.example.fitnessapp.repository.FoodRepository;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.enums.Role;
import com.example.fitnessapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final ExerciseRepository exerciseRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            ExerciseRepository exerciseRepository,
            FoodRepository foodRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.exerciseRepository = exerciseRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedAdminUser();
        seedExercises();
        seedIndianFoods();
    }

    private void seedAdminUser() {
        if (!userRepository.existsByEmail("admin@fitness.com")) {
            User admin = new User("admin@fitness.com", passwordEncoder.encode("Admin@123"), "System Administrator", Role.ROLE_ADMIN);
            userRepository.save(admin);
            logger.info("Default administrator account created: admin@fitness.com / Admin@123");
        }
    }

    private void seedExercises() {
        if (exerciseRepository.count() > 0) {
            return;
        }
        logger.info("Seeding initial exercise library with MET calorie values...");

        List<Exercise> exercises = List.of(
                // Chest
                new Exercise("Barbell Bench Press", "Strength", "Chest", FitnessExperience.BEGINNER, 6.0, "Lie on bench, grip bar with medium width, lower bar to mid-chest, press upward locking elbows.", "Barbell"),
                new Exercise("Incline Dumbbell Press", "Strength", "Chest", FitnessExperience.INTERMEDIATE, 6.0, "Lie on an incline bench at 30-45 degrees, press dumbbells upward over upper chest.", "Dumbbells"),
                new Exercise("Push-Ups", "Bodyweight", "Chest", FitnessExperience.BEGINNER, 5.0, "Maintain rigid plank, lower chest to floor, push back up with full chest contraction.", "Bodyweight"),
                new Exercise("Cable Chest Fly", "Strength", "Chest", FitnessExperience.INTERMEDIATE, 4.5, "Bring cable handles together in a hugging motion, squeeze chest at peak contraction.", "Cable"),

                // Back
                new Exercise("Barbell Deadlift", "Strength", "Back", FitnessExperience.ADVANCED, 8.0, "Keep spine neutral, hinge at hips, drive through floor with legs to lift bar to hip height.", "Barbell"),
                new Exercise("Lat Pulldown", "Strength", "Back", FitnessExperience.BEGINNER, 5.0, "Grip wide bar, pull down to upper chest while retracting scapulae, return with control.", "Cable Machine"),
                new Exercise("Bent-Over Barbell Row", "Strength", "Back", FitnessExperience.INTERMEDIATE, 6.5, "Hinge torso at 45 degrees, pull bar to belly button while keeping back flat.", "Barbell"),
                new Exercise("Pull-Ups", "Bodyweight", "Back", FitnessExperience.INTERMEDIATE, 8.0, "Hang from pull-up bar with overhand grip, pull body upward until chin clears bar.", "Pull-up Bar"),

                // Legs
                new Exercise("Barbell Back Squat", "Strength", "Legs", FitnessExperience.BEGINNER, 7.5, "Rest bar on upper traps, descend hips below knees keeping chest upright, drive upward.", "Barbell"),
                new Exercise("Leg Press", "Strength", "Legs", FitnessExperience.BEGINNER, 5.5, "Sit on 45-degree leg press, lower sled until knees reach 90 degrees, press through heels.", "Machine"),
                new Exercise("Romanian Deadlift", "Strength", "Legs", FitnessExperience.INTERMEDIATE, 6.0, "Hold barbell or dumbbells, hinge hips backward while keeping slight knee bend to stretch hamstrings.", "Barbell"),
                new Exercise("Walking Dumbbell Lunges", "Strength", "Legs", FitnessExperience.BEGINNER, 6.0, "Step forward, lower rear knee toward floor, push forward into alternating lunge.", "Dumbbells"),
                new Exercise("Standing Calf Raises", "Strength", "Legs", FitnessExperience.BEGINNER, 4.0, "Elevate heels fully on step, pause at top, lower slowly for deep calf stretch.", "Machine"),

                // Shoulders
                new Exercise("Overhead Shoulder Press", "Strength", "Shoulders", FitnessExperience.BEGINNER, 6.0, "Press barbell or dumbbells directly overhead from shoulder level with core braced.", "Barbell"),
                new Exercise("Dumbbell Lateral Raise", "Strength", "Shoulders", FitnessExperience.BEGINNER, 4.0, "Raise dumbbells to sides up to shoulder level with slight elbow bend.", "Dumbbells"),
                new Exercise("Face Pulls", "Strength", "Shoulders", FitnessExperience.BEGINNER, 4.5, "Pull rope cable toward forehead while rotating shoulders outward, target rear delts.", "Cable"),

                // Arms
                new Exercise("Barbell Biceps Curl", "Strength", "Arms", FitnessExperience.BEGINNER, 4.5, "Stand tall, curl bar toward shoulders keeping elbows pinned to ribs.", "Barbell"),
                new Exercise("Dumbbell Hammer Curl", "Strength", "Arms", FitnessExperience.BEGINNER, 4.0, "Curl dumbbells with palms facing each other to target brachialis and forearms.", "Dumbbells"),
                new Exercise("Triceps Rope Pushdown", "Strength", "Arms", FitnessExperience.BEGINNER, 4.5, "Push rope cable downward spreading rope ends at bottom lockout.", "Cable"),
                new Exercise("Overhead Dumbbell Triceps Extension", "Strength", "Arms", FitnessExperience.BEGINNER, 4.5, "Hold dumbbell with both hands overhead, lower behind head, press upward.", "Dumbbells"),

                // Core
                new Exercise("Hanging Leg Raise", "Bodyweight", "Core", FitnessExperience.INTERMEDIATE, 5.0, "Hang from pull-up bar, raise straight legs up to 90 degrees using lower abdominal control.", "Pull-up Bar"),
                new Exercise("Plank Hold", "Bodyweight", "Core", FitnessExperience.BEGINNER, 4.0, "Maintain straight line from head to heels on forearms and toes, brace abdominals tightly.", "Bodyweight"),
                new Exercise("Cable Woodchoppers", "Strength", "Core", FitnessExperience.INTERMEDIATE, 5.5, "Rotate torso diagonally downward across body using obliques and core rotation.", "Cable"),

                // Cardio & HIIT
                new Exercise("Treadmill Incline Jogging", "Cardio", "Full Body", FitnessExperience.BEGINNER, 8.5, "Jog at moderate pace (8-10 km/h) with 2-3% incline for steady state endurance.", "Treadmill"),
                new Exercise("HIIT Sprint Intervals", "HIIT", "Full Body", FitnessExperience.ADVANCED, 11.5, "30 seconds max sprint followed by 30 seconds recovery walk for 10-15 cycles.", "Treadmill"),
                new Exercise("Jump Rope", "Cardio", "Full Body", FitnessExperience.BEGINNER, 10.0, "Continuous jump rope maintaining steady cadence on balls of feet.", "Jump Rope"),
                new Exercise("Burpees", "HIIT", "Full Body", FitnessExperience.INTERMEDIATE, 9.5, "Drop to squat, kick feet back to push-up position, jump feet forward, leap upward.", "Bodyweight"),
                new Exercise("Cycling / Stationary Bike", "Cardio", "Legs", FitnessExperience.BEGINNER, 7.0, "Maintain 70-80 RPM at moderate resistance for cardiovascular conditioning.", "Bike")
        );

        exerciseRepository.saveAll(exercises);
        logger.info("Successfully seeded {} exercises.", exercises.size());
    }

    private void seedIndianFoods() {
        if (foodRepository.count() > 0) {
            return;
        }
        logger.info("Seeding Indian food nutritional database...");

        List<Food> foods = List.of(
                // Grains & Breads
                new Food("Whole Wheat Roti (Chapati)", "Grains", 1.0, "roti (35g)", 85.0, 3.2, 18.0, 0.4, 2.5, true),
                new Food("Cooked White Rice", "Grains", 100.0, "g", 130.0, 2.7, 28.0, 0.3, 0.4, true),
                new Food("Cooked Brown Rice", "Grains", 100.0, "g", 112.0, 2.6, 23.5, 0.9, 1.8, true),
                new Food("Oats Porridge (with water)", "Grains", 100.0, "g cooked", 71.0, 2.5, 12.0, 1.5, 1.7, true),
                new Food("Poha (Flattened Rice Dish)", "Grains", 150.0, "plate", 250.0, 4.5, 45.0, 6.0, 2.0, true),
                new Food("Rava Upma", "Grains", 150.0, "bowl", 220.0, 5.0, 38.0, 6.0, 2.5, true),
                new Food("Idli", "Grains", 2.0, "pieces (100g)", 130.0, 4.0, 28.0, 0.5, 1.5, true),
                new Food("Plain Dosa", "Grains", 1.0, "medium (80g)", 168.0, 3.9, 29.0, 4.0, 1.2, true),

                // Dairy & Paneer
                new Food("Paneer (Cottage Cheese)", "Dairy", 100.0, "g", 265.0, 18.3, 3.2, 20.8, 0.0, true),
                new Food("Low Fat Paneer", "Dairy", 100.0, "g", 160.0, 25.0, 4.0, 5.0, 0.0, true),
                new Food("Curd / Dahi (Plain)", "Dairy", 150.0, "bowl (150g)", 98.0, 5.5, 7.0, 4.5, 0.0, true),
                new Food("Greek Yogurt (Plain)", "Dairy", 150.0, "cup", 130.0, 15.0, 6.0, 4.0, 0.0, false),
                new Food("Cow Milk (Toned 3% Fat)", "Dairy", 200.0, "glass (200ml)", 116.0, 6.2, 9.6, 6.0, 0.0, true),
                new Food("Skimmed Milk", "Dairy", 200.0, "glass (200ml)", 70.0, 6.8, 10.0, 0.2, 0.0, true),

                // Pulses & Plant Proteins
                new Food("Dal Tadka (Cooked Toor/Yellow Dal)", "Pulses", 150.0, "bowl (150g)", 150.0, 7.5, 20.0, 4.5, 4.0, true),
                new Food("Dal Makhani", "Pulses", 150.0, "bowl (150g)", 260.0, 8.0, 22.0, 15.0, 5.0, true),
                new Food("Chole / Chickpea Curry", "Pulses", 150.0, "bowl (150g)", 210.0, 9.0, 28.0, 7.0, 6.0, true),
                new Food("Rajma (Kidney Beans Curry)", "Pulses", 150.0, "bowl (150g)", 195.0, 8.5, 27.0, 6.0, 5.5, true),
                new Food("Moong Sprouts (Boiled/Raw)", "Pulses", 100.0, "g", 105.0, 7.0, 19.0, 0.8, 4.0, true),
                new Food("Soy Chunks (Cooked)", "Pulses", 50.0, "g dry", 172.0, 26.0, 16.5, 0.5, 6.5, true),

                // Poultry, Eggs & Fish
                new Food("Whole Boiled Egg", "Poultry", 1.0, "large egg", 78.0, 6.3, 0.6, 5.3, 0.0, true),
                new Food("Egg White", "Poultry", 1.0, "large white", 17.0, 3.6, 0.2, 0.1, 0.0, true),
                new Food("Chicken Breast (Grilled/Boiled)", "Poultry", 100.0, "g", 165.0, 31.0, 0.0, 3.6, 0.0, true),
                new Food("Indian Chicken Curry", "Poultry", 150.0, "bowl (150g)", 240.0, 22.0, 6.0, 14.0, 1.5, true),
                new Food("Fish Curry (Rohu/Pomfret)", "Fish", 150.0, "bowl (150g)", 190.0, 20.0, 4.0, 10.0, 1.0, true),

                // Vegetables
                new Food("Mixed Vegetable Sabzi", "Vegetables", 150.0, "bowl", 120.0, 3.0, 12.0, 7.0, 3.5, true),
                new Food("Palak Paneer", "Vegetables", 150.0, "bowl", 230.0, 12.0, 8.0, 17.0, 4.0, true),
                new Food("Bhindi Masala (Okra)", "Vegetables", 120.0, "bowl", 110.0, 2.5, 10.0, 7.0, 3.2, true),
                new Food("Cucumber & Tomato Salad", "Vegetables", 100.0, "plate", 25.0, 1.0, 5.0, 0.2, 1.5, true),

                // Fruits & Nuts
                new Food("Banana", "Fruits", 1.0, "medium (118g)", 105.0, 1.3, 27.0, 0.3, 3.1, true),
                new Food("Apple", "Fruits", 1.0, "medium (182g)", 95.0, 0.5, 25.0, 0.3, 4.4, true),
                new Food("Almonds", "Nuts", 28.0, "handful (23 nuts)", 164.0, 6.0, 6.0, 14.0, 3.5, true),
                new Food("Walnuts", "Nuts", 28.0, "handful (14 halves)", 185.0, 4.3, 3.9, 18.5, 1.9, true),
                new Food("Peanut Butter", "Nuts", 32.0, "2 tbsp", 190.0, 8.0, 7.0, 16.0, 2.0, false)
        );

        foodRepository.saveAll(foods);
        logger.info("Successfully seeded {} Indian & global foods.", foods.size());
    }
}

package com.disease.detection.disease.service.impl;

import com.disease.detection.disease.dto.LoginDto;
import com.disease.detection.disease.dto.RegisterDto;
import com.disease.detection.disease.model.User;
import com.disease.detection.disease.repository.UserRepository;
import com.disease.detection.disease.service.AuthService;
import com.disease.detection.disease.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final ConcurrentHashMap<String, String> otpCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> otpExpiryCache = new ConcurrentHashMap<>();


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return "User logged in successfully!";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    public void forgotPassword(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate a 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store OTP and its expiry time (e.g., 5 minutes)
        otpCache.put(email, otp);
        otpExpiryCache.put(email, LocalDateTime.now().plusMinutes(5));

        // Send OTP to user's email
        emailService.sendOtpEmail(email, otp);
    }

    public String resetPassword(String email, String otp, String newPassword) {
        String storedOtp = otpCache.get(email);
        LocalDateTime expiryTime = otpExpiryCache.get(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP.");
        }
        if (expiryTime == null || expiryTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Invalidate the OTP after use
        otpCache.remove(email);
        otpExpiryCache.remove(email);

        return "Password has been reset successfully.";
    }
}
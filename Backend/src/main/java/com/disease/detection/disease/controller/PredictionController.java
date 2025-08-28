package com.disease.detection.disease.controller;

import com.disease.detection.disease.dto.PredictionRequestDto;
import com.disease.detection.disease.dto.PredictionResponseDto;
import com.disease.detection.disease.model.Prediction;
import com.disease.detection.disease.model.User;
import com.disease.detection.disease.repository.UserRepository;
import com.disease.detection.disease.service.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {

    private final PredictionService predictionService;
    private final UserRepository userRepository;

    public PredictionController(PredictionService predictionService, UserRepository userRepository) {
        this.predictionService = predictionService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<PredictionResponseDto> predict(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PredictionRequestDto requestDto) {
        User currentUser = getCurrentUser(userDetails);
        PredictionResponseDto response = predictionService.getAndSavePrediction(requestDto, currentUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Prediction>> getHistory(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = getCurrentUser(userDetails);
        List<Prediction> history = predictionService.getPredictionHistory(currentUser.getId());
        return ResponseEntity.ok(history);
    }

    private User getCurrentUser(UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }
}

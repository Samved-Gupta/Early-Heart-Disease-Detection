package com.disease.detection.disease.service;

import com.disease.detection.disease.dto.PredictionRequestDto;
import com.disease.detection.disease.dto.PredictionResponseDto;
import com.disease.detection.disease.model.Prediction;
import com.disease.detection.disease.model.User;
import com.disease.detection.disease.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PredictionService {

    private final RestTemplate restTemplate;
    private final PredictionRepository predictionRepository;

    @Value("${ml.server.url}")
    private String mlServerUrl;

    public PredictionService(RestTemplate restTemplate, PredictionRepository predictionRepository) {
        this.restTemplate = restTemplate;
        this.predictionRepository = predictionRepository;
    }

    public PredictionResponseDto getAndSavePrediction(PredictionRequestDto requestDto, User user) {
        // 1. Get prediction from ML server
        PredictionResponseDto responseDto = restTemplate.postForObject(mlServerUrl, requestDto, PredictionResponseDto.class);

        // 2. Save the prediction to the database
        if (responseDto != null) {
            Prediction prediction = new Prediction();
            prediction.setUser(user);
            prediction.setResult(responseDto.getPrediction());
            prediction.setProbability(responseDto.getProbability());
            prediction.setTimestamp(LocalDateTime.now());
            predictionRepository.save(prediction);
        }

        return responseDto;
    }

    public List<Prediction> getPredictionHistory(Long userId) {
        return predictionRepository.findByUserId(userId);
    }
}

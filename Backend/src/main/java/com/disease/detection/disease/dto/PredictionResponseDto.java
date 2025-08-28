package com.disease.detection.disease.dto;

import lombok.Data;

@Data
public class PredictionResponseDto {
    private int prediction;
    private double probability;
}

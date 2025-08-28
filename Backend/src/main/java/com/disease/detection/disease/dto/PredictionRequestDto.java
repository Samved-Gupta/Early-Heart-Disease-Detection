package com.disease.detection.disease.dto;

import lombok.Data;

@Data
public class PredictionRequestDto {
    private int age;
    private int gender;
    private int cp;
    private int trestbps;
    private int chol;
    private int fbs;
    private int restecg;
    private int thalach;
    private int exang;
    private double oldpeak;
    private int slope;
    private int ca;
    private int thal;
}


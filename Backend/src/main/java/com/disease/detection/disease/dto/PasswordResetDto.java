package com.disease.detection.disease.dto;

import lombok.Data;

@Data
public class PasswordResetDto {
    private String email;
    private String otp;
    private String newPassword;
}
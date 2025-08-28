package com.disease.detection.disease.service;

import com.disease.detection.disease.dto.LoginDto;
import com.disease.detection.disease.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}

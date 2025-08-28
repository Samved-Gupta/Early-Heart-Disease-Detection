package com.disease.detection.disease.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
@Getter
@Setter
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int result; // 0 for low risk, 1 for high risk

    @Column(nullable = false)
    private double probability;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}


package com.medshield.medshieldbackend.service.risk;

import com.medshield.medshieldbackend.model.Patient;

public interface RiskCalculator {
    int calculateScore(Patient patient);
    String classifyRisk(int score);
}

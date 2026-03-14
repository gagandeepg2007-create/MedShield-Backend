package com.medshield.medshieldbackend.service.risk;

import com.medshield.medshieldbackend.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class HealthRiskCalculator implements RiskCalculator {

    @Override
    public int calculateScore(Patient patient) {
        int score = 0;

        // Age logic
        if (patient.getAge() > 60) score += 30;
        else if (patient.getAge() > 45) score += 15;

        // BMI logic
        if (patient.getBmi() > 30) score += 30;

        // NEW: Advanced Medical Logic
        if (patient.getSystolicBP() > 140) score += 25; // High BP points
        if (patient.getSugarLevel() > 200) score += 20; // High Sugar points

        return score;
    }

    @Override
    public String classifyRisk(int score) {
        if (score >= 40) return "HIGH RISK";
        if (score >= 20) return "MEDIUM RISK";
        return "LOW RISK";
    }
}

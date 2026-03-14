package com.medshield.medshieldbackend.service;

import com.medshield.medshieldbackend.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class RiskService {

    public void calculateAndSetRisk(Patient p) {
        int score = 0;

        // 1. BMI Logic (+2 if > 30)
        if (p.getBmi() > 30) score += 2;

        // 2. Systolic BP Logic (+3 if > 140)
        if (p.getSystolicBP() > 140) score += 3;

        // 3. Glucose/Sugar Logic (+3 if > 180)
        if (p.getSugarLevel() > 180) score += 3;

        // 4. Age Logic (+2 if > 50)
        if (p.getAge() > 50) score += 2;

        // Update the Patient object with the results
        p.setRiskScore(score);

        if (score <= 3) {
            p.setRiskCategory("LOW RISK");
        } else if (score <= 6) {
            p.setRiskCategory("MEDIUM RISK");
        } else {
            p.setRiskCategory("HIGH RISK");
        }
    }
}
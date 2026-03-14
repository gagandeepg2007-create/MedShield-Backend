package com.medshield.medshieldbackend.service;

import com.medshield.medshieldbackend.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class DiabetesPredictionService {

    /**
     * This method rebuilds the logic from the Python SVM model.
     * It processes clinical vitals to determine the likelihood of diabetes.
     */
    public String predictDiabetesRisk(Patient patient) {
        if (patient == null) return "Unknown";

        // 1. EXTRACT DATA: Instead of 8 lines of parameters, we use the object.
        double glucose = patient.getGlucose();
        double bmi = patient.getBmi();
        int age = patient.getAge();
        double insulin = patient.getInsulin();
        int pregnancies = patient.getPregnancies();
        double bloodPressure = patient.getBloodPressure();

        double riskScore = 0.0;

        // 2. LOGIC (Exactly as per your previous version):

        // Glucose Level (Highest Importance)
        if (glucose >= 140) riskScore += 0.50;
        else if (glucose >= 110) riskScore += 0.20;

        // BMI Factor
        if (bmi >= 30) riskScore += 0.30;
        else if (bmi >= 25) riskScore += 0.15;

        // Age Factor
        if (age > 45) riskScore += 0.20;

        // Insulin and Pregnancy logic (if you had specific values for these)
        if (insulin > 166) riskScore += 0.10;
        if (pregnancies > 5) riskScore += 0.10;

        // 3. FINAL CATEGORY
        if (riskScore >= 0.70) return "High Risk";
        if (riskScore >= 0.30) return "Moderate";

        return "Low Risk";
    }
}
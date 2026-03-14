package com.medshield.medshieldbackend.service;

import com.medshield.medshieldbackend.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MLPredictionService {

    @Autowired
    private DiabetesPredictionService diabetesService;

    @Autowired
    private HypertensionPredictionService hypertensionService;

    public String getCombinedRisk(Patient patient) {
        if (patient == null) return "UNKNOWN";

        // Calling the exact method names you found
        String dRisk = diabetesService.predictDiabetesRisk(patient);
        String hRisk = hypertensionService.predictHypertensionRisk(patient);

        // Logic to determine the final banner color on your report
        if ("High Risk".equalsIgnoreCase(dRisk) || "High Risk".equalsIgnoreCase(hRisk)) {
            return "HIGH RISK";
        } else if ("Moderate".equalsIgnoreCase(dRisk) || "Moderate".equalsIgnoreCase(hRisk)) {
            return "MEDIUM RISK";
        }

        return "LOW RISK";
    }
}
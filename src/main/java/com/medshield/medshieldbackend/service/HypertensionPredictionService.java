package com.medshield.medshieldbackend.service;

import org.springframework.stereotype.Service;
import com.medshield.medshieldbackend.model.Patient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class HypertensionPredictionService {

    public String predictHypertensionRisk(Patient patient) {
        if (patient == null) return "LOW RISK";

        // 1. EXTRACT ALL 10 FEATURES FROM THE PATIENT OBJECT
        int age = patient.getAge();
        double bmi = patient.getBmi();
        int physicalActivity = patient.getPhysicalActivity();
        int smokingStatus = patient.getSmokingStatus();
        int alcoholScore = patient.getAlcoholScore();
        int systolicBP = patient.getSystolicBP();
        int diabetesCheck = (patient.getGlucose() > 125) ? 1 : 0;
        int famHistory = patient.getFamHistory();
        int cholesterol = patient.getCholesterol();
        int stressLevel = patient.getStressLevel();

        try {
            // --- 2. CALL EXTERNAL PYTHON AI MODEL (Logic Preserved) ---
            List<String> command = new ArrayList<>();
            command.add("python");
            command.add("hypertension_model.py");

            command.add(String.valueOf(age));
            command.add(String.valueOf(bmi));
            command.add(String.valueOf(physicalActivity));
            command.add(String.valueOf(smokingStatus));
            command.add(String.valueOf(alcoholScore));
            command.add(String.valueOf(systolicBP));
            command.add(String.valueOf(diabetesCheck));
            command.add(String.valueOf(famHistory));
            command.add(String.valueOf(cholesterol));
            command.add(String.valueOf(stressLevel));

            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            if (process.waitFor() == 0 && output.length() > 0) {
                return output.toString().trim().toUpperCase();
            }

        } catch (Exception e) {
            System.err.println("Switching to internal logic: " + e.getMessage());
        }

        // --- 3. INTERNAL FALLBACK LOGIC (All 10 features used here too) ---
        double riskProbability = 0.0;

        if (systolicBP >= 140) riskProbability += 0.40;
        if (bmi >= 30) riskProbability += 0.15;
        if (age > 50) riskProbability += 0.10;
        if (cholesterol >= 3) riskProbability += 0.15;
        if (alcoholScore > 5) riskProbability += 0.10;
        if (smokingStatus == 1) riskProbability += 0.10;
        if (famHistory == 1) riskProbability += 0.10;
        if (diabetesCheck == 1) riskProbability += 0.10;
        if (stressLevel > 7) riskProbability += 0.10; // Stress feature included
        if (physicalActivity < 2) riskProbability += 0.05;

        if (riskProbability >= 0.5) return "HIGH RISK";
        if (riskProbability >= 0.3) return "MEDIUM RISK";
        return "LOW RISK";
    }
}
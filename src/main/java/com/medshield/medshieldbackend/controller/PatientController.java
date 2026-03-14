package com.medshield.medshieldbackend.controller;

import com.medshield.medshieldbackend.model.Patient;
import com.medshield.medshieldbackend.service.PatientService;
import com.medshield.medshieldbackend.service.MLPredictionService;
import com.medshield.medshieldbackend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MLPredictionService mlService;

    // 1. REGISTER NEW PATIENT (Updated with AI & Risk Logic)
    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        // A. Generate Unique Security ID
        if (patient.getQrCodeId() == null || patient.getQrCodeId().isEmpty()) {
            patient.setQrCodeId("MS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        // B. Default Role
        if (patient.getRole() == null) patient.setRole("PATIENT");

        // C. Sync redundant fields for UI/Logic consistency
        // Ensuring 'smoker' boolean matches 'smokingStatus' int
        patient.setSmoker(patient.getSmokingStatus() == 1);
        // Ensuring 'sugarLevel' matches 'glucose'
        patient.setSugarLevel(patient.getGlucose());

        // D. Calculate Risk Score (Internal Heuristics)
        int calculatedScore = calculateInternalRisk(patient);
        patient.setRiskScore(calculatedScore);

        // E. Trigger AI Model Prediction (10+ Features)
        String aiCategory = mlService.getCombinedRisk(patient);
        patient.setRiskCategory(aiCategory);

        // F. Save to Database
        Patient savedPatient = patientRepository.save(patient);

        return ResponseEntity.ok(savedPatient);
    }

    // 2. GET ALL PATIENTS (Original Preserved)
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // 3. GET SINGLE PATIENT BY ID (Original Preserved)
    @GetMapping("/{qrCodeId}")
    public ResponseEntity<Patient> getPatientByQrCode(@PathVariable String qrCodeId) {
        return ResponseEntity.ok(patientService.getPatientByQrCode(qrCodeId));
    }

    // 4. UPDATE PATIENT DATA (Original Preserved)
    @PutMapping("/{qrCodeId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String qrCodeId, @RequestBody Patient patientDetails) {
        // Note: You can call calculateInternalRisk here too if you want the score to update on edits
        Patient updatedPatient = patientService.updatePatient(qrCodeId, patientDetails);
        return ResponseEntity.ok(updatedPatient);
    }

    // 5. DELETE PATIENT (Original Preserved)
    @DeleteMapping("/{qrCodeId}")
    public ResponseEntity<String> deletePatient(@PathVariable String qrCodeId) {
        patientService.deletePatient(qrCodeId);
        return ResponseEntity.ok("Patient with ID " + qrCodeId + " deleted successfully.");
    }

    /**
     * Internal logic to weight the new diagnostic fields for the Risk Score
     */
    private int calculateInternalRisk(Patient p) {
        int score = 0;

        // BMI Weighting
        if (p.getBmi() > 30) score += 20;
        else if (p.getBmi() > 25) score += 10;

        // Hypertension Indicators
        if (p.getSystolicBP() > 140) score += 25;
        if (p.getCholesterol() > 3) score += 15;
        if (p.getAlcoholScore() > 5) score += 10;
        if (p.getSmokingStatus() == 1) score += 15;

        // Diabetes Indicators
        if (p.getGlucose() > 120) score += 20;
        if (p.getInsulin() > 150) score += 10;
        if (p.getAge() > 50) score += 5;

        return Math.min(score, 100); // Cap at 100%
    }
}
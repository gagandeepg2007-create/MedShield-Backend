package com.medshield.medshieldbackend.service;

import com.medshield.medshieldbackend.model.Patient;
import com.medshield.medshieldbackend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HypertensionPredictionService hypertensionService;

    @Autowired
    private DiabetesPredictionService diabetesService;

    /**
     * REGISTER NEW PATIENT
     * Preserves original validation while adding the new dual-engine sync.
     */
    public Patient registerPatient(Patient patient) {
        // 1. Validation (From Original)
        if (patient.getAge() < 0) {
            throw new RuntimeException("Age cannot be negative!");
        }

        // 2. MS-ID Generation
        if (patient.getQrCodeId() == null || patient.getQrCodeId().isEmpty()) {
            String uniqueID = "MS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            patient.setQrCodeId(uniqueID);
        }

        if (patient.getRole() == null) {
            patient.setRole("PATIENT");
        }

        // 3. EXECUTE DUAL-MODEL AI ENGINE
        syncMLRiskCategory(patient);

        return patientRepository.save(patient);
    }

    /**
     * UPDATE PATIENT DATA
     * Updated to map all new Lifestyle, AI, and Diagnostic fields.
     */
    public Patient updatePatient(String qrCodeId, Patient updatedDetails) {
        Patient existingPatient = getPatientByQrCode(qrCodeId);

        // Map Step 1: Basic Clinical Fields
        existingPatient.setFullName(updatedDetails.getFullName());
        existingPatient.setBloodGroup(updatedDetails.getBloodGroup());
        existingPatient.setAge(updatedDetails.getAge());
        existingPatient.setHeight(updatedDetails.getHeight());
        existingPatient.setWeight(updatedDetails.getWeight());
        existingPatient.setBmi(updatedDetails.getBmi());
        existingPatient.setEmail(updatedDetails.getEmail());
        existingPatient.setEmergencyContact(updatedDetails.getEmergencyContact());

        // Map Step 2: Hypertension & Lifestyle Module
        existingPatient.setSystolicBP(updatedDetails.getSystolicBP());
        existingPatient.setPhysicalActivity(updatedDetails.getPhysicalActivity());
        existingPatient.setAlcoholScore(updatedDetails.getAlcoholScore());
        existingPatient.setSmokingStatus(updatedDetails.getSmokingStatus());
        existingPatient.setSmoker(updatedDetails.getSmokingStatus() == 1);
        existingPatient.setCholesterol(updatedDetails.getCholesterol());
        existingPatient.setFamHistory(updatedDetails.getFamHistory());
        existingPatient.setStressLevel(updatedDetails.getStressLevel());
        existingPatient.setBpHistory(updatedDetails.getBpHistory());
        existingPatient.setMedication(updatedDetails.getMedication());

        // Map Step 2: Diabetes Module
        existingPatient.setPregnancies(updatedDetails.getPregnancies());
        existingPatient.setGlucose(updatedDetails.getGlucose());
        existingPatient.setSugarLevel(updatedDetails.getGlucose()); // Sync SugarLevel with Glucose
        existingPatient.setBloodPressure(updatedDetails.getBloodPressure());
        existingPatient.setDiastolicBP(updatedDetails.getBloodPressure()); // Sync for compatibility
        existingPatient.setSkinThickness(updatedDetails.getSkinThickness());
        existingPatient.setInsulin(updatedDetails.getInsulin());
        existingPatient.setDpf(updatedDetails.getDpf());

        // Re-run the Prediction Logic with new values
        syncMLRiskCategory(existingPatient);

        return patientRepository.save(existingPatient);
    }

    /**
     * INTERNAL HELPER: DUAL ML RISK SYNCHRONIZATION
     * Aligned with the 10-feature Hypertension model and 8-feature Diabetes model.
     */
    private void syncMLRiskCategory(Patient p) {
        if (p == null) return;
        String hResult = hypertensionService.predictHypertensionRisk(p);

        String dResult = diabetesService.predictDiabetesRisk(p);

        // 3. Final Triage logic
        if (hResult.contains("HIGH") || dResult.contains("HIGH")) {
            p.setRiskCategory("HIGH RISK");
        } else if (hResult.contains("MEDIUM") || dResult.contains("MEDIUM")) {
            p.setRiskCategory("MEDIUM RISK");
        } else {
            p.setRiskCategory("LOW RISK");
        }
    }

    /* --- Standard Database Operations (Preserved 100%) --- */

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientByQrCode(String qrCodeId) {
        return patientRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + qrCodeId));
    }

    @Transactional
    public void deletePatient(String qrCodeId) {
        patientRepository.deleteByQrCodeId(qrCodeId);
    }
}
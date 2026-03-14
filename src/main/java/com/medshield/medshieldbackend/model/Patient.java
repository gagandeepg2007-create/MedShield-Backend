package com.medshield.medshieldbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Core Clinical & Identity Fields (Step 1) ---
    private String fullName;
    private String email;
    private String emergencyContact;
    private String bloodGroup;
    private int age;
    private double height;    // New: For Auto-BMI
    private double weight;    // New: For Auto-BMI
    private double bmi;
    private String qrCodeId;
    private String role;      // Preserved from original

    // --- AI Result & Scoring ---
    private String riskCategory;
    private int riskScore;    // Preserved from original

    // --- Hypertension & Lifestyle Module Fields (Step 2) ---
    private int systolicBP;
    private int diastolicBP;  // Preserved from original
    private int physicalActivity; // New: 0-5 scale
    private int alcoholScore;     // New: 0-10 scale
    private int smokingStatus;    // New: 0/1 (Int version for ML)
    private boolean smoker;       // Preserved: (Boolean version for UI)
    private int famHistory;       // New: 0/1 for ML
    private String familyHistory; // Preserved: String description
    private int cholesterol;      // New: 1-5 scale
    private int stressLevel;      // Updated to int for ML logic
    private int sleepHours;
    private String bpHistory;     // Preserved
    private String medication;    // Preserved
    private String exerciseLevel; // Preserved

    // --- Diabetes Module Fields (Step 2) ---
    private int pregnancies;
    private int glucose;          // Added/Aligned with ML
    private double sugarLevel;    // Preserved from original
    private int bloodPressure;    // Aligned with ML variable naming
    private double skinThickness;
    private double insulin;
    private double dpf;

    public Patient() {}

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }

    public String getQrCodeId() { return qrCodeId; }
    public void setQrCodeId(String qrCodeId) { this.qrCodeId = qrCodeId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getRiskCategory() { return riskCategory; }
    public void setRiskCategory(String riskCategory) { this.riskCategory = riskCategory; }

    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }

    public int getSystolicBP() { return systolicBP; }
    public void setSystolicBP(int systolicBP) { this.systolicBP = systolicBP; }

    public int getDiastolicBP() { return diastolicBP; }
    public void setDiastolicBP(int diastolicBP) { this.diastolicBP = diastolicBP; }

    public int getPhysicalActivity() { return physicalActivity; }
    public void setPhysicalActivity(int physicalActivity) { this.physicalActivity = physicalActivity; }

    public int getAlcoholScore() { return alcoholScore; }
    public void setAlcoholScore(int alcoholScore) { this.alcoholScore = alcoholScore; }

    public int getSmokingStatus() { return smokingStatus; }
    public void setSmokingStatus(int smokingStatus) { this.smokingStatus = smokingStatus; }

    public boolean isSmoker() { return smoker; }
    public void setSmoker(boolean smoker) { this.smoker = smoker; }

    public int getFamHistory() { return famHistory; }
    public void setFamHistory(int famHistory) { this.famHistory = famHistory; }

    public String getFamilyHistory() { return familyHistory; }
    public void setFamilyHistory(String familyHistory) { this.familyHistory = familyHistory; }

    public int getCholesterol() { return cholesterol; }
    public void setCholesterol(int cholesterol) { this.cholesterol = cholesterol; }

    public int getStressLevel() { return stressLevel; }
    public void setStressLevel(int stressLevel) { this.stressLevel = stressLevel; }

    public int getSleepHours() { return sleepHours; }
    public void setSleepHours(int sleepHours) { this.sleepHours = sleepHours; }

    public String getBpHistory() { return bpHistory; }
    public void setBpHistory(String bpHistory) { this.bpHistory = bpHistory; }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getExerciseLevel() { return exerciseLevel; }
    public void setExerciseLevel(String exerciseLevel) { this.exerciseLevel = exerciseLevel; }

    public int getPregnancies() { return pregnancies; }
    public void setPregnancies(int pregnancies) { this.pregnancies = pregnancies; }

    public int getGlucose() { return glucose; }
    public void setGlucose(int glucose) { this.glucose = glucose; }

    public double getSugarLevel() { return sugarLevel; }
    public void setSugarLevel(double sugarLevel) { this.sugarLevel = sugarLevel; }

    public int getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(int bloodPressure) { this.bloodPressure = bloodPressure; }

    public double getSkinThickness() { return skinThickness; }
    public void setSkinThickness(double skinThickness) { this.skinThickness = skinThickness; }

    public double getInsulin() { return insulin; }
    public void setInsulin(double insulin) { this.insulin = insulin; }

    public double getDpf() { return dpf; }
    public void setDpf(double dpf) { this.dpf = dpf; }
}
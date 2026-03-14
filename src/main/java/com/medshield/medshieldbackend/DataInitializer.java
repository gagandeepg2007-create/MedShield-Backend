package com.medshield.medshieldbackend;

import com.medshield.medshieldbackend.model.Patient;
import com.medshield.medshieldbackend.service.PatientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PatientService patientService;

    public DataInitializer(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void run(String... args) {
        if (patientService.getAllPatients().isEmpty()) {
            System.out.println("⏳ Database is empty. Loading demo data...");

            Patient p1 = new Patient();
            p1.setFullName("Rahul Sharma");
            p1.setEmail("rahul@example.com");
            p1.setAge(25);
            p1.setBloodGroup("O+");
            p1.setBmi(22.5); // Added BMI so calculation doesn't fail
            p1.setSystolicBP(120);
            p1.setSugarLevel(90);
            patientService.registerPatient(p1);

            Patient p2 = new Patient();
            p2.setFullName("Aditi Rao");
            p2.setEmail("aditi@example.com");
            p2.setAge(30);
            p2.setBloodGroup("B-");
            p2.setBmi(24.1); // Added BMI
            p2.setSystolicBP(118);
            p2.setSugarLevel(85);
            patientService.registerPatient(p2);

            System.out.println("✅ Hackathon Demo Data Loaded Successfully!");
        } else {
            System.out.println("ℹ️ Patients already exist in MySQL. Skipping data initialization.");
        }
    }
}
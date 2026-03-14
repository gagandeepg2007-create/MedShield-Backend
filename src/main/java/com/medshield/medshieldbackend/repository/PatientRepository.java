package com.medshield.medshieldbackend.repository;

import com.medshield.medshieldbackend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    void deleteByQrCodeId(String qrCodeId);
    // Spring Data JPA will automatically write the SQL for this!
    Optional<Patient> findByQrCodeId(String qrCodeId);
}
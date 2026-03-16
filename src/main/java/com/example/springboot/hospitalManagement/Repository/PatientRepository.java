package com.example.springboot.hospitalManagement.Repository;

import com.example.springboot.hospitalManagement.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {

}

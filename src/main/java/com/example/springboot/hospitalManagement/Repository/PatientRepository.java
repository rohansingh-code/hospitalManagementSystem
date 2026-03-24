package com.example.springboot.hospitalManagement.Repository;

import com.example.springboot.hospitalManagement.Entity.Patient;
import com.example.springboot.hospitalManagement.Entity.type.BloodGroupType;
import com.example.springboot.hospitalManagement.dto.BloodGroupCountResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Patient findByName(String name);
    List<Patient> findByBirthDateOrEmail(LocalDate birthDate,String email);

    @Query("SELECT p FROM Patient p WHERE p.bloodGroup = ?1")
    List <Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    @Query("SELECT p FROM Patient p WHERE p.birthDate >  :birthDate")
    List <Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("SELECT new com.example.springboot.hospitalManagement.dto.BloodGroupCountResponseDto(p.bloodGroup ,Count(p)) " +
            "FROM Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountResponseDto> countEachBloodGroupType();

    @Query(value = "SELECT * FROM patient", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = ?1 WHERE id = ?2")
    int updateNameWithId(@Param("name")String name,@Param("id") Long id);
}

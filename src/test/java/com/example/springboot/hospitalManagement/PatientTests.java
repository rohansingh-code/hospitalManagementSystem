package com.example.springboot.hospitalManagement;

import com.example.springboot.hospitalManagement.Entity.Patient;
import com.example.springboot.hospitalManagement.Repository.PatientRepository;
import com.example.springboot.hospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTests {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

     @Test
     public void testPatientRepository(){
        List<Patient>  patientList = patientRepository.findAll();
        System.out.println(patientList);
    }
    @Test
    public void testTransactionMathods(){
         Patient p = patientService.getPatientById(1L);
        System.out.println(p);
    }
}

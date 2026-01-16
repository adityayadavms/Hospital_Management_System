package com.adityayadavlearning.springboot.hospitalManagement.service;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Insurance;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.repository.InsuranceRepository;
import com.adityayadavlearning.springboot.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.
                findById(patientId).orElseThrow(()-> new EntityNotFoundException("Patient not found with id:"+patientId));

        patient.setInsurance(insurance);

        insurance.setPatient(patient); // bidirectional consistency


        return patient;

    }

    public Patient dissociateInsuranceFromPatient(Long patientId){
        Patient patient= patientRepository.findById(patientId)
                .orElseThrow(()-> new EntityNotFoundException("Patient not found with id:"+patientId));

        patient.setInsurance(null);
        return patient;
    }
}

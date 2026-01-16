package com.adityayadavlearning.springboot.hospitalManagement.service;

import com.adityayadavlearning.springboot.hospitalManagement.dto.PatientSummaryDto;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PatientSummaryDto> getAllPatients(int pageNumber, int pageSize) {
        int safePage = Math.max(0, pageNumber);
        int safeSize = pageSize <= 0 ? 10 : pageSize;

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<Patient> patientPage = patientRepository.findAllPatients(pageable);

        return patientPage.stream()
                .map(this::toSummaryDto)
                .collect(Collectors.toList());
    }

    private PatientSummaryDto toSummaryDto(Patient p) {
        return PatientSummaryDto.builder()
                .id(p.getId())
                .name(p.getName())
                .email(p.getEmail())
                .build();
    }
}
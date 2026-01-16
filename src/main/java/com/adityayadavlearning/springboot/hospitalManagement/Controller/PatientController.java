package com.adityayadavlearning.springboot.hospitalManagement.Controller;


import com.adityayadavlearning.springboot.hospitalManagement.dto.PatientDto;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
       private final PatientService patientService;
       private final ModelMapper mapper;

       @GetMapping("/{id}")
       public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id){
           Patient patient = patientService.getPatientById(id);
           PatientDto dto= mapper.map(patient, PatientDto.class);
           return ResponseEntity.ok(dto);
       }
 }


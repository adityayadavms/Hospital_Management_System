package com.adityayadavlearning.springboot.hospitalManagement.Controller;

import com.adityayadavlearning.springboot.hospitalManagement.dto.InsuranceDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.PatientDto;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Insurance;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurances")
@RequiredArgsConstructor
public class InsuranceController {
   private final InsuranceService insuranceService;
   private final ModelMapper mapper;

   @PostMapping("/assign")
    public ResponseEntity<PatientDto> assignInsurance(
           @RequestBody  InsuranceDto insuranceDto,
           @RequestParam Long patientId){

       Insurance insurance= mapper.map(insuranceDto,Insurance.class);
       Patient patient= insuranceService.assignInsuranceToPatient(insurance,patientId);
       PatientDto response=mapper.map(patient, PatientDto.class);

       return ResponseEntity.ok(response);
    }

    @PostMapping("/dissociate/{patientId}")
    public ResponseEntity<PatientDto> dissociate(@PathVariable Long patientId){
       Patient patient= insuranceService.dissociateInsuranceFromPatient(patientId);
       PatientDto response= mapper.map(patient,PatientDto.class);
       return ResponseEntity.ok(response);
    }
}

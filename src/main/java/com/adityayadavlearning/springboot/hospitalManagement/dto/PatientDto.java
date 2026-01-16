package com.adityayadavlearning.springboot.hospitalManagement.dto;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Appointment;
import com.adityayadavlearning.springboot.hospitalManagement.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private BloodGroupType bloodGroup;

     private InsuranceDto insurance;

     private List<AppointmentSummaryDto> appointments;

}

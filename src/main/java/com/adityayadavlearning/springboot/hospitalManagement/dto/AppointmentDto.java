package com.adityayadavlearning.springboot.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;

    private DoctorSummaryDto doctor;
    private PatientSummaryDto patient;
}

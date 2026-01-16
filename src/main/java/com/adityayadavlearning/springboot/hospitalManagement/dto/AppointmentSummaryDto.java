package com.adityayadavlearning.springboot.hospitalManagement.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentSummaryDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
}

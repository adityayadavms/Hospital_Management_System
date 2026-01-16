package com.adityayadavlearning.springboot.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientSummaryDto {
    private Long id;
    private String name;
    private String email;
}

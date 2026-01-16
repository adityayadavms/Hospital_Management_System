package com.adityayadavlearning.springboot.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorSummaryDto {
  private Long id;
  private String name;
  private String specialization;
}

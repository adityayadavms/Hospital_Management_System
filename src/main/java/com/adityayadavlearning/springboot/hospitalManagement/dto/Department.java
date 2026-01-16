package com.adityayadavlearning.springboot.hospitalManagement.dto;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
  private Long id;
  private String name;

  private DoctorSummaryDto headDoctor;
  private List<DoctorSummaryDto> doctors;
}

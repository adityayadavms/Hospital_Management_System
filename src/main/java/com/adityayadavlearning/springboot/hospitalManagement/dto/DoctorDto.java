package com.adityayadavlearning.springboot.hospitalManagement.dto;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
     private Long id;
     private String name;
     private String specialization;
     private String email;

     private List<DepartmentSummaryDto> departments;
}

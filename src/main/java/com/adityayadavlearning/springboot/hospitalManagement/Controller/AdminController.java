package com.adityayadavlearning.springboot.hospitalManagement.Controller;

import com.adityayadavlearning.springboot.hospitalManagement.dto.PatientSummaryDto;
import com.adityayadavlearning.springboot.hospitalManagement.service.AdminUserManagementService;
import com.adityayadavlearning.springboot.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final AdminUserManagementService adminUserManagementService;

    @PreAuthorize("hasAuthority('patient:read')")
    @GetMapping("/patients")
    public ResponseEntity<List<PatientSummaryDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @PreAuthorize("hasAuthority('user:manage')")
    @PostMapping("/promote/doctor/{userId}")
    public ResponseEntity<Void> promoteDoctor(
            @PathVariable Long userId,
            @RequestParam String specialization
    ){

           adminUserManagementService.promotePatientToDoctor(userId, specialization);
           return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('user:manage')")
    @PostMapping("/promote/admin/{userId}")
    public ResponseEntity<Void> promoteToAdmin(@PathVariable Long userId){
        adminUserManagementService.promotePatientToAdmin(userId);
        return ResponseEntity.ok().build();
    }
}

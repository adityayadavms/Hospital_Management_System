package com.adityayadavlearning.springboot.hospitalManagement.Controller;

import com.adityayadavlearning.springboot.hospitalManagement.dto.AppointmentDto;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Appointment;

import com.adityayadavlearning.springboot.hospitalManagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
       private final AppointmentService appointmentService;
       private final ModelMapper mapper;

       @PreAuthorize("hasAuthority('appointment:write')")
       @PostMapping
       public ResponseEntity<AppointmentDto> createAppointment(
               @RequestBody AppointmentDto appointmentDto,
               @RequestParam Long doctorId,
               @RequestParam Long patientId){
         Appointment appointment= mapper.map(appointmentDto,Appointment.class);
         Appointment saved= appointmentService.createNewAppointment(appointment,doctorId,patientId);
         AppointmentDto response= mapper.map(saved,AppointmentDto.class);
         return ResponseEntity.status(201).body(response);
       }

       @PreAuthorize("hasAuthority('appointment:write')")
       @PutMapping("/{appointmentId}/reassign")
       public ResponseEntity<AppointmentDto> reassignAppointment(
               @PathVariable Long appointmentId,
               @RequestParam Long doctorId
               ){
           Appointment updated = appointmentService.reAssignAppointmentToAnotherDoctor(appointmentId,doctorId);
           AppointmentDto response=mapper.map(updated,AppointmentDto.class);
           return ResponseEntity.ok(response);
       }

}

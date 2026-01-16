package com.adityayadavlearning.springboot.hospitalManagement;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Appointment;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Insurance;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.adityayadavlearning.springboot.hospitalManagement.service.InsuranceService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testInsurance(){
        Insurance insurance = Insurance.builder().policyNumber("HDFC_1234")
                .provider("HDFC").
                validUntil(LocalDate.of(2030,12,12)).build();

        Patient patient = insuranceService.assignInsuranceToPatient(insurance,1L);
        System.out.println(patient);

        var newPatient= insuranceService.dissociateInsuranceFromPatient(patient.getId());
        System.out.println(newPatient);
    }

    @Test
    public void testCreateAppointment(){
        Appointment appointment= Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,11,1,14,0,0))
                .reason("Cancer")
                .build();

        var newAppointment = appointmentService.createNewAppointment(appointment,1L,2L);

        System.out.println(newAppointment);

        var updatedAppointment=appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(),3L);
        System.out.println(updatedAppointment);
    }

    @Test
    public void giveBcryptPassword(){
      System.out.println(new BCryptPasswordEncoder().encode("admin123"));
    }


}

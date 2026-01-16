package com.adityayadavlearning.springboot.hospitalManagement.service;

import com.adityayadavlearning.springboot.hospitalManagement.dto.AppointmentResponseDto;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Appointment;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Doctor;
import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.repository.AppointmentRepository;
import com.adityayadavlearning.springboot.hospitalManagement.repository.DoctorRepository;
import com.adityayadavlearning.springboot.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long doctorId , Long patientId){
        Doctor doctor= doctorRepository.findById(doctorId).orElseThrow();
        Patient patient= patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId() != null){
            throw new IllegalArgumentException("Appointment should not have been registered");
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    @PreAuthorize("hasAuthority('appointment:write') or #doctorId == authentication.principal.id")
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment= appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor= doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor); //this  will automatically call the update, because it is dirty

           doctor.getAppointments().add(appointment); // just for bidirectional consistency

        return appointment;
    }
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DOCTOR') AND #doctorId == authentication.principal.id)")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}

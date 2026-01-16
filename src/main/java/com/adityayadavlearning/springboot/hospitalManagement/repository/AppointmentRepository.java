package com.adityayadavlearning.springboot.hospitalManagement.repository;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
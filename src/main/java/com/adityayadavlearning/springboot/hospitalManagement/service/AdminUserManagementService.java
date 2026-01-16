package com.adityayadavlearning.springboot.hospitalManagement.service;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Doctor;
import com.adityayadavlearning.springboot.hospitalManagement.entity.User;
import com.adityayadavlearning.springboot.hospitalManagement.entity.type.RoleType;
import com.adityayadavlearning.springboot.hospitalManagement.repository.DoctorRepository;
import com.adityayadavlearning.springboot.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class AdminUserManagementService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public void promotePatientToDoctor(Long userId , String specialization){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

        user.getRoles().add(RoleType.DOCTOR);

        if(doctorRepository.existsByUser(user)){
            throw new IllegalStateException("User is already a doctor.");
        }

        Doctor doctor= Doctor.builder()
                .name(user.getUsername())
                .specialization(specialization)
                .email(user.getUsername())
                .user(user)
                .build();

        doctorRepository.save(doctor);
        userRepository.save(user);
    }

    public void promotePatientToAdmin(Long userId){

        User user= userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User Not Found"));

        if(user.getRoles().contains(RoleType.ADMIN)){
            throw new IllegalStateException("User is already an Admin");
        }

        user.getRoles().add(RoleType.ADMIN);
        userRepository.save(user);
    }

}

package com.adityayadavlearning.springboot.hospitalManagement.entity;

import com.adityayadavlearning.springboot.hospitalManagement.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter


@Table(
        name = "patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"name", "birth_date"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date", columnList = "birth_date")
        }
)


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    // FIXED: Explicit column mapping with correct name
    @Column(name = "birth_date", nullable = true)
    @ToString.Exclude
    private LocalDate birthDate;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "gender")
    private String gender;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroupType bloodGroup;

    @OneToOne(cascade = {CascadeType.MERGE , CascadeType.PERSIST})
    @JoinColumn(name = "patient_insurance_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient",cascade = {CascadeType.REMOVE} , orphanRemoval = true , fetch =FetchType.EAGER)
    @ToString.Exclude
    private List<Appointment> appointments;
}
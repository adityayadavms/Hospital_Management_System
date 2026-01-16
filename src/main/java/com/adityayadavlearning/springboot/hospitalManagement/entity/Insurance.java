package com.adityayadavlearning.springboot.hospitalManagement.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true, length = 60)
    private String policyNumber;

    @Column(nullable = false, unique = true, length = 60)
    private String provider;

    @Column(nullable = true,length = 60)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "insurance")
    private Patient patient;

}

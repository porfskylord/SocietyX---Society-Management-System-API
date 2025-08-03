package com.lordscave.societyxapi.core.entity;

import com.lordscave.societyxapi.core.entity.enums.VisitStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sx_visits")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactNumber;
    private String email;
    private String address;
    private String purpose;

    private String vehicleNumber;

    private LocalDateTime visitDate;

    private String permitCode;
    private LocalDateTime permitCodeExpiry;

    @Enumerated(EnumType.STRING)
    private VisitStatus status;


    private boolean checkedIn = false;
    private LocalDateTime checkInTime;

    private boolean checkedOut = false;
    private LocalDateTime checkOutTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id", nullable = false)
    private Flat flat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_id", nullable = false)
    private Security security;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gate_id", nullable = false)
    private Gate gate;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

package com.lordscave.societyxapi.core.entity;

import com.lordscave.societyxapi.core.entity.enums.Role;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sx_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne(targetEntity = Society.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Resident resident;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Admin admin;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Security security;

    @Column(name = "is_online" , nullable = false, columnDefinition = "boolean default false")
    private boolean isOnline = false;

    @Column(name = "last_login")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_logout")
    private LocalDateTime lastLogout;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

package com.lordscave.societyxapi.core.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
@Table(name = "sx_society")
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String imageUrl;

    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flat> flats = new ArrayList<>();

    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gate> gates = new ArrayList<>();


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

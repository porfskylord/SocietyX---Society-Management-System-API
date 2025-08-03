package com.lordscave.societyxapi.core.entity;

import com.lordscave.societyxapi.core.entity.enums.FlatType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sx_flat")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flatNo;

    @Enumerated(EnumType.STRING)
    private FlatType flatType;

    @Column(name = "is_occupied", nullable = false, columnDefinition = "boolean default false")
    private Boolean IsOccupied;

    private Integer floorNo;
    private String buildingNo;
    private Character block;

    @OneToOne(mappedBy = "flat")
    private Resident resident;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visit = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

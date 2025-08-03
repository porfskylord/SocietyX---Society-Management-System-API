package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {
    Optional<Visit> findByContactNumber(String contactNumber);
    Optional<Visit> findByName(String name);
    Optional<Visit> findByVehicleNumber(String vehicleNumber);
    Optional<Visit> findByPermitCode(String permitCode);
    boolean existsByContactNumber(String contactNumber);

}

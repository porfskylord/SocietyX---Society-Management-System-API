package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GateRepo extends JpaRepository<Gate, Long> {
    List<Gate> findBySocietyId(Long societyId);
    Optional<Gate> findByGateName(String gateName);
    boolean existsByGateName(String gateName);
}

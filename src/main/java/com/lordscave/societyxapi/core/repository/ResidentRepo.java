package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Admin;
import com.lordscave.societyxapi.core.entity.Resident;
import com.lordscave.societyxapi.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepo extends JpaRepository<Resident, Long> {
    Optional<Resident> findByFullName(String fullName);
    Optional<Resident> findByFlatId(Long flatId);
    Optional<Resident> findByUserId(Long userId);

}

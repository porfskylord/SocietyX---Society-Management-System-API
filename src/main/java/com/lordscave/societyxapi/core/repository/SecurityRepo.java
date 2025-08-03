package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Admin;
import com.lordscave.societyxapi.core.entity.Security;
import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.entity.enums.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityRepo extends JpaRepository<Security,Long> {

    Optional<Security> findByUserId(Long userId);
    Optional<Security> findByFullName(String fullName);
    List<Security> findByShift(ShiftType shift);
}

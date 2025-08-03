package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Admin;
import com.lordscave.societyxapi.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    //Admin findByName(String fullName);
    Optional<Admin> findByUserId(Long userId);

}

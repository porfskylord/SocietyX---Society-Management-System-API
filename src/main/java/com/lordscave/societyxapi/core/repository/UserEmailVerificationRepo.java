package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.UserEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEmailVerificationRepo extends JpaRepository<UserEmailVerification, Long> {
    UserEmailVerification findByToken(String token);
}

package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.entity.enums.Role;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User findByPhone(String phone);
    List<User> findBySocietyId(Long societyId);


}

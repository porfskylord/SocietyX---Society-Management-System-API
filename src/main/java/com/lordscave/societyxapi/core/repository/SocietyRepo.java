package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocietyRepo extends JpaRepository<Society, Long> {
    Society findByName(String name);
    boolean existsByName(String name);
}

package com.lordscave.societyxapi.core.repository;

import com.lordscave.societyxapi.core.entity.Flat;
import com.lordscave.societyxapi.core.entity.enums.FlatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlatRepo extends JpaRepository<Flat,Long> {
    Optional<Flat> findByFlatNo(String flatNo);
    List<Flat> findAllBySocietyId(Long societyId);
    List<Flat> findAllByFlatType(FlatType flatType);
    List<Flat> findAllByFloorNo(Integer floorNo);
    List<Flat> findAllByBlock(Character block);
    boolean existsByFlatNo(String flatNo);
}

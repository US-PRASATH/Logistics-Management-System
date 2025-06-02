package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Supplier;
import com.example.demo.model.TransportPlan;

@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long>{
    List<TransportPlan> findByUserId(Long userId);
    
    Optional<TransportPlan> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}
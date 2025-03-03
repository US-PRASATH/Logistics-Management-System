package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TransportPlan;

@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long>{
    
}
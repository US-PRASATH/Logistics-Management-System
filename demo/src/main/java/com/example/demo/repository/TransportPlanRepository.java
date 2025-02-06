package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TransportPlan;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long>{
    
}
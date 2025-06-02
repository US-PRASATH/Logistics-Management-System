package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Supplier;
import com.example.demo.model.Transporter;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
    List<Transporter> findByUserId(Long userId);
    
    Optional<Transporter> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}
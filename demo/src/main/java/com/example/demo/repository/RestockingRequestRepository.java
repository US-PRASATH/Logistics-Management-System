package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RestockingRequest;
import com.example.demo.model.Supplier;

@Repository
public interface RestockingRequestRepository extends JpaRepository<RestockingRequest, Long> {
    List<RestockingRequest> findByUserId(Long userId);
    
    Optional<RestockingRequest> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RestockingRequest;

@Repository
public interface RestockingRequestRepository extends JpaRepository<RestockingRequest, Long> {
}
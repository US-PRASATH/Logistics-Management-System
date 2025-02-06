package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Transporter;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}
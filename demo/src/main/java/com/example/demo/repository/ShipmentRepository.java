package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>{

    public Optional<Shipment> findByTrackingNumber(String trackingNumber);

    public Object findByTransportPlanId(Long id);

    public void deleteByTransportPlanId(Long transportPlanId);
    
}
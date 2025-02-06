package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Shipment;
import com.example.demo.model.TransportPlan;
import com.example.demo.repository.ShipmentRepository;
import com.example.demo.repository.TransportPlanRepository;

@Service
public class ShipmentService{
    @Autowired
    ShipmentRepository repo;

    @Autowired
    TransportPlanRepository transportPlanRepo;

    public List<Shipment> getAllShipments(){
        return repo.findAll();
    }

    public Optional<Shipment> getShipmentById(Long id){
        return repo.findById(id);
    }

    public void createShipment(Shipment shipment){
        if (shipment.getTransportPlan().getId() != null) {
            Optional<TransportPlan> optionalTransportPlan = transportPlanRepo.findById(shipment.getTransportPlan().getId());
            if(optionalTransportPlan.isPresent()){
                TransportPlan existingTransportPlan = optionalTransportPlan.get();
                shipment.setTransportPlan(existingTransportPlan);
            }
    }
        repo.save(shipment);
    }

    public void updateShipment(Long id, Shipment shipment){
        Optional<Shipment> optionalShipment = repo.findById(id);
        if(optionalShipment.isPresent()){
            Shipment existingShipment = optionalShipment.get();
            if (shipment.getTrackingNumber() != null) {
                existingShipment.setTrackingNumber(shipment.getTrackingNumber());
            }
            if (shipment.getOrigin() != null) {
                existingShipment.setOrigin(shipment.getOrigin());
            }
            if (shipment.getDestination() != null) {
                existingShipment.setDestination(shipment.getDestination());
            }
            if (shipment.getEstimatedDeliveryDate() != null) {
                existingShipment.setEstimatedDeliveryDate(shipment.getEstimatedDeliveryDate());
            }
            if (shipment.getTransportPlan().getId() != null) {
                Optional<TransportPlan> optionalTransportPlan = transportPlanRepo.findById(shipment.getTransportPlan().getId());
                if(optionalTransportPlan.isPresent()){
                    TransportPlan existingTransportPlan = optionalTransportPlan.get();
                    existingShipment.setTransportPlan(existingTransportPlan);
                }
        }
            // Add more fields as needed
            repo.save(existingShipment);
        }   
    }

    public void deleteShipment(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}
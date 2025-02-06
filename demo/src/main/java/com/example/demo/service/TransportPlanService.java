package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.TransportPlan;
import com.example.demo.model.Transporter;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransportPlanRepository;
import com.example.demo.repository.TransporterRepository;

@Service
public class TransportPlanService{
    @Autowired
    TransportPlanRepository repo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    TransporterRepository transporterRepo;

    public List<TransportPlan> getAllTransportPlans(){
        return repo.findAll();
    }

    public Optional<TransportPlan> getTransportPlanById(Long id){
        return repo.findById(id);
    }

    public void createTransportPlan(TransportPlan transportPlan){
        if (transportPlan.getOrder().getId() != null) {
            Optional<Order> optionalOrder = orderRepo.findById(transportPlan.getOrder().getId());
            if(optionalOrder.isPresent()){
                Order existingOrder = optionalOrder.get();
                transportPlan.setOrder(existingOrder);
            }
    }
        if (transportPlan.getTransporter().getId() != null) {
            Optional<Transporter> optionalTransporter = transporterRepo.findById(transportPlan.getTransporter().getId());
            if(optionalTransporter.isPresent()){
                Transporter existingTransporter = optionalTransporter.get();
                transportPlan.setTransporter(existingTransporter);
            }
    }
        repo.save(transportPlan);
    }

    public void updateTransportPlan(Long id, TransportPlan transportPlan){
        Optional<TransportPlan> optionalTransportPlan = repo.findById(id);
        if (optionalTransportPlan.isPresent()) {
            TransportPlan existingTransportPlan = optionalTransportPlan.get();
            if (transportPlan.getRoute() != null) {
                existingTransportPlan.setRoute(transportPlan.getRoute());
            }
            if (transportPlan.getCarrier() != null) {
                existingTransportPlan.setCarrier(transportPlan.getCarrier());
            }
            if (transportPlan.getLoadCapacity() != null) {
                existingTransportPlan.setLoadCapacity(transportPlan.getLoadCapacity());
            }
            if (transportPlan.getSchedule() != null) {
                existingTransportPlan.setSchedule(transportPlan.getSchedule());
            }
            // if (transportPlan.getOrder() != null) {
            //     existingTransportPlan.setOrder(transportPlan.getOrder());
            // }
            if (transportPlan.getOrder().getId() != null) {
                Optional<Order> optionalOrder = orderRepo.findById(transportPlan.getOrder().getId());
                if(optionalOrder.isPresent()){
                    Order existingOrder = optionalOrder.get();
                    existingTransportPlan.setOrder(existingOrder);
                }
        }
        if (existingTransportPlan.getTransporter().getId() != null) {
            Optional<Transporter> optionalTransporter = transporterRepo.findById(existingTransportPlan.getTransporter().getId());
            if(optionalTransporter.isPresent()){
                Transporter existingTransporter = optionalTransporter.get();
                existingTransportPlan.setTransporter(existingTransporter);
            }
    }
            // Add more fields as needed
            repo.save(existingTransportPlan);
        }
    }

    public void deleteTransportPlan(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }


}
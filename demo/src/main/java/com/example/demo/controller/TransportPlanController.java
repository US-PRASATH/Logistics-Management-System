package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TransportPlan;
import com.example.demo.service.TransportPlanService;





@RestController
@CrossOrigin
class TransportPlanController{
    @Autowired
    TransportPlanService service;
    // @GetMapping("/")
    // public String getHello() {
    //     return new String("Hello world");
    // }
    
    @GetMapping("/api/transport-plans")
    public List<TransportPlan> getTransportPlans() {
        return service.getAllTransportPlans();
    }

    @PostMapping("/api/transport-plans")
    public void createTransportPlan(@RequestBody TransportPlan data) {
        //TODO: process POST request
        service.createTransportPlan(data);
        //return entity;
    }

    @PutMapping("/api/transport-plans/{id}")
    public void updateTransportPlan(@PathVariable Long id, @RequestBody TransportPlan data) {
        //TODO: process PUT request
        service.updateTransportPlan(id, data);
        // return entity;
    }

    @DeleteMapping("/api/transport-plans/{id}")
    public void deleteTransportPlan(@PathVariable Long id){
        service.deleteTransportPlan(id);
    }
    
}
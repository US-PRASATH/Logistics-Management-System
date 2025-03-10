package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TransportPlan;
import com.example.demo.service.TransportPlanService;

@RestController
@CrossOrigin
@RequestMapping("/api/transport-plans")
public class TransportPlanController {

    @Autowired
    private TransportPlanService transportPlanService;

    @GetMapping
    public ResponseEntity<List<TransportPlan>> getAllTransportPlans() {
        return ResponseEntity.ok(transportPlanService.getAllTransportPlans());
    }

    @PostMapping
    public ResponseEntity<Void> createTransportPlan(@RequestBody TransportPlan transportPlan) {
        transportPlanService.createTransportPlan(transportPlan);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransportPlan(@PathVariable Long id, @RequestBody TransportPlan transportPlan) {
        transportPlanService.updateTransportPlan(id, transportPlan);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransportPlan(@PathVariable Long id) {
        transportPlanService.deleteTransportPlan(id);
        return ResponseEntity.noContent().build();
    }
}
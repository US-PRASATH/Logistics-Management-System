package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.model.Transporter;
import com.example.demo.service.TransporterService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/transporters")
public class TransporterController {
    private final TransporterService service;

    @Autowired
    public TransporterController(TransporterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Transporter>> getTransporters() {
        return ResponseEntity.ok(service.getAllTransporters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transporter> getTransporterById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTransporterById(id));
    }

    @PostMapping
    public ResponseEntity<Transporter> createTransporter(@RequestBody Transporter data) {
        return ResponseEntity.ok(service.createTransporter(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transporter> updateTransporter(@PathVariable Long id, @RequestBody Transporter data) {
        return ResponseEntity.ok(service.updateTransporter(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransporter(@PathVariable Long id) {
        service.deleteTransporter(id);
        return ResponseEntity.noContent().build();
    }
}
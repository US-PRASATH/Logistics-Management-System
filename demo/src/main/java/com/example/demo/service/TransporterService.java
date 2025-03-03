package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Transporter;
import com.example.demo.repository.TransporterRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransporterService {
    private final TransporterRepository transporterRepo;

    @Autowired
    public TransporterService(TransporterRepository transporterRepo) {
        this.transporterRepo = transporterRepo;
    }

    public List<Transporter> getAllTransporters() {
        return transporterRepo.findAll();
    }

    public Transporter getTransporterById(Long id) {
        return transporterRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Transporter not found with id: " + id));
    }

    public Transporter createTransporter(Transporter transporter) {
        return transporterRepo.save(transporter);
    }

    public Transporter updateTransporter(Long id, Transporter transporterDetails) {
        Transporter existingTransporter = getTransporterById(id);
        
        existingTransporter.setName(transporterDetails.getName() != null ? 
            transporterDetails.getName() : existingTransporter.getName());
        existingTransporter.setContactInfo(transporterDetails.getContactInfo() != null ? 
            transporterDetails.getContactInfo() : existingTransporter.getContactInfo());
        existingTransporter.setTransporterType(transporterDetails.getTransporterType() != null ? 
            transporterDetails.getTransporterType() : existingTransporter.getTransporterType());

        return transporterRepo.save(existingTransporter);
    }

    public void deleteTransporter(Long id) {
        Transporter transporter = getTransporterById(id);
        transporterRepo.delete(transporter);
    }
}
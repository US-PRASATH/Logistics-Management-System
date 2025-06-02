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

      @Autowired
    private TenantService tenantService;

    public List<Transporter> getAllTransporters() {
        return transporterRepo.findByUserId(tenantService.getCurrentUserId());
    }

    public Transporter getTransporterById(Long id) {
        return transporterRepo.findByIdAndUserId(id, tenantService.getCurrentUserId())
            .orElseThrow(() -> new EntityNotFoundException("Transporter not found with id: " + id));
    }

    public Transporter createTransporter(Transporter transporter) {
        transporter.setUser(tenantService.getCurrentUser());
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

    // public void deleteTransporter(Long id) {
    //     Transporter transporter = getTransporterById(id);
    //     transporterRepo.delete(transporter);
    // }

    public void deleteTransporter(Long id) {
    Long userId = tenantService.getCurrentUserId();
    
    // Check ownership
    transporterRepo.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new EntityNotFoundException("Transporter not found with id: " + id));
    
    // Safe deletion using userId
    transporterRepo.deleteByIdAndUserId(id, userId);
}

}
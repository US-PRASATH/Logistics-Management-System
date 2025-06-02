package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository repo;

      @Autowired
    private TenantService tenantService;

    public List<Supplier> getAllSuppliers() {
        return repo.findByUserId(tenantService.getCurrentUserId());
    }

    public Supplier getSupplierById(Long id) {
        return repo.findByIdAndUserId(id, tenantService.getCurrentUserId())
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));
    }

    public Supplier createSupplier(Supplier supplier) {
        supplier.setUser(tenantService.getCurrentUser());
        return repo.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier existingSupplier = getSupplierById(id);
        
        existingSupplier.setName(supplierDetails.getName() != null ? 
            supplierDetails.getName() : existingSupplier.getName());
        existingSupplier.setContactInfo(supplierDetails.getContactInfo() != null ? 
            supplierDetails.getContactInfo() : existingSupplier.getContactInfo());
        existingSupplier.setAddress(supplierDetails.getAddress() != null ? 
            supplierDetails.getAddress() : existingSupplier.getAddress());
        existingSupplier.setPerformanceRating(supplierDetails.getPerformanceRating() != null ? 
            supplierDetails.getPerformanceRating() : existingSupplier.getPerformanceRating());

        return repo.save(existingSupplier);
    }

    // public void deleteSupplier(Long id) {
    //     Supplier supplier = getSupplierById(id);
    //     repo.delete(supplier);
    // }

    public void deleteSupplier(Long id) {
    Long userId = tenantService.getCurrentUserId();

    // Ensure the supplier belongs to the user
    repo.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));

    // Delete in a tenant-safe way
    repo.deleteByIdAndUserId(id, userId);
}

}
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

    public List<Supplier> getAllSuppliers() {
        return repo.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));
    }

    public Supplier createSupplier(Supplier supplier) {
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

    public void deleteSupplier(Long id) {
        Supplier supplier = getSupplierById(id);
        repo.delete(supplier);
    }
}
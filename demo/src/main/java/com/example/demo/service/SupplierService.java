package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;

@Service
public class SupplierService{
    @Autowired
    SupplierRepository repo;

    public List<Supplier> getAllSuppliers(){
        return repo.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id){
        return repo.findById(id);
    }

    public void createSupplier(Supplier supplier){
        repo.save(supplier);
    }

    public void updateSupplier(Long id, Supplier supplier){
        Optional<Supplier> optionalSupplier = repo.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier existingSupplier = optionalSupplier.get();
            if (supplier.getName() != null) {
                existingSupplier.setName(supplier.getName());
            }
            if (supplier.getContactInfo() != null) {
                existingSupplier.setContactInfo(supplier.getContactInfo());
            }
            if (supplier.getAddress() != null) {
                existingSupplier.setAddress(supplier.getAddress());
            }
            if (supplier.getPerformanceRating() != null) {
                existingSupplier.setPerformanceRating(supplier.getPerformanceRating());
            }
            // Add more fields as needed
            repo.save(existingSupplier);
        }
    }

    public void deleteSupplier(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }


}
package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Transporter;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransporterRepository;

@Service
public class TransporterService{
    @Autowired
    TransporterRepository repo;

    @Autowired
    ProductRepository productRepo;
    


    public List<Transporter> getAllTransporters(){
        return repo.findAll();
    }

    public Transporter getTransporterById(Long id){
        Optional<Transporter> optionalTransporter = repo.findById(id);
        return optionalTransporter.orElse(null); // Return the order if present, otherwise return null
    }

    public void createTransporter(Transporter order){
        // if (order.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(order.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         order.setProduct(existingProduct);
        //     }
        // }
        repo.save(order);
    }

    public void updateTransporter(Long id, Transporter data){
        Optional<Transporter> optionalTransporter = repo.findById(id);
    if (optionalTransporter.isPresent()) {
        Transporter existingTransporter = optionalTransporter.get();
        // Update fields of the existing order with the new order data
        if (data.getName() != null) {
            existingTransporter.setName(data.getName());
        }
        if (data.getContactInfo() != null) {
            existingTransporter.setContactInfo(data.getContactInfo());
        }
        if (data.getTransporterType() != null) {
            existingTransporter.setTransporterType(data.getTransporterType());
        }
        // if (data.getStatus() != null) {
        //     existingTransporter.setStatus(data.getStatus());
        // }
        // if (data.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(data.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         existingTransporter.setProduct(existingProduct);
        //     }
        // }
        // Add more fields as needed
        repo.save(existingTransporter);
    } else {
        throw new RuntimeException("Transporter not found with id: " + id);
    }
    }

    public void deleteTransporter(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Transporter not found with id: " + id);
        }
    }
}
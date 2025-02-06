package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Warehouse;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WarehouseRepository;

@Service
public class WarehouseManagementService{
    @Autowired
    WarehouseRepository repo;

    @Autowired
    ProductRepository productRepo;


    public List<Warehouse> getAllWarehouses(){
        return repo.findAll();
    }

    public Warehouse getWarehouseById(Long id){
        Optional<Warehouse> optionalWarehouse = repo.findById(id);
        return optionalWarehouse.orElse(null); // Return the order if present, otherwise return null
    }

    public void createWarehouse(Warehouse order){
        // if (order.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(order.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         order.setProduct(existingProduct);
        //     }
        // }
        repo.save(order);
    }

    public void updateWarehouse(Long id, Warehouse data){
        Optional<Warehouse> optionalWarehouse = repo.findById(id);
    if (optionalWarehouse.isPresent()) {
        Warehouse existingWarehouse = optionalWarehouse.get();
        // Update fields of the existing order with the new order data
        if (data.getName() != null) {
            existingWarehouse.setName(data.getName());
        }
        if (data.getLocation() != null) {
            existingWarehouse.setLocation(data.getLocation());
        }
        if (data.getCapacity() != null) {
            existingWarehouse.setCapacity(data.getCapacity());
        }
        // if (data.getStatus() != null) {
        //     existingWarehouse.setStatus(data.getStatus());
        // }
        // if (data.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(data.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         existingWarehouse.setProduct(existingProduct);
        //     }
        // }
        // Add more fields as needed
        repo.save(existingWarehouse);
    } else {
        throw new RuntimeException("Warehouse not found with id: " + id);
    }
    }

    public void deleteWarehouse(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Warehouse not found with id: " + id);
        }
    }
}
package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.RestockingRequest;
import com.example.demo.model.Supplier;
import com.example.demo.model.Warehouse;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RestockingRequestRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.WarehouseRepository;

@Service
public class RestockingRequestService{
    @Autowired
    RestockingRequestRepository repo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    WarehouseRepository warehouseRepo;

    @Autowired
    SupplierRepository supplierRepo;


    public List<RestockingRequest> getAllRestockingRequests(){
        return repo.findAll();
    }

    public RestockingRequest getRestockingRequestById(Long id){
        Optional<RestockingRequest> optionalRestockingRequest = repo.findById(id);
        return optionalRestockingRequest.orElse(null); // Return the order if present, otherwise return null
    }

    public void createRestockingRequest(RestockingRequest order){
        if (order.getWarehouse().getId() != null) {
            Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(order.getWarehouse().getId());
            if(optionalWarehouse.isPresent()){
                Warehouse existingWarehouse = optionalWarehouse.get();
                order.setWarehouse(existingWarehouse);
            }
        }
        if (order.getProduct().getId() != null) {
            Optional<Product> optionalProduct = productRepo.findById(order.getProduct().getId());
            if(optionalProduct.isPresent()){
                Product existingProduct = optionalProduct.get();
                order.setProduct(existingProduct);
            }
        }
        if (order.getSupplier().getId() != null) {
            Optional<Supplier> optionalSupplier = supplierRepo.findById(order.getSupplier().getId());
            if(optionalSupplier.isPresent()){
                Supplier existingSupplier = optionalSupplier.get();
                order.setSupplier(existingSupplier);
            }
        }
        repo.save(order);
    }

    public void updateRestockingRequest(Long id, RestockingRequest data){
        Optional<RestockingRequest> optionalRestockingRequest = repo.findById(id);
    if (optionalRestockingRequest.isPresent()) {
        RestockingRequest existingRestockingRequest = optionalRestockingRequest.get();
        // Update fields of the existing order with the new order data
        // if (data.getCustomerName() != null) {
        //     existingRestockingRequest.setCustomerName(data.getCustomerName());
        // }
        // if (data.getProduct() != null) {
        //     existingRestockingRequest.setProduct(data.getProduct());
        // }
        if (existingRestockingRequest.getWarehouse().getId() != null) {
            Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(existingRestockingRequest.getWarehouse().getId());
            if(optionalWarehouse.isPresent()){
                Warehouse existingWarehouse = optionalWarehouse.get();
                existingRestockingRequest.setWarehouse(existingWarehouse);
            }
        }
        if (existingRestockingRequest.getProduct().getId() != null) {
            Optional<Product> optionalProduct = productRepo.findById(existingRestockingRequest.getProduct().getId());
            if(optionalProduct.isPresent()){
                Product existingProduct = optionalProduct.get();
                existingRestockingRequest.setProduct(existingProduct);
            }
        }
        if (existingRestockingRequest.getSupplier().getId() != null) {
            Optional<Supplier> optionalSupplier = supplierRepo.findById(existingRestockingRequest.getSupplier().getId());
            if(optionalSupplier.isPresent()){
                Supplier existingSupplier = optionalSupplier.get();
                existingRestockingRequest.setSupplier(existingSupplier);
            }
        }
        if (data.getQuantity() != null) {
            existingRestockingRequest.setQuantity(data.getQuantity());
        }
        if (data.getStatus() != null) {
            existingRestockingRequest.setStatus(data.getStatus());
        }
        // if (data.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(data.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         existingRestockingRequest.setProduct(existingProduct);
        //     }
        // }
        // Add more fields as needed
        repo.save(existingRestockingRequest);
    } else {
        throw new RuntimeException("RestockingRequest not found with id: " + id);
    }
    }

    public void deleteRestockingRequest(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("RestockingRequest not found with id: " + id);
        }
    }
}
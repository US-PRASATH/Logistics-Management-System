package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseItem;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WarehouseItemRepository;
import com.example.demo.repository.WarehouseRepository;

@Service
public class WarehouseService{
    @Autowired
    WarehouseItemRepository repo;

    @Autowired
    WarehouseRepository warehouseRepo;

    @Autowired
    ProductRepository productRepo;

    public List<WarehouseItem> getAllWarehouseItems(){
        return repo.findAll();
    }

    public Optional<WarehouseItem> getWarehouseItemById(Long id){
        return repo.findById(id);
    }

    public void createWarehouseItem(WarehouseItem warehouseItem){
        if (warehouseItem.getWarehouse().getId() != null) {
            Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(warehouseItem.getWarehouse().getId());
            if(optionalWarehouse.isPresent()){
                Warehouse existingWarehouse = optionalWarehouse.get();
                warehouseItem.setWarehouse(existingWarehouse);
            }
        }
        if (warehouseItem.getProduct().getId() != null) {
            Optional<Product> optionalProduct = productRepo.findById(warehouseItem.getProduct().getId());
            if(optionalProduct.isPresent()){
                Product existingProduct = optionalProduct.get();
                warehouseItem.setProduct(existingProduct);
            }
        }
        repo.save(warehouseItem);
    }

    public void updateWarehouseItem(Long id, WarehouseItem warehouseItem){
        Optional<WarehouseItem> optionalWarehouseItem = repo.findById(id);
        if (optionalWarehouseItem.isPresent()) {
            WarehouseItem existingWarehouseItem = optionalWarehouseItem.get();
            if (warehouseItem.getItemName() != null) {
                existingWarehouseItem.setItemName(warehouseItem.getItemName());
            }
            if (warehouseItem.getCategory() != null) {
                existingWarehouseItem.setCategory(warehouseItem.getCategory());
            }
            if (warehouseItem.getQuantity() != null) {
                existingWarehouseItem.setQuantity(warehouseItem.getQuantity());
            }
            if (warehouseItem.getStorageLocation() != null) {
                existingWarehouseItem.setStorageLocation(warehouseItem.getStorageLocation());
            }
            if (existingWarehouseItem.getWarehouse().getId() != null) {
                Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(existingWarehouseItem.getWarehouse().getId());
                if(optionalWarehouse.isPresent()){
                    Warehouse existingWarehouse = optionalWarehouse.get();
                    existingWarehouseItem.setWarehouse(existingWarehouse);
                }
            }
            if (existingWarehouseItem.getProduct().getId() != null) {
                Optional<Product> optionalProduct = productRepo.findById(existingWarehouseItem.getProduct().getId());
                if(optionalProduct.isPresent()){
                    Product existingProduct = optionalProduct.get();
                    existingWarehouseItem.setProduct(existingProduct);
                }
            }
            // Add more fields as needed
            repo.save(existingWarehouseItem);
        }
    }

    public void deleteWarehouseItem(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}
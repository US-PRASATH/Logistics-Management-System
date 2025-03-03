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

import jakarta.transaction.Transactional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseItemRepository warehouseItemRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private ProductRepository productRepo;

    public List<WarehouseItem> getAllWarehouseItems() {
        return warehouseItemRepo.findAll();
    }

    public WarehouseItem getWarehouseItemById(Long id) {
        return warehouseItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("WarehouseItem not found with ID: " + id));
    }

    @Transactional
    public void createWarehouseItem(WarehouseItem warehouseItem) {
        // Validate and set Warehouse
        if (warehouseItem.getWarehouse() == null || warehouseItem.getWarehouse().getId() == null) {
            throw new IllegalArgumentException("Warehouse ID is mandatory");
        }
        Warehouse warehouse = warehouseRepo.findById(warehouseItem.getWarehouse().getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + warehouseItem.getWarehouse().getId()));
        warehouseItem.setWarehouse(warehouse);

        // Validate and set Product
        if (warehouseItem.getProduct() == null || warehouseItem.getProduct().getId() == null) {
            throw new IllegalArgumentException("Product ID is mandatory");
        }
        Product product = productRepo.findById(warehouseItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + warehouseItem.getProduct().getId()));
        warehouseItem.setProduct(product);
        warehouseItem.getWarehouse().setRemainingCapacity(warehouseItem.getWarehouse().getRemainingCapacity() - warehouseItem.getQuantity());
        warehouseItemRepo.save(warehouseItem);
    }

    @Transactional
    public void updateWarehouseItem(Long id, WarehouseItem warehouseItem) {
        WarehouseItem existingWarehouseItem = warehouseItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("WarehouseItem not found with ID: " + id));

        // Update fields if provided
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

        // Update Warehouse if provided
        if (warehouseItem.getWarehouse() != null && warehouseItem.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepo.findById(warehouseItem.getWarehouse().getId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + warehouseItem.getWarehouse().getId()));
            existingWarehouseItem.setWarehouse(warehouse);
        }

        // Update Product if provided
        if (warehouseItem.getProduct() != null && warehouseItem.getProduct().getId() != null) {
            Product product = productRepo.findById(warehouseItem.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + warehouseItem.getProduct().getId()));
            existingWarehouseItem.setProduct(product);
        }

        warehouseItemRepo.save(existingWarehouseItem);
    }

    @Transactional
    public void deleteWarehouseItem(Long id) {
        if (!warehouseItemRepo.existsById(id)) {
            throw new RuntimeException("WarehouseItem not found with ID: " + id);
        }
        
        Optional<WarehouseItem> optionalWarehouseItem = warehouseItemRepo.findById(id);
        if(optionalWarehouseItem.isPresent()){
            WarehouseItem warehouseItem = optionalWarehouseItem.get();
            warehouseItem.getWarehouse().setRemainingCapacity(warehouseItem.getWarehouse().getRemainingCapacity() + warehouseItem.getQuantity());
            warehouseItemRepo.save(warehouseItem);
        }
        
        warehouseItemRepo.deleteById(id);
    }
}
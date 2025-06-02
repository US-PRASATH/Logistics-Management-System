package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Warehouse;
import com.example.demo.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

@Service
public class WarehouseManagementService {

    @Autowired
    private WarehouseRepository warehouseRepo;

      @Autowired
    private TenantService tenantService;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepo.findByUserId(tenantService.getCurrentUserId());
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepo.findByIdAndUserId(id, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + id));
    }

    @Transactional
    public void createWarehouse(Warehouse warehouse) {
        // Validate the warehouse data
        if (warehouse.getName() == null || warehouse.getLocation() == null || warehouse.getCapacity() == null) {
            throw new IllegalArgumentException("Name, location, and capacity are mandatory");
        }
        warehouse.setUser(tenantService.getCurrentUser());
        warehouseRepo.save(warehouse);
    }

    @Transactional
    public void updateWarehouse(Long id, Warehouse data) {
        Warehouse existingWarehouse = warehouseRepo.findByIdAndUserId(id, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + id));

        // Update fields if provided
        if (data.getName() != null) {
            existingWarehouse.setName(data.getName());
        }
        if (data.getLocation() != null) {
            existingWarehouse.setLocation(data.getLocation());
        }
        if (data.getCapacity() != null) {
            existingWarehouse.setCapacity(data.getCapacity());
        }

        warehouseRepo.save(existingWarehouse);
    }

    // @Transactional
    // public void deleteWarehouse(Long id) {
    //     if (!warehouseRepo.existsById(id)) {
    //         throw new RuntimeException("Warehouse not found with ID: " + id);
    //     }
    //     warehouseRepo.deleteById(id);
    // }

    @Transactional
public void deleteWarehouse(Long id) {
    Long currentUserId = tenantService.getCurrentUserId();

    // Only retrieve the warehouse if it belongs to the current user
    Optional<Warehouse> optionalWarehouse = warehouseRepo.findByIdAndUserId(id, currentUserId);

    if (optionalWarehouse.isEmpty()) {
        throw new RuntimeException("Warehouse not found or unauthorized access with ID: " + id);
    }

    warehouseRepo.deleteByIdAndUserId(id, currentUserId);
}

}
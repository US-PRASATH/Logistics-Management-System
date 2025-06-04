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

      @Autowired
    private TenantService tenantService;

    public List<WarehouseItem> getAllWarehouseItems(Long warehouseId) {
        return warehouseItemRepo.findByWarehouseIdAndUserId(warehouseId, tenantService.getCurrentUserId());
    }

    public WarehouseItem getWarehouseItemById(Long warehouseId, Long id) {
        return warehouseItemRepo.findByWarehouseIdAndIdAndUserId(warehouseId, id, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("WarehouseItem not found with ID: " + id));
    }

//     @Transactional
//     public void createWarehouseItem(Long warehouseId, WarehouseItem warehouseItem) {
//         // Validate and set Warehouse
//         if (warehouseItem.getWarehouse() == null || warehouseItem.getWarehouse().getId() == null) {
//             throw new IllegalArgumentException("Warehouse ID is mandatory");
//         }
//         Warehouse warehouse = warehouseRepo.findByIdAndUserId(warehouseItem.getWarehouse().getId(), tenantService.getCurrentUserId())
//                 .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + warehouseItem.getWarehouse().getId()));
//         warehouseItem.setWarehouse(warehouse);

//         // Validate and set Product
//         if (warehouseItem.getProduct() == null || warehouseItem.getProduct().getId() == null) {
//             throw new IllegalArgumentException("Product ID is mandatory");
//         }
//         Product product = productRepo.findByIdAndUserId(warehouseItem.getProduct().getId(), tenantService.getCurrentUserId())
//                 .orElseThrow(() -> new RuntimeException("Product not found with ID: " + warehouseItem.getProduct().getId()));
//                 if (warehouse.getRemainingCapacity() < warehouseItem.getQuantity()) {
//     throw new RuntimeException("Not enough space in the warehouse.");
// }

//         warehouseItem.setProduct(product);
//         warehouseItem.setUser(tenantService.getCurrentUser());
//         warehouseItem.getWarehouse().setRemainingCapacity(warehouseItem.getWarehouse().getRemainingCapacity() - warehouseItem.getQuantity());
//         warehouseItemRepo.save(warehouseItem);
//     }

@Transactional
public void createWarehouseItem(Long warehouseId, WarehouseItem warehouseItem) {
    // Force use of path variable ID over any ID from body
    Warehouse warehouse = warehouseRepo.findByIdAndUserId(warehouseId, tenantService.getCurrentUserId())
            .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + warehouseId));
    warehouseItem.setWarehouse(warehouse);

    // Validate and set Product
    if (warehouseItem.getProduct() == null || warehouseItem.getProduct().getId() == null) {
        throw new IllegalArgumentException("Product ID is mandatory");
    }

    Product product = productRepo.findByIdAndUserId(warehouseItem.getProduct().getId(), tenantService.getCurrentUserId())
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + warehouseItem.getProduct().getId()));

    // Check capacity
    if (warehouse.getRemainingCapacity() < warehouseItem.getQuantity()) {
        throw new RuntimeException("Not enough space in the warehouse.");
    }

    // Update warehouse capacity
    warehouse.setRemainingCapacity(warehouse.getRemainingCapacity() - warehouseItem.getQuantity());
    warehouseRepo.save(warehouse); // Needed if cascade is not set

    // Final associations
    warehouseItem.setProduct(product);
    warehouseItem.setUser(tenantService.getCurrentUser());

    warehouseItemRepo.save(warehouseItem);
}


    @Transactional
    public void updateWarehouseItem(Long warehouseId, Long id, WarehouseItem warehouseItem) {
        WarehouseItem existingWarehouseItem = warehouseItemRepo.findByWarehouseIdAndIdAndUserId(warehouseId, id, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("WarehouseItem not found with ID: " + id));

        // Update fields if provided
        if (warehouseItem.getItemName() != null) {
            existingWarehouseItem.setItemName(warehouseItem.getItemName());
        }
        if (warehouseItem.getCategory() != null) {
            existingWarehouseItem.setCategory(warehouseItem.getCategory());
        }
        // if (warehouseItem.getQuantity() != null) {
        //     existingWarehouseItem.setQuantity(warehouseItem.getQuantity());
        // }
        if (warehouseItem.getQuantity() != null) {
    int oldQuantity = existingWarehouseItem.getQuantity();
    int newQuantity = warehouseItem.getQuantity();
    int diff = newQuantity - oldQuantity;

    
    // Adjust remaining capacity
    Warehouse warehouse = existingWarehouseItem.getWarehouse();
    if (diff > 0 && warehouse.getRemainingCapacity() < diff) {
            throw new IllegalArgumentException("Not enough capacity in the warehouse to increase quantity");
        }
    warehouse.setRemainingCapacity(warehouse.getRemainingCapacity() - diff);

    existingWarehouseItem.setQuantity(newQuantity);
}
        if (warehouseItem.getStorageLocation() != null) {
            existingWarehouseItem.setStorageLocation(warehouseItem.getStorageLocation());
        }

        // Update Warehouse if provided
        if (warehouseItem.getWarehouse() != null && warehouseItem.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepo.findByIdAndUserId(warehouseItem.getWarehouse().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + warehouseItem.getWarehouse().getId()));
            existingWarehouseItem.setWarehouse(warehouse);
        }

        // Update Product if provided
        if (warehouseItem.getProduct() != null && warehouseItem.getProduct().getId() != null) {
            Product product = productRepo.findByIdAndUserId(warehouseItem.getProduct().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + warehouseItem.getProduct().getId()));
            existingWarehouseItem.setProduct(product);
        }

        warehouseItemRepo.save(existingWarehouseItem);
    }

    // @Transactional
    // public void deleteWarehouseItem(Long id) {


    //     if (!warehouseItemRepo.existsById(id)) {
    //         throw new RuntimeException("WarehouseItem not found with ID: " + id);
    //     }
        
    //     Optional<WarehouseItem> optionalWarehouseItem = warehouseItemRepo.findByIdAndUserId(id, tenantService.getCurrentUserId());
    //     if(optionalWarehouseItem.isPresent()){
    //         WarehouseItem warehouseItem = optionalWarehouseItem.get();
    //         warehouseItem.getWarehouse().setRemainingCapacity(warehouseItem.getWarehouse().getRemainingCapacity() + warehouseItem.getQuantity());
    //         warehouseItemRepo.save(warehouseItem);
    //     }
        
    //     warehouseItemRepo.deleteById(id);
    // }

    @Transactional
public void deleteWarehouseItem(Long warehouseId, Long id) {
    Long currentUserId = tenantService.getCurrentUserId();

    // Strictly fetch only if the item belongs to current user
    Optional<WarehouseItem> optionalWarehouseItem = warehouseItemRepo.findByWarehouseIdAndIdAndUserId(warehouseId, id, currentUserId);

    if (optionalWarehouseItem.isEmpty()) {
        throw new RuntimeException("WarehouseItem not found or unauthorized access with ID: " + id);
    }

    WarehouseItem warehouseItem = optionalWarehouseItem.get();

    // Update warehouse capacity
    warehouseItem.getWarehouse().setRemainingCapacity(
        warehouseItem.getWarehouse().getRemainingCapacity() + warehouseItem.getQuantity()
    );

    // Save updated warehouse item just to persist the capacity update
    // warehouseItemRepo.save(warehouseItem);
    warehouseRepo.save(warehouseItem.getWarehouse());


    // Now safely delete
    warehouseItemRepo.deleteByWarehouseIdAndIdAndUserId(warehouseId, id, currentUserId);
}

}
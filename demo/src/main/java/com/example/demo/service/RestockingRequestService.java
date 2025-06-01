package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.RestockingRequest;
import com.example.demo.model.Supplier;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseItem;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RestockingRequestRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.WarehouseItemRepository;
import com.example.demo.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

@Service
public class RestockingRequestService {

    @Autowired
    private RestockingRequestRepository restockingRequestRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private SupplierRepository supplierRepo;

    @Autowired
    private WarehouseItemRepository warehouseItemRepository;

    public List<RestockingRequest> getAllRestockingRequests() {
        return restockingRequestRepo.findAll();
    }

    @Transactional
    public List<RestockingRequest> getAllRestockingRequestsForSupplier(Long supplierId) {
        // Fetch all restocking requests for products that belong to this supplier
        return restockingRequestRepo.findAll().stream()
                .filter(req -> req.getProduct().getSupplier().getId().equals(supplierId))
                .collect(Collectors.toList());
    }

    public RestockingRequest getRestockingRequestById(Long id) {
        return restockingRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RestockingRequest not found with ID: " + id));
    }

    @Transactional
    public void createRestockingRequest(RestockingRequest restockingRequest) {
        // Validate and set Warehouse
        if (restockingRequest.getWarehouse() == null || restockingRequest.getWarehouse().getId() == null) {
            throw new IllegalArgumentException("Warehouse ID is mandatory");
        }
        Warehouse warehouse = warehouseRepo.findById(restockingRequest.getWarehouse().getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + restockingRequest.getWarehouse().getId()));
        restockingRequest.setWarehouse(warehouse);

        
        // Validate and set Product
        if (restockingRequest.getProduct() == null || restockingRequest.getProduct().getId() == null) {
            throw new IllegalArgumentException("Product ID is mandatory");
        }
        Product product = productRepo.findById(restockingRequest.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + restockingRequest.getProduct().getId()));
        restockingRequest.setProduct(product);

        // Validate and set Supplier
        if (restockingRequest.getProduct().getSupplier() == null || restockingRequest.getProduct().getSupplier().getId() == null) {
            throw new IllegalArgumentException("Supplier ID is mandatory");
        }
        Supplier supplier = supplierRepo.findById(restockingRequest.getProduct().getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + restockingRequest.getProduct().getSupplier().getId()));
        restockingRequest.getProduct().setSupplier(supplier);

        if (restockingRequest.getQuantity() > restockingRequest.getWarehouse().getRemainingCapacity()){
            throw new RuntimeException("Quantity exceeded available Warehouse capacity");
        }

        restockingRequestRepo.save(restockingRequest);
    }

    @Transactional
    public void updateRestockingRequest(Long id, RestockingRequest data) {
        RestockingRequest existingRestockingRequest = restockingRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RestockingRequest not found with ID: " + id));

        // Update fields if provided
        if (data.getQuantity() != null) {
            existingRestockingRequest.setQuantity(data.getQuantity());
        }
        if (data.getStatus() != null) {
            existingRestockingRequest.setStatus(data.getStatus());
        }

        // Update Warehouse if provided
        if (data.getWarehouse() != null && data.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepo.findById(data.getWarehouse().getId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + data.getWarehouse().getId()));
            existingRestockingRequest.setWarehouse(warehouse);
        }

        // Update Product if provided
        if (data.getProduct() != null && data.getProduct().getId() != null) {
            Product product = productRepo.findById(data.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + data.getProduct().getId()));
            existingRestockingRequest.setProduct(product);
        }

        // Update Supplier if provided
        if (data.getProduct().getSupplier() != null && data.getProduct().getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findById(data.getProduct().getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + data.getProduct().getSupplier().getId()));
            existingRestockingRequest.getProduct().setSupplier(supplier);
        }

        restockingRequestRepo.save(existingRestockingRequest);
    }

    @Transactional
    public void deleteRestockingRequest(Long id) {
        if (!restockingRequestRepo.existsById(id)) {
            throw new RuntimeException("RestockingRequest not found with ID: " + id);
        }
        restockingRequestRepo.deleteById(id);
    }


    @Transactional
    public void updateRestockingRequestStatus(Long restockingRequestId, RestockingRequest.Status newStatus) {
        RestockingRequest restockingRequest = restockingRequestRepo.findById(restockingRequestId)
                .orElseThrow(() -> new RuntimeException("RestockingRequest not found"));

        // Prevent changing status after delivery
        if (restockingRequest.getStatus() == RestockingRequest.Status.DELIVERED) {
            throw new RuntimeException("Cannot change status. RestockingRequest is already delivered.");
        }

        // Update the status
        restockingRequest.setStatus(newStatus);
        restockingRequestRepo.save(restockingRequest);

        // If the status is changed to DELIVERED, update warehouse stock
        if (newStatus == RestockingRequest.Status.DELIVERED) {
            WarehouseItem warehouseItem = warehouseItemRepository
                    .findByWarehouseIdAndProductId(restockingRequest.getWarehouse().getId(), restockingRequest.getProduct().getId())
                    // .orElseThrow(() -> new RuntimeException("WarehouseItem not found"));
                    .orElseGet(() -> {
                    // Create a new WarehouseItem if it doesn't exist
                    WarehouseItem newItem = new WarehouseItem();
                    newItem.setWarehouse(restockingRequest.getWarehouse());
                    newItem.setProduct(restockingRequest.getProduct());
                    newItem.setQuantity(0); // initialize with 0 or any business-defined default
                    return newItem;
                });
            // if (warehouseItem.getQuantity() < restockingRequest.getQuantity()) {
            //     throw new RuntimeException("Insufficient stock in warehouse");
            // }

            // Reduce warehouse stock
            warehouseItem.setQuantity(warehouseItem.getQuantity() + restockingRequest.getQuantity());
            restockingRequest.getWarehouse().setRemainingCapacity(restockingRequest.getWarehouse().getRemainingCapacity() - restockingRequest.getQuantity());
            warehouseItemRepository.save(warehouseItem);
        }
    }
}
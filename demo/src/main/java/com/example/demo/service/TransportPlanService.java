package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.TransportPlan;
import com.example.demo.model.Transporter;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransportPlanRepository;
import com.example.demo.repository.TransporterRepository;
import com.example.demo.repository.WarehouseItemRepository;
import com.example.demo.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

// @Service
// public class TransportPlanService {

//     @Autowired
//     private TransportPlanRepository transportPlanRepo;

//     @Autowired
//     private OrderRepository orderRepo;

//     @Autowired
//     private TransporterRepository transporterRepo;

//     @Autowired
//     private ShipmentService shipmentService;

//     public List<TransportPlan> getAllTransportPlans() {
//         return transportPlanRepo.findAll();
//     }

//     public Optional<TransportPlan> getTransportPlanById(Long id) {
//         return transportPlanRepo.findById(id);
//     }

//     @Transactional
//     public void createTransportPlan(TransportPlan transportPlan) {
//         // Validate and set Order
//         if (transportPlan.getOrder() == null || transportPlan.getOrder().getId() == null) {
//             throw new IllegalArgumentException("Order ID is mandatory");
//         }
//         Order order = orderRepo.findById(transportPlan.getOrder().getId())
//                 .orElseThrow(() -> new RuntimeException("Order not found with ID: " + transportPlan.getOrder().getId()));
//         transportPlan.setOrder(order);

//         // Validate and set Transporter
//         if (transportPlan.getTransporter() == null || transportPlan.getTransporter().getId() == null) {
//             throw new IllegalArgumentException("Transporter ID is mandatory");
//         }
//         Transporter transporter = transporterRepo.findById(transportPlan.getTransporter().getId())
//                 .orElseThrow(() -> new RuntimeException("Transporter not found with ID: " + transportPlan.getTransporter().getId()));
//         transportPlan.setTransporter(transporter);

//         transportPlanRepo.save(transportPlan);
//         shipmentService.createShipment(transportPlan);
//     }

//     @Transactional
//     public void updateTransportPlan(Long id, TransportPlan transportPlan) {
//         TransportPlan existingTransportPlan = transportPlanRepo.findById(id)
//                 .orElseThrow(() -> new RuntimeException("TransportPlan not found with ID: " + id));

//         // Update fields if provided
//         // if (transportPlan.getRoute() != null) {
//         //     existingTransportPlan.setRoute(transportPlan.getRoute());
//         // }
//         if (transportPlan.getOriginLocation() != null) {
//             existingTransportPlan.setOriginLocation(transportPlan.getOriginLocation());
//         }
//         if (transportPlan.getDestinationLocation() != null) {
//             existingTransportPlan.setDestinationLocation(transportPlan.getDestinationLocation());
//         }
//         if (transportPlan.getLoadCapacity() != null) {
//             existingTransportPlan.setLoadCapacity(transportPlan.getLoadCapacity());
//         }
//         if (transportPlan.getSchedule() != null) {
//             existingTransportPlan.setSchedule(transportPlan.getSchedule());
//         }

//         // Update Order if provided
//         if (transportPlan.getOrder() != null && transportPlan.getOrder().getId() != null) {
//             Order order = orderRepo.findById(transportPlan.getOrder().getId())
//                     .orElseThrow(() -> new RuntimeException("Order not found with ID: " + transportPlan.getOrder().getId()));
//             existingTransportPlan.setOrder(order);
//         }

//         // Update Transporter if provided
//         if (transportPlan.getTransporter() != null && transportPlan.getTransporter().getId() != null) {
//             Transporter transporter = transporterRepo.findById(transportPlan.getTransporter().getId())
//                     .orElseThrow(() -> new RuntimeException("Transporter not found with ID: " + transportPlan.getTransporter().getId()));
//             existingTransportPlan.setTransporter(transporter);
//         }

//         transportPlanRepo.save(existingTransportPlan);
//     }

//     @Transactional
//     public void deleteTransportPlan(Long id) {
//         if (!transportPlanRepo.existsById(id)) {
//             throw new RuntimeException("TransportPlan not found with ID: " + id);
//         }
//         transportPlanRepo.deleteById(id);
//     }
// }


// TransportPlanService.java
@Service
public class TransportPlanService {
    @Autowired
    private TransportPlanRepository transportPlanRepo;
    
    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired
    private TransporterRepository transporterRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;
    
    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private WarehouseItemRepository warehouseItemRepository;

      @Autowired
    private TenantService tenantService;

    public List<TransportPlan> getAllTransportPlans() {
        return transportPlanRepo.findByUserId(tenantService.getCurrentUserId());
    }

    public Optional<TransportPlan> getTransportPlanById(Long id) {
        return transportPlanRepo.findByIdAndUserId(id, tenantService.getCurrentUserId());
    }

    @Transactional
    public TransportPlan createTransportPlan(TransportPlan transportPlan) {
        // Validate and set Order
        if (transportPlan.getOrder() == null || transportPlan.getOrder().getId() == null) {
            throw new IllegalArgumentException("Order ID is mandatory");
        }
        Order order = orderRepo.findByIdAndUserId(transportPlan.getOrder().getId(), tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + transportPlan.getOrder().getId()));
        transportPlan.setOrder(order);

        // Validate and set Transporter
        if (transportPlan.getTransporter() == null || transportPlan.getTransporter().getId() == null) {
            throw new IllegalArgumentException("Transporter ID is mandatory");
        }
        Transporter transporter = transporterRepo.findByIdAndUserId(transportPlan.getTransporter().getId(), tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Transporter not found with ID: " + transportPlan.getTransporter().getId()));
        transportPlan.setTransporter(transporter);

        if (transportPlan.getWarehouse() == null || transportPlan.getWarehouse().getId() == null) {
            throw new IllegalArgumentException("Warehouse ID is mandatory");
        }
        Warehouse warehouse = warehouseRepo.findByIdAndUserId(transportPlan.getWarehouse().getId(), tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with ID: " + transportPlan.getWarehouse().getId()));
        transportPlan.setWarehouse(warehouse);
        WarehouseItem warehouseItem = warehouseItemRepository.findByWarehouseIdAndProductIdAndUserId(transportPlan.getWarehouse().getId(), transportPlan.getOrder().getProduct().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("WarehouseItem not found"));

            if (warehouseItem.getQuantity() < transportPlan.getOrder().getQuantity()) {
                throw new RuntimeException("Insufficient stock in warehouse");
            }
        // Save transport plan
        transportPlan.setUser(tenantService.getCurrentUser());
        TransportPlan savedPlan = transportPlanRepo.save(transportPlan);
        
        // Create associated shipment
        shipmentService.createShipment(savedPlan);
        
        return savedPlan;
    }

    @Transactional
    public TransportPlan updateTransportPlan(Long id, TransportPlan transportPlan) {
        TransportPlan existingTransportPlan = transportPlanRepo.findByIdAndUserId(id, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("TransportPlan not found with ID: " + id));

        // Update fields if provided
        if (transportPlan.getOriginLocation() != null) {
            existingTransportPlan.setOriginLocation(transportPlan.getOriginLocation());
        }
        if (transportPlan.getDestinationLocation() != null) {
            existingTransportPlan.setDestinationLocation(transportPlan.getDestinationLocation());
        }
        if (transportPlan.getLoadCapacity() != null) {
            existingTransportPlan.setLoadCapacity(transportPlan.getLoadCapacity());
        }
        if (transportPlan.getSchedule() != null) {
            existingTransportPlan.setSchedule(transportPlan.getSchedule());
        }

        // Update Order if provided
        if (transportPlan.getOrder() != null && transportPlan.getOrder().getId() != null) {
            Order order = orderRepo.findByIdAndUserId(transportPlan.getOrder().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + transportPlan.getOrder().getId()));
            existingTransportPlan.setOrder(order);
        }

        // Update Transporter if provided
        if (transportPlan.getTransporter() != null && transportPlan.getTransporter().getId() != null) {
            Transporter transporter = transporterRepo.findByIdAndUserId(transportPlan.getTransporter().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Transporter not found with ID: " + transportPlan.getTransporter().getId()));
            existingTransportPlan.setTransporter(transporter);
        }

        // Save updated transport plan
        TransportPlan updatedPlan = transportPlanRepo.save(existingTransportPlan);
        
        // Update associated shipment
        shipmentService.updateShipmentFromTransportPlan(updatedPlan);
        
        return updatedPlan;
    }

    // @Transactional
    // public void deleteTransportPlan(Long id) {
    //     if (!transportPlanRepo.existsById(id)) {
    //         throw new RuntimeException("TransportPlan not found with ID: " + id);
    //     }
    //     // Delete associated shipment first
    //     shipmentService.deleteShipmentByTransportPlanId(id);
    //     // Then delete transport plan
    //     transportPlanRepo.deleteById(id);
    // }

    @Transactional
public void deleteTransportPlan(Long id) {
    Long currentUserId = tenantService.getCurrentUserId();

    Optional<TransportPlan> optionalTransportPlan = transportPlanRepo.findByIdAndUserId(id, currentUserId);
    if (optionalTransportPlan.isEmpty()) {
        throw new RuntimeException("TransportPlan not found or unauthorized access with ID: " + id);
    }

    // Delete associated shipment(s)
    shipmentService.deleteShipmentByTransportPlanId(id);

    // Delete the transport plan safely
    transportPlanRepo.deleteByIdAndUserId(id, currentUserId);
}

}
package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Shipment;
import com.example.demo.model.TransportPlan;
import com.example.demo.model.WarehouseItem;
import com.example.demo.repository.ShipmentRepository;
import com.example.demo.repository.TransportPlanRepository;
import com.example.demo.repository.WarehouseItemRepository;
import com.example.demo.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

// @Service
// public class ShipmentService {
//     @Autowired
//     private ShipmentRepository shipmentRepository;

//     @Autowired
//     private TransportPlanRepository transportPlanRepository;

//     @Autowired
//     private WarehouseItemRepository warehouseItemRepository;

//     public List<Shipment> getAllShipments() {
//         return shipmentRepository.findAll();
//     }

//     public Optional<Shipment> getShipmentById(Long id) {
//         return shipmentRepository.findById(id);
//     }

//     // public Shipment createShipment(Shipment shipment) {
//     //     if (shipment.getTransportPlan() != null && shipment.getTransportPlan().getId() != null) {
//     //         TransportPlan transportPlan = transportPlanRepository.findById(shipment.getTransportPlan().getId())
//     //                 .orElseThrow(() -> new RuntimeException("TransportPlan not found"));
//     //         shipment.setTransportPlan(transportPlan);
//     //     }
//     //     return shipmentRepository.save(shipment);
//     // }

//     public Shipment createShipment(TransportPlan transportPlan) {
//         Shipment shipment = new Shipment();
//         if (transportPlan != null) {
//             // TransportPlan transportPlan = transportPlanRepository.findById(shipment.getTransportPlan().getId())
//             //         .orElseThrow(() -> new RuntimeException("TransportPlan not found"));
//             shipment.setOrder(transportPlan.getOrder());
//             shipment.setTransporter(transportPlan.getTransporter());
//             shipment.setEstimatedDeliveryDate(transportPlan.getSchedule());
//             shipment.setOriginLocation(transportPlan.getOrigin());
//             shipment.setDestination(transportPlan.getDestination());
//             shipment.setTransportPlan(transportPlan);
//         }
//         return shipmentRepository.save(shipment);
//     }

//     public Shipment updateShipment(Long id, Shipment shipment) {
//         Shipment existingShipment = shipmentRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));

//         if (shipment.getTrackingNumber() != null) {
//             existingShipment.setTrackingNumber(shipment.getTrackingNumber());
//         }
//         if (shipment.getOrigin() != null) {
//             existingShipment.setOriginLocation(shipment.getOrigin());
//         }
//         if (shipment.getDestination() != null) {
//             existingShipment.setDestination(shipment.getDestination());
//         }
//         if (shipment.getEstimatedDeliveryDate() != null) {
//             existingShipment.setEstimatedDeliveryDate(shipment.getEstimatedDeliveryDate());
//         }
//         if (shipment.getStatus() != null) {
//             existingShipment.setStatus(shipment.getStatus());
//         }
//         if (shipment.getTransportPlan() != null && shipment.getTransportPlan().getId() != null) {
//             TransportPlan transportPlan = transportPlanRepository.findById(shipment.getTransportPlan().getId())
//                     .orElseThrow(() -> new RuntimeException("TransportPlan not found"));
//             existingShipment.setTransportPlan(transportPlan);
//         }

//         return shipmentRepository.save(existingShipment);
//     }

//     public void deleteShipment(Long id) {
//         if (!shipmentRepository.existsById(id)) {
//             throw new RuntimeException("Shipment not found with id: " + id);
//         }
//         shipmentRepository.deleteById(id);
//     }


//     @Transactional
//     public void updateShipmentStatus(Long shipmentId, Shipment.Status newStatus) {
//         Shipment shipment = shipmentRepository.findById(shipmentId)
//                 .orElseThrow(() -> new RuntimeException("Shipment not found"));

//         // Prevent changing status after delivery
//         if (shipment.getStatus() == Shipment.Status.DELIVERED) {
//             throw new RuntimeException("Cannot change status. Shipment is already delivered.");
//         }

//         // Update the status
//         shipment.setStatus(newStatus);
//         shipmentRepository.save(shipment);

//         // If the status is changed to DELIVERED, update warehouse stock
//         if (newStatus == Shipment.Status.DELIVERED) {
//             WarehouseItem warehouseItem = warehouseItemRepository
//                     .findByWarehouseIdAndProductId(shipment.getTransportPlan().getWarehouse().getId(), shipment.getOrder().getProduct().getId())
//                     .orElseThrow(() -> new RuntimeException("WarehouseItem not found"));

//             if (warehouseItem.getQuantity() < shipment.getOrder().getQuantity()) {
//                 throw new RuntimeException("Insufficient stock in warehouse");
//             }

//             // Reduce warehouse stock
//             warehouseItem.setQuantity(warehouseItem.getQuantity() - shipment.getOrder().getQuantity());
//             warehouseItemRepository.save(warehouseItem);
//         }
//     }
// }


// ShipmentService.java
@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private TransportPlanRepository transportPlanRepository;

    @Autowired
    private WarehouseItemRepository warehouseItemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

      @Autowired
    private TenantService tenantService;

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findByUserId(tenantService.getCurrentUserId());
    }

    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findByIdAndUserId(id, tenantService.getCurrentUserId());
    }

    public Optional<Shipment> getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumberAndUserId(trackingNumber, tenantService.getCurrentUserId());
    }

    @Transactional
    public Shipment createShipment(TransportPlan transportPlan) {
        Shipment shipment = new Shipment();
        shipment.setTransportPlan(transportPlan);
        shipment.setOrder(transportPlan.getOrder());
        shipment.setTransporter(transportPlan.getTransporter());
        shipment.setOriginLocation(transportPlan.getOriginLocation());
        shipment.setDestinationLocation(transportPlan.getDestinationLocation());
        shipment.setEstimatedDeliveryDate(transportPlan.getSchedule());
        shipment.setStatus(Shipment.Status.IN_TRANSIT);
        shipment.setUser(tenantService.getCurrentUser());
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Shipment updateShipmentFromTransportPlan(TransportPlan transportPlan) {
        // Shipment optionalShipment = shipmentRepository.findByTransportPlanId(transportPlan.getId());
        Shipment existingShipment = ((Shipment) shipmentRepository.findByTransportPlanIdAndUserId(transportPlan.getId(), tenantService.getCurrentUserId()));
                // .orElseThrow(() -> new RuntimeException("Shipment not found for TransportPlan ID: " + transportPlan.getId()));

        existingShipment.setOriginLocation(transportPlan.getOriginLocation());
        existingShipment.setDestinationLocation(transportPlan.getDestinationLocation());
        existingShipment.setEstimatedDeliveryDate(transportPlan.getSchedule());
        existingShipment.setTransporter(transportPlan.getTransporter());
        existingShipment.setOrder(transportPlan.getOrder());

        return shipmentRepository.save(existingShipment);
    }

    @Transactional
    public void deleteShipmentByTransportPlanId(Long transportPlanId) {
        shipmentRepository.deleteByTransportPlanIdAndUserId(transportPlanId, tenantService.getCurrentUserId());
    }

    @Transactional
    public void updateShipmentStatus(Long shipmentId, Shipment.Status newStatus) {
        Shipment shipment = shipmentRepository.findByIdAndUserId(shipmentId, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        if (shipment.getStatus() == Shipment.Status.DELIVERED) {
            throw new RuntimeException("Cannot change status. Shipment is already delivered.");
        }

        shipment.setStatus(newStatus);
        shipmentRepository.save(shipment);

        if (newStatus == Shipment.Status.DELIVERED) {
            updateWarehouseStock(shipment);
        }
    }

    private void updateWarehouseStock(Shipment shipment) {
        // Your existing warehouse stock update logic
        WarehouseItem warehouseItem = warehouseItemRepository
                    .findByWarehouseIdAndProductIdAndUserId(shipment.getTransportPlan().getWarehouse().getId(), shipment.getOrder().getProduct().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("WarehouseItem not found"));

            if (warehouseItem.getQuantity() < shipment.getOrder().getQuantity()) {
                throw new RuntimeException("Insufficient stock in warehouse");
            }

            // Reduce warehouse stock
            warehouseItem.setQuantity(warehouseItem.getQuantity() - shipment.getOrder().getQuantity());
            shipment.getTransportPlan().getWarehouse().setRemainingCapacity(shipment.getTransportPlan().getWarehouse().getRemainingCapacity() + shipment.getOrder().getQuantity());
            warehouseItemRepository.save(warehouseItem);
            shipmentRepository.save(shipment);
            
    }
}

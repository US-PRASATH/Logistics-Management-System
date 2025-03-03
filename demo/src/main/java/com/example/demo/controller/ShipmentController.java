package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Shipment;
import com.example.demo.service.ShipmentService;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<List<Shipment>> getShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
    //     return shipmentService.getShipmentById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<Shipment> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return shipmentService.getShipmentByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public void updateShipment(@PathVariable Long id, @RequestBody Shipment.Status status) {
        shipmentService.updateShipmentStatus(id, status);
    }

    // @PostMapping
    // public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
    //     return ResponseEntity.ok(shipmentService.createShipment(shipment));
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Shipment> updateShipment(@PathVariable Long id, @RequestBody Shipment shipment) {
    //     return ResponseEntity.ok(shipmentService.updateShipment(id, shipment));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
    //     shipmentService.deleteShipment(id);
    //     return ResponseEntity.noContent().build();
    // }
}

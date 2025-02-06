package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Shipment;
import com.example.demo.service.ShipmentService;





@RestController
@CrossOrigin
class ShipmentController{
    @Autowired
    ShipmentService service;

    // @GetMapping("/")
    // public String getHello() {
    //     return new String("Hello world");
    // }
    
    @GetMapping("/api/shipments")
    public List<Shipment> getShipments() {
        return service.getAllShipments();
    }

    @PostMapping("/api/shipments")
    public void createShipment(@RequestBody Shipment data) {
        //TODO: process POST request
        service.createShipment(data);
        //return entity;
    }

    @PutMapping("/api/shipments/{id}")
    public void updateShipment(@PathVariable Long id, @RequestBody Shipment data) {
        //TODO: process PUT request
        service.updateShipment(id, data);
        // return entity;
    }

    @DeleteMapping("/api/shipments/{shipmentId}")
    public void deleteShipment(@PathVariable Long shipmentId){
        service.deleteShipment(shipmentId);
    }
    
}
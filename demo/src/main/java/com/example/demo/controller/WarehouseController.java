package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.WarehouseItem;
import com.example.demo.service.WarehouseService;

@RestController
@CrossOrigin
// @RequestMapping("/api/warehouse-items")
@RequestMapping("/api/warehouses/{warehouseId}/warehouse-items")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<WarehouseItem>> getAllWarehouseItems(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(warehouseService.getAllWarehouseItems(warehouseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseItem> getWarehouseItemById(@PathVariable Long warehouseId, @PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseItemById(warehouseId, id));
    }

    @PostMapping
    public ResponseEntity<Void> createWarehouseItem(@PathVariable Long warehouseId, @RequestBody WarehouseItem warehouseItem) {
        warehouseService.createWarehouseItem(warehouseId, warehouseItem);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWarehouseItem(@PathVariable Long warehouseId, @PathVariable Long id, @RequestBody WarehouseItem warehouseItem) {
        warehouseService.updateWarehouseItem(warehouseId, id, warehouseItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteWarehouseItem(@PathVariable Long warehouseId, @PathVariable Long id) {
        warehouseService.deleteWarehouseItem(warehouseId, id);
        return ResponseEntity.noContent().build();
    }
}
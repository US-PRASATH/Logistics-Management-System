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
@RequestMapping("/api/warehouse-items")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<WarehouseItem>> getAllWarehouseItems() {
        return ResponseEntity.ok(warehouseService.getAllWarehouseItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseItem> getWarehouseItemById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseItemById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createWarehouseItem(@RequestBody WarehouseItem warehouseItem) {
        warehouseService.createWarehouseItem(warehouseItem);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWarehouseItem(@PathVariable Long id, @RequestBody WarehouseItem warehouseItem) {
        warehouseService.updateWarehouseItem(id, warehouseItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouseItem(@PathVariable Long id) {
        warehouseService.deleteWarehouseItem(id);
        return ResponseEntity.noContent().build();
    }
}
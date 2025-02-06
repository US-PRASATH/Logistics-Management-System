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

import com.example.demo.model.WarehouseItem;
import com.example.demo.service.WarehouseService;





@RestController
@CrossOrigin
class WarehouseController{
    @Autowired
    WarehouseService service;
    // @GetMapping("/")
    // public String getHello() {
    //     return new String("Hello world");
    // }
    
    @GetMapping("/api/warehouse-items")
    public List<WarehouseItem> getWarehouseItems() {
        return service.getAllWarehouseItems();
    }

    @PostMapping("/api/warehouse-items")
    public void createWarehouseItem(@RequestBody WarehouseItem data) {
        //TODO: process POST request
        service.createWarehouseItem(data);
        //return entity;
    }

    @PutMapping("/api/warehouse-items/{id}")
    public void updateWarehouseItem(@PathVariable Long id, @RequestBody WarehouseItem data) {
        //TODO: process PUT request
        service.updateWarehouseItem(id, data);
        // return entity;
    }

    @DeleteMapping("/api/warehouse-items/{id}")
    public void deleteWarehouseItem(@PathVariable Long id){
        service.deleteWarehouseItem(id);
    }
    
}
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

import com.example.demo.model.Supplier;
import com.example.demo.service.SupplierService;





@RestController
@CrossOrigin
class SupplierController{
    @Autowired
    SupplierService service;
    // @GetMapping("/")
    // public String getHello() {
    //     return new String("Hello world");
    // }
    
    @GetMapping("/api/suppliers")
    public List<Supplier> getSuppliers() {
        return service.getAllSuppliers();
    }

    @PostMapping("/api/suppliers")
    public void createSupplier(@RequestBody Supplier data) {
        //TODO: process POST request
        service.createSupplier(data);
        //return entity;
    }

    @PutMapping("/api/suppliers/{id}")
    public void updateSupplier(@PathVariable Long id, @RequestBody Supplier data) {
        //TODO: process PUT request
        service.updateSupplier(id, data);
        // return entity;
    }

    @DeleteMapping("/api/suppliers/{supplierId}")
    public void deleteSupplier(@PathVariable Long supplierId){
        service.deleteSupplier(supplierId);
    }
    
}
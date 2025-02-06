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

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;





@RestController
@CrossOrigin
class OrderController{
    @Autowired
    OrderService service;
    @GetMapping("/")
    public String getHello() {
        return new String("Hello world");
    }
    
    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return service.getAllOrders();
    }

    @PostMapping("/api/orders")
    public void createOrder(@RequestBody Order data) {
        //TODO: process POST request
        service.createOrder(data);
        //return entity;
    }

    @PutMapping("/api/orders/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody Order data) {
        //TODO: process PUT request
        service.updateOrder(id, data);
        // return entity;
    }

    @DeleteMapping("/api/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId){
        service.deleteOrder(orderId);
    }
    
}
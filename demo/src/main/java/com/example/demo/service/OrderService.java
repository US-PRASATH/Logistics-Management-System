package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class OrderService{
    @Autowired
    OrderRepository repo;

    @Autowired
    ProductRepository productRepo;


    public List<Order> getAllOrders(){
        return repo.findAll();
    }

    public Order getOrderById(Long id){
        Optional<Order> optionalOrder = repo.findById(id);
        return optionalOrder.orElse(null); // Return the order if present, otherwise return null
    }

    public void createOrder(Order order){
        if (order.getProduct().getId() != null) {
            Optional<Product> optionalProduct = productRepo.findById(order.getProduct().getId());
            if(optionalProduct.isPresent()){
                Product existingProduct = optionalProduct.get();
                order.setProduct(existingProduct);
            }
        }
        repo.save(order);
    }

    public void updateOrder(Long id, Order data){
        Optional<Order> optionalOrder = repo.findById(id);
    if (optionalOrder.isPresent()) {
        Order existingOrder = optionalOrder.get();
        // Update fields of the existing order with the new order data
        if (data.getCustomerName() != null) {
            existingOrder.setCustomerName(data.getCustomerName());
        }
        // if (data.getProduct() != null) {
        //     existingOrder.setProduct(data.getProduct());
        // }
        if (data.getQuantity() != null) {
            existingOrder.setQuantity(data.getQuantity());
        }
        if (data.getOrderDate() != null) {
            existingOrder.setOrderDate(data.getOrderDate());
        }
        if (data.getTotalAmount() != null) {
            existingOrder.setTotalAmount(data.getTotalAmount());
        }
        if (data.getStatus() != null) {
            existingOrder.setStatus(data.getStatus());
        }
        if (data.getProduct().getId() != null) {
            Optional<Product> optionalProduct = productRepo.findById(data.getProduct().getId());
            if(optionalProduct.isPresent()){
                Product existingProduct = optionalProduct.get();
                existingOrder.setProduct(existingProduct);
            }
        }
        // Add more fields as needed
        repo.save(existingOrder);
    } else {
        throw new RuntimeException("Order not found with id: " + id);
    }
    }

    public void deleteOrder(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}
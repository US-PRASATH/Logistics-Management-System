package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        // Set order date if not provided
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now());
        }

        // Validate and set product
        if (order.getProduct() != null && order.getProduct().getId() != null) {
            Product product = productRepo.findById(order.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            order.setProduct(product);
        }

        // Set default status if not provided
        if (order.getStatus() == null) {
            order.setStatus(Order.OrderStatus.PENDING);
        }

        return orderRepo.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = getOrderById(id);
        
        existingOrder.setCustomerName(orderDetails.getCustomerName() != null ? 
            orderDetails.getCustomerName() : existingOrder.getCustomerName());
        existingOrder.setQuantity(orderDetails.getQuantity() != null ? 
            orderDetails.getQuantity() : existingOrder.getQuantity());
        existingOrder.setOrderDate(orderDetails.getOrderDate() != null ? 
            orderDetails.getOrderDate() : existingOrder.getOrderDate());
        existingOrder.setStatus(orderDetails.getStatus() != null ? 
            orderDetails.getStatus() : existingOrder.getStatus());
            existingOrder.setLocation(orderDetails.getLocation() != null ? 
            orderDetails.getLocation() : existingOrder.getLocation());

        // Update product if provided
        if (orderDetails.getProduct() != null && orderDetails.getProduct().getId() != null) {
            Product product = productRepo.findById(orderDetails.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingOrder.setProduct(product);
        }

        return orderRepo.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepo.delete(order);
    }
}
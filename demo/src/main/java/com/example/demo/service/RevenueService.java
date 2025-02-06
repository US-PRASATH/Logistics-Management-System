package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.OrderRepository;

@Service
public class RevenueService {

    @Autowired
    private OrderRepository orderRepository;

    public double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    public List<Object[]> getDailyRevenue() {
        return orderRepository.getDailyRevenue();
    }

    public List<Object[]> getMonthlyRevenue() {
        return orderRepository.getMonthlyRevenue();
    }

    public List<Object[]> getYearlyRevenue() {
        return orderRepository.getYearlyRevenue();
    }
}

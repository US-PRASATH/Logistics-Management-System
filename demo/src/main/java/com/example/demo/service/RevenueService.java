package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.OrderRepository;

@Service
public class RevenueService {

    @Autowired
    private OrderRepository orderRepository;

      @Autowired
    private TenantService tenantService;

    public double getTotalRevenue() {
        return orderRepository.getTotalRevenue(tenantService.getCurrentUserId());
    }

    public List<Object[]> getDailyRevenue() {
        return orderRepository.getDailyRevenue(tenantService.getCurrentUserId());
    }

    public List<Object[]> getMonthlyRevenue() {
        return orderRepository.getMonthlyRevenue(tenantService.getCurrentUserId());
    }

    public List<Object[]> getYearlyRevenue() {
        return orderRepository.getYearlyRevenue(tenantService.getCurrentUserId());
    }
}

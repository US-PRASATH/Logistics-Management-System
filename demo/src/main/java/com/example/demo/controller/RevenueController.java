package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RevenueService;

@RestController
@RequestMapping("/api/revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/total")
    public double getTotalRevenue() {
        return revenueService.getTotalRevenue();
    }

    @GetMapping("/daily")
    public List<Object[]> getDailyRevenue() {
        return revenueService.getDailyRevenue();
    }

    @GetMapping("/monthly")
    public List<Object[]> getMonthlyRevenue() {
        return revenueService.getMonthlyRevenue();
    }

    @GetMapping("/yearly")
    public List<Object[]> getYearlyRevenue() {
        return revenueService.getYearlyRevenue();
    }
}

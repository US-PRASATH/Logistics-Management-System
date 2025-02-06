package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED'")
    Double getTotalRevenue();

    @Query("SELECT o.orderDate, COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY o.orderDate ORDER BY o.orderDate")
    List<Object[]> getDailyRevenue();

    @Query("SELECT YEAR(o.orderDate), MONTH(o.orderDate), COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    List<Object[]> getMonthlyRevenue();

    @Query("SELECT YEAR(o.orderDate), COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY YEAR(o.orderDate) ORDER BY YEAR(o.orderDate)")
    List<Object[]> getYearlyRevenue();
}

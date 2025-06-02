package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.model.Supplier;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED'")
    // Double getTotalRevenue(@Param("userId") Long userId);

    // @Query("SELECT o.orderDate, COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY o.orderDate ORDER BY o.orderDate")
    // List<Object[]> getDailyRevenue(@Param("userId") Long userId);

    // @Query("SELECT YEAR(o.orderDate), MONTH(o.orderDate), COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    // List<Object[]> getMonthlyRevenue(@Param("userId") Long userId);

    // @Query("SELECT YEAR(o.orderDate), COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' GROUP BY YEAR(o.orderDate) ORDER BY YEAR(o.orderDate)")
    // List<Object[]> getYearlyRevenue(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = :userId")
Double getTotalRevenue(@Param("userId") Long userId);

@Query("SELECT o.orderDate, COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = :userId GROUP BY o.orderDate ORDER BY o.orderDate")
List<Object[]> getDailyRevenue(@Param("userId") Long userId);

@Query("SELECT YEAR(o.orderDate), MONTH(o.orderDate), COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = :userId GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
List<Object[]> getMonthlyRevenue(@Param("userId") Long userId);

@Query("SELECT YEAR(o.orderDate), COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'DELIVERED' AND o.user.id = :userId GROUP BY YEAR(o.orderDate) ORDER BY YEAR(o.orderDate)")
List<Object[]> getYearlyRevenue(@Param("userId") Long userId);

    List<Order> findByUserId(Long userId);
    
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

//     @Query("SELECT o FROM Order o WHERE (o.status <> 'DELIVERED' OR o.transportPlan IS NULL) AND o.user.id = :userId")
// List<Order> findUndeliveredOrWithoutTransportPlanByUserId(@Param("userId") Long userId);

@Query("SELECT o FROM Order o WHERE (o.status <> 'DELIVERED' OR o.trackingNumber IS NULL) AND o.user.id = :userId")
List<Order> findUndeliveredOrWithoutTransportPlanByUserId(@Param("userId") Long userId);

}

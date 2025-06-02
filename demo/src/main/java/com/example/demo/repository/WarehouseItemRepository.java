package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Supplier;
import com.example.demo.model.WarehouseItem;

@Repository
public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Long>{
    Optional<WarehouseItem> findByWarehouseIdAndProductId(Long warehouseId, Long productId);
    Optional<WarehouseItem> findByWarehouseIdAndProductIdAndUserId(Long warehouseId, Long productId, Long userId);
    List<WarehouseItem> findByUserId(Long userId);
    
    Optional<WarehouseItem> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}
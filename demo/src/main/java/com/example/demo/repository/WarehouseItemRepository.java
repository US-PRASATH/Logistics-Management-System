package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.WarehouseItem;

public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Long>{
    
}
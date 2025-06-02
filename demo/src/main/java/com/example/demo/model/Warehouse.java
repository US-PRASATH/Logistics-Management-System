package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer remainingCapacity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    // @OneToMany
    // @JoinColumn(name = "warehouseitems_id")
    // private WarehouseItem warehouseItem;
    // Automatically set values before saving
    @PrePersist
    private void setRemainingCapacity() {
    if (this.remainingCapacity == null) {
        this.remainingCapacity = this.capacity;
    }
}
}


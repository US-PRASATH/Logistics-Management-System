package com.example.demo.model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import jakarta.persistence.AllArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String customerName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private LocalDate orderDate;
    private Double totalAmount;
    private String location;

    @Column(nullable = true)
    private String trackingNumber; // Initially NULL

    
    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "supplier_id")
    // private Supplier supplier;

    // Automatically calculate totalAmount before saving or updating the entity
    @PrePersist
    @PreUpdate
    private void calculateTotalAmount() {
        if (product != null && product.getPrice() != null && quantity != null) {
            this.totalAmount = product.getPrice() * quantity;
        } else {
            this.totalAmount = 0.0;  // Default value if product or quantity is null
        }
    }

    // @PrePersist
    // @PreUpdate
    // private void updateWarehouseItem() {
    //     if (product != null && product.getPrice() != null && quantity != null) {
    //         this.totalAmount = product.getPrice() * quantity;
    //     } else {
    //         this.totalAmount = 0.0;  // Default value if product or quantity is null
    //     }
    // }

    public enum OrderStatus {
        PENDING, 
        CANCELLED, 
        SHIPPED, 
        DELIVERED
    }
    
}

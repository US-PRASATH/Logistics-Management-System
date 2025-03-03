package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transport_plans")
public class TransportPlan{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // private String route;
    private String originLocation;
    private String destinationLocation;
    private String carrier;
    private Double loadCapacity;
    private LocalDate schedule;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    // @OneToOne
    // @JoinColumn(name = "warehouse_id")
    // private Warehouse warehouse;

    // Automatically set values before saving
    @PrePersist
    @PreUpdate
    private void setDerivedFields() {
        System.out.println("hello from function");
        if (transporter != null) {
            this.carrier = transporter.getName();
        }
        if (warehouse != null) {
            this.originLocation = warehouse.getLocation();
        }
        if (order != null){
            System.out.println("Order location: " + order.getLocation());
            this.destinationLocation = order.getLocation();
            // this.destinationLocation = warehouse.getLocation();
        }
    }

    // @PrePersist
    // @PreUpdate
    // private void setOriginLocation(){
    //     if(this.origin == null){
    //         this.origin = warehouse.getLocation();
    //     }
    // }
}
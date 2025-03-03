package com.example.demo.model;
// import javax.persistence.Entity;
// import javax.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "shipments")
public class Shipment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String trackingNumber;
    private String originLocation;
    private String destinationLocation;
    private LocalDate estimatedDeliveryDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "transport_plan_id")
    private TransportPlan transportPlan;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public enum Status{
        IN_TRANSIT,
        DELIVERED,
        DELAYED
    }

    // Automatically set values before saving
    @PrePersist
    @PreUpdate
    private void setDerivedFields() {
        if (transportPlan != null) {
            this.originLocation = transportPlan.getOriginLocation();
            this.destinationLocation = transportPlan.getDestinationLocation();
            this.estimatedDeliveryDate = transportPlan.getSchedule();
        }
        if (this.order != null) {
            if(this.status == Status.IN_TRANSIT || this.status == Status.DELAYED){
            order.setStatus(Order.OrderStatus.SHIPPED);
        }
        if(this.status == Status.DELIVERED){
            order.setStatus(Order.OrderStatus.DELIVERED);
        }  
        if (this.trackingNumber == null) {
            this.trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        if (this.order != null) {
            order.setTrackingNumber(this.trackingNumber);
        }
    }
    }

    // @PrePersist
    // private void generateTrackingNumber() {
    // if (this.trackingNumber == null) {
    //     this.trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    // }
    // if (this.order != null) {
    //     order.setTrackingNumber(this.trackingNumber);
    // }
    // }

    // @PrePersist
    // @PreUpdate
    // private void setOrderStatus() {
    //     if (this.order != null) {
    //         if(this.status == Status.IN_TRANSIT || this.status == Status.DELAYED){
    //         order.setStatus(Order.OrderStatus.SHIPPED);
    //     }
    //     if(this.status == Status.DELIVERED){
    //         order.setStatus(Order.OrderStatus.DELIVERED);
    //     }            
    // }

}

// @PreUpdate
//     private void updateWarehouseStock() {
//         if (this.status == Status.DELIVERED && this.transportPlan != null) {
//             Warehouse warehouse = this.transportPlan.getWarehouse();
//             Product product = this.transportPlan.getOrder().getProduct();
//             Integer orderedQuantity = this.transportPlan.getOrder().getQuantity();

//             if (warehouse != null && product != null && orderedQuantity != null) {
//                 WarehouseItem warehouseItem = warehouse.getWarehouseItem().find
                    

//                 if (warehouseItem != null) {
//                     double newQuantity = warehouseItem.getQuantity() - orderedQuantity;
//                     warehouseItem.setQuantity(Math.max(newQuantity, 0)); // Prevent negative stock
//                 }
//             }
//         }
    // }

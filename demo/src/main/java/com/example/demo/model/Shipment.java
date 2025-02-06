package com.example.demo.model;
// import javax.persistence.Entity;
// import javax.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    private String origin;
    private String destination;
    private String estimatedDeliveryDate;

    @OneToOne
    @JoinColumn(name = "transport_plan_id")
    private TransportPlan transportPlan;
}
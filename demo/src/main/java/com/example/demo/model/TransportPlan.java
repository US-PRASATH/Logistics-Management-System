package com.example.demo.model;

// import javax.persistence.Entity;
// import javax.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private String route;
    private String carrier;
    private Double loadCapacity;
    private String schedule;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    // Automatically set values before saving
    @PrePersist
    @PreUpdate
    private void setDerivedFields() {
        if (transporter != null) {
            this.carrier = transporter.getName();
        }
    }
}
package com.example.demo.model;

import java.time.LocalDate;

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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double amount;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "transporter_id", nullable = true)
    private Transporter transporter;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = true)
    private Warehouse warehouse;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = true)
    private Supplier supplier;
    
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
    public enum ExpenseType{
        TRANSPORTATION,
        WAREHOUSE,
        SUPPLIER_PAYMENT,
        MISCELLANEOUS
    }

    @PrePersist
    @PreUpdate
    private void validateExpense() {
        if ((expenseType == ExpenseType.TRANSPORTATION && transporter == null) ||
            (expenseType == ExpenseType.WAREHOUSE && warehouse == null)||
            (expenseType == ExpenseType.SUPPLIER_PAYMENT && supplier == null)) {
            throw new IllegalArgumentException("For TRANSPORTATION, transportation_id is required. For WAREHOUSE, warehouse_id is required.");
        }
    }
}

// package com.example.demo.model;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToOne;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @Entity
// @AllArgsConstructor
// @NoArgsConstructor
// @Table(name = "revenue")
// public class Revenue {
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     private Long id;
    
//     @OneToOne
//     @JoinColumn(name = "order_id")
//     private Order order;

//     private Double amount;
// }

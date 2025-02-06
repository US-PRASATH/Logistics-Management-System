package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Expenses;
import com.example.demo.model.Supplier;
import com.example.demo.model.Transporter;
import com.example.demo.model.Warehouse;
import com.example.demo.repository.ExpensesRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.TransporterRepository;
import com.example.demo.repository.WarehouseRepository;

@Service
public class ExpensesService{
    @Autowired
    ExpensesRepository repo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    WarehouseRepository warehouseRepo;

    @Autowired
    SupplierRepository supplierRepo;

    @Autowired
    TransporterRepository transporterRepo;


    public List<Expenses> getAllExpensess(){
        return repo.findAll();
    }

    public Expenses getExpensesById(Long id){
        Optional<Expenses> optionalExpenses = repo.findById(id);
        return optionalExpenses.orElse(null); // Return the order if present, otherwise return null
    }

    public void createExpenses(Expenses order){
        if (order.getWarehouse().getId() != null) {
            Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(order.getWarehouse().getId());
            if(optionalWarehouse.isPresent()){
                Warehouse existingWarehouse = optionalWarehouse.get();
                order.setWarehouse(existingWarehouse);
            }
        }
        if (order.getTransporter().getId() != null) {
            Optional<Transporter> optionalTransporter = transporterRepo.findById(order.getTransporter().getId());
            if(optionalTransporter.isPresent()){
                Transporter existingTransporter = optionalTransporter.get();
                order.setTransporter(existingTransporter);
            }
        }
        if (order.getSupplier().getId() != null) {
            Optional<Supplier> optionalSupplier = supplierRepo.findById(order.getSupplier().getId());
            if(optionalSupplier.isPresent()){
                Supplier existingSupplier = optionalSupplier.get();
                order.setSupplier(existingSupplier);
            }
        }
        repo.save(order);
    }

    public void updateExpenses(Long id, Expenses data){
        Optional<Expenses> optionalExpenses = repo.findById(id);
    if (optionalExpenses.isPresent()) {
        Expenses existingExpenses = optionalExpenses.get();
        // Update fields of the existing order with the new order data
        // if (data.getCustomerName() != null) {
        //     existingExpenses.setCustomerName(data.getCustomerName());
        // }
        // if (data.getProduct() != null) {
        //     existingExpenses.setProduct(data.getProduct());
        // }
        if (existingExpenses.getWarehouse().getId() != null) {
            Optional<Warehouse> optionalWarehouse = warehouseRepo.findById(existingExpenses.getWarehouse().getId());
            if(optionalWarehouse.isPresent()){
                Warehouse existingWarehouse = optionalWarehouse.get();
                existingExpenses.setWarehouse(existingWarehouse);
            }
        }
        // if (existingExpenses.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(existingExpenses.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         existingExpenses.setProduct(existingProduct);
        //     }
        // }
        if (existingExpenses.getTransporter().getId() != null) {
            Optional<Transporter> optionalTransporter = transporterRepo.findById(existingExpenses.getTransporter().getId());
            if(optionalTransporter.isPresent()){
                Transporter existingTransporter = optionalTransporter.get();
                existingExpenses.setTransporter(existingTransporter);
            }
        }
        if (existingExpenses.getSupplier().getId() != null) {
            Optional<Supplier> optionalSupplier = supplierRepo.findById(existingExpenses.getSupplier().getId());
            if(optionalSupplier.isPresent()){
                Supplier existingSupplier = optionalSupplier.get();
                existingExpenses.setSupplier(existingSupplier);
            }
        }
        if (data.getExpenseType() != null) {
            existingExpenses.setExpenseType(data.getExpenseType());
        }
        if (data.getAmount() != null) {
            existingExpenses.setAmount(data.getAmount());
        }
        if (data.getDate() != null) {
            existingExpenses.setDate(data.getDate());
        }
        // if (data.getProduct().getId() != null) {
        //     Optional<Product> optionalProduct = productRepo.findById(data.getProduct().getId());
        //     if(optionalProduct.isPresent()){
        //         Product existingProduct = optionalProduct.get();
        //         existingExpenses.setProduct(existingProduct);
        //     }
        // }
        // Add more fields as needed
        repo.save(existingExpenses);
    } else {
        throw new RuntimeException("Expenses not found with id: " + id);
    }
    }

    public void deleteExpenses(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Expenses not found with id: " + id);
        }
    }
}
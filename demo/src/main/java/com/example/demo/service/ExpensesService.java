package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Expenses;
import com.example.demo.model.Supplier;
import com.example.demo.model.Transporter;
import com.example.demo.model.Warehouse;
import com.example.demo.repository.ExpensesRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.TransporterRepository;
import com.example.demo.repository.WarehouseRepository;

@Service
public class ExpensesService {
    @Autowired
    private ExpensesRepository repo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private SupplierRepository supplierRepo;

    @Autowired
    private TransporterRepository transporterRepo;

    public List<Expenses> getAllExpenses() {
        return repo.findAll();
    }

    public Expenses getExpenseById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    public Expenses createExpense(Expenses expense) {
        validateExpenseReferences(expense);
        return repo.save(expense);
    }

    public Expenses updateExpense(Long id, Expenses data) {
        Expenses existingExpense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        if (data.getAmount() != null) {
            existingExpense.setAmount(data.getAmount());
        }
        if (data.getDate() != null) {
            existingExpense.setDate(data.getDate());
        }
        if (data.getExpenseType() != null) {
            existingExpense.setExpenseType(data.getExpenseType());
        }
        if (data.getWarehouse() != null && data.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepo.findById(data.getWarehouse().getId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found"));
            existingExpense.setWarehouse(warehouse);
        }
        if (data.getTransporter() != null && data.getTransporter().getId() != null) {
            Transporter transporter = transporterRepo.findById(data.getTransporter().getId())
                    .orElseThrow(() -> new RuntimeException("Transporter not found"));
            existingExpense.setTransporter(transporter);
        }
        if (data.getSupplier() != null && data.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findById(data.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            existingExpense.setSupplier(supplier);
        }

        return repo.save(existingExpense);
    }

    public void deleteExpense(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        repo.deleteById(id);
    }

    private void validateExpenseReferences(Expenses expense) {
        if (expense.getWarehouse() != null && expense.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepo.findById(expense.getWarehouse().getId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found"));
            expense.setWarehouse(warehouse);
        }
        if (expense.getTransporter() != null && expense.getTransporter().getId() != null) {
            Transporter transporter = transporterRepo.findById(expense.getTransporter().getId())
                    .orElseThrow(() -> new RuntimeException("Transporter not found"));
            expense.setTransporter(transporter);
        }
        if (expense.getSupplier() != null && expense.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findById(expense.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            expense.setSupplier(supplier);
        }
    }
}

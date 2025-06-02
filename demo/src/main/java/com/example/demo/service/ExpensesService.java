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

      @Autowired
    private TenantService tenantService;

    public List<Expenses> getAllExpenses() {
        return repo.findByUserId(tenantService.getCurrentUserId());
    }

    public Expenses getExpenseById(Long id) {
        return repo.findByIdAndUserId(id, tenantService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    public Expenses createExpense(Expenses expense) {
        validateExpenseReferences(expense);
        expense.setUser(tenantService.getCurrentUser());
        return repo.save(expense);
    }

    public Expenses updateExpense(Long id, Expenses data) {
        Expenses existingExpense = repo.findByIdAndUserId(id, tenantService.getCurrentUserId())
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
            Warehouse warehouse = warehouseRepo.findByIdAndUserId(data.getWarehouse().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found"));
            existingExpense.setWarehouse(warehouse);
        }
        if (data.getTransporter() != null && data.getTransporter().getId() != null) {
            Transporter transporter = transporterRepo.findByIdAndUserId(data.getTransporter().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Transporter not found"));
            existingExpense.setTransporter(transporter);
        }
        if (data.getSupplier() != null && data.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findByIdAndUserId(data.getSupplier().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            existingExpense.setSupplier(supplier);
        }

        return repo.save(existingExpense);
    }

    // public void deleteExpense(Long id) {
    //     if (!repo.existsById(id)) {
    //         throw new RuntimeException("Expense not found with id: " + id);
    //     }
    //     repo.deleteById(id);
    // }

    public void deleteExpense(Long id) {
    Long userId = tenantService.getCurrentUserId();

    // Ensure the expense exists and belongs to the user
    repo.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new RuntimeException("Expense not found or unauthorized with id: " + id));

    // Safe and efficient deletion
    repo.deleteByIdAndUserId(id, userId);
}

    private void validateExpenseReferences(Expenses expense) {
        if (expense.getWarehouse() != null && expense.getWarehouse().getId() != null) {
            Warehouse warehouse = warehouseRepo.findByIdAndUserId(expense.getWarehouse().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found"));
            expense.setWarehouse(warehouse);
        }
        if (expense.getTransporter() != null && expense.getTransporter().getId() != null) {
            Transporter transporter = transporterRepo.findByIdAndUserId(expense.getTransporter().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Transporter not found"));
            expense.setTransporter(transporter);
        }
        if (expense.getSupplier() != null && expense.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findByIdAndUserId(expense.getSupplier().getId(), tenantService.getCurrentUserId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            expense.setSupplier(supplier);
        }
    }
}

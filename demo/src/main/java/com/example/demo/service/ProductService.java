package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final SupplierRepository supplierRepo;

    @Autowired
    public ProductService(ProductRepository productRepo, SupplierRepository supplierRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
    }

      @Autowired
    private TenantService tenantService;

    public List<Product> getAllProducts() {
        return productRepo.findByUserId(tenantService.getCurrentUserId());
    }

    public Product getProductById(Long id) {
        return productRepo.findByIdAndUserId(id, tenantService.getCurrentUserId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        if (product.getSupplier() != null && product.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findByIdAndUserId(product.getSupplier().getId(), tenantService.getCurrentUserId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
            product.setSupplier(supplier);
        }
        product.setUser(tenantService.getCurrentUser());
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);
        
        existingProduct.setName(productDetails.getName() != null ? 
            productDetails.getName() : existingProduct.getName());
        existingProduct.setCategory(productDetails.getCategory() != null ? 
            productDetails.getCategory() : existingProduct.getCategory());
        existingProduct.setPrice(productDetails.getPrice() != null ? 
            productDetails.getPrice() : existingProduct.getPrice());

        if (productDetails.getSupplier() != null && productDetails.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findByIdAndUserId(productDetails.getSupplier().getId(), tenantService.getCurrentUserId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
            existingProduct.setSupplier(supplier);
        }

        return productRepo.save(existingProduct);
    }

    // public void deleteProduct(Long id) {
    //     Product product = getProductById(id);
    //     productRepo.delete(product);
    // }

    public void deleteProduct(Long id) {
    Long userId = tenantService.getCurrentUserId();

    // Ensure the product belongs to the user
    productRepo.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

    // Safe and efficient deletion
    productRepo.deleteByIdAndUserId(id, userId);
}

}
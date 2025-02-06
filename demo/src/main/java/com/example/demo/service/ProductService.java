package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;

@Service
public class ProductService{
    @Autowired
    ProductRepository repo;

    @Autowired
    ProductRepository productRepo;
    @Autowired
    SupplierRepository supplierRepo;


    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getProductById(Long id){
        Optional<Product> optionalProduct = repo.findById(id);
        return optionalProduct.orElse(null); // Return the product if present, otherwise return null
    }

    public void createProduct(Product product){
        if (product.getSupplier().getId() != null) {
            Optional<Supplier> optionalSupplier = supplierRepo.findById(product.getSupplier().getId());
            if(optionalSupplier.isPresent()){
                Supplier existingSupplier = optionalSupplier.get();
                product.setSupplier(existingSupplier);
            }
        }
        repo.save(product);
    }

    public void updateProduct(Long id, Product data){
        Optional<Product> optionalProduct = repo.findById(id);
    if (optionalProduct.isPresent()) {
        Product existingProduct = optionalProduct.get();
        // Update fields of the existing product with the new product data
        if (data.getName() != null) {
            existingProduct.setName(data.getName());
        }
        if (data.getCategory() != null) {
            existingProduct.setCategory(data.getCategory());
        }
        if (data.getPrice() != null) {
            existingProduct.setPrice(data.getPrice());
        }
        // if (data.getStatus() != null) {
        //     existingProduct.setStatus(data.getStatus());
        // }
        if (data.getSupplier().getId() != null) {
            Optional<Supplier> optionalSupplier = supplierRepo.findById(data.getSupplier().getId());
            if(optionalSupplier.isPresent()){
                Supplier existingSupplier = optionalSupplier.get();
                existingProduct.setSupplier(existingSupplier);
            }
        }
        // Add more fields as needed
        repo.save(existingProduct);
    } else {
        throw new RuntimeException("Product not found with id: " + id);
    }
    }

    public void deleteProduct(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
package com.example.demo.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{
    List<Supplier> findByUserId(Long userId);
    
    Optional<Supplier> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
    
    // @Query("SELECT p FROM Supplier p WHERE p.category = :category AND p.user.id = :userId")
    // List<Supplier> findByCategoryAndUser(@Param("category") String category, 
    //                                    @Param("userId") Long userId);
}
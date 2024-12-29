package com.project.ecommerce.repository;

import com.project.ecommerce.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "(COALESCE(:category, '') = '' OR LOWER(p.productCategory) = LOWER(:category)) AND " +
            "(COALESCE(:subCategory, '') = '' OR LOWER(p.productSubCategory) = LOWER(:subCategory))")
    List<Product> findByCategoryAndSubCategory(
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            Pageable pageable);
}
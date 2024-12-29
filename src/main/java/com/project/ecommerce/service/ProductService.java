package com.project.ecommerce.service;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getFilteredProducts(Optional<String> category, Optional<String> subCategory, Optional<Integer> limit) {
        int pageSize = 100;
        if (limit.isPresent())
            pageSize = limit.get();
        Pageable pageable = PageRequest.of(0, pageSize);

        String categoryParam = category.orElse("");
        String subCategoryParam = subCategory.orElse("");

        return productRepository.findByCategoryAndSubCategory(categoryParam, subCategoryParam, pageable);
    }
}

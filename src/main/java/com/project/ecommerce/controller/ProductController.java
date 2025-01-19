package com.project.ecommerce.controller;

import com.project.ecommerce.model.Message;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.UserPrincipal;
import com.project.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Product")
@RequestMapping("v1")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getProducts(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> subCategory,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> seller,
            @AuthenticationPrincipal UserPrincipal user) {
        List<Product> productList = new ArrayList<>();

        if (seller.isPresent()) {
            if (user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SELLER"))) {
                productList.addAll(productService.getFilteredProducts(category, subCategory, limit, seller));
            } else {
                return new ResponseEntity<>(Message.getErrorMsg("sellerParam need seller login"), HttpStatus.UNAUTHORIZED);
            }
        }

        productList.addAll(productService.getFilteredProducts(category, subCategory, limit));

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}

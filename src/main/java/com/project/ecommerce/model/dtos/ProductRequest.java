package com.project.ecommerce.model.dtos;

import com.project.ecommerce.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private Users seller;
    private String productName;
    private String productBrand;
    private String productDesc;
    private String productCategory;
    private String productSubCategory;
    private String productPrice;
    private String productDiscount;
    private String productImg;
    private String productThumbImg;
    private double productRating;
    private long productQuantity;

}

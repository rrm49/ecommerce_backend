package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.ecommerce.model.enums.AdsTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ads_table")
public class Ads {

    @Enumerated(EnumType.STRING)
    @Column(name = "ad_type")
    private AdsTypes adType;

    @Column(name = "ad_image_o")
    private String adImageL;

    @Column(name = "ad_image_m")
    private String adImageM;

    @Column(name = "ad_image_t")
    private String adImageT;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false) // Foreign key to products table
    @JsonBackReference // Break the circular reference here
    private Product productId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private int adId;
}

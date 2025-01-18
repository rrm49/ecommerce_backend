package com.project.ecommerce.repository;

import com.project.ecommerce.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
}

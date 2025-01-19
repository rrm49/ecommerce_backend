package com.project.ecommerce.controller;

import com.project.ecommerce.model.Ads;
import com.project.ecommerce.service.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsService adsService;

    // Endpoint to get all ads (optional for initial data loading)
    @GetMapping
    public List<Ads> getAllAds() {
        return adsService.getAllAds();
    }

    @PostMapping
    public void addAd(@RequestBody Ads ad) {
        adsService.addAd(ad);
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable int id) {
        adsService.deleteAd(id);
    }
}

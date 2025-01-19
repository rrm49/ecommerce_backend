package com.project.ecommerce.service;

import com.project.ecommerce.model.Ads;
import com.project.ecommerce.repository.AdsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsService {

    private final AdsRepository adsRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    public List<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    public void addAd(Ads ad) {
        adsRepository.save(ad);
        broadcastAds(); // Broadcast the updated ads immediately
    }

    public void deleteAd(int id) {
        adsRepository.deleteById(id);
        broadcastAds(); // Broadcast the updated ads immediately
    }

    private void broadcastAds() {
        List<Ads> ads = getAllAds();
        messagingTemplate.convertAndSend("/topic/ads", ads); // Send updated ads to subscribers
    }
}

package com.example.watchlist_service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Component
public class KeepAliveScheduler {

    @Value("${api.base.url}")
    private static String RENDER_BASE_URL;
    private static final String RENDER_URL = RENDER_BASE_URL + "watchlist/all";
    private static final Random RANDOM = new Random();
    
    private final RestTemplate restTemplate = new RestTemplate();

    // Generate random delay between 1 and 14 minutes (in milliseconds)
    public static long getRandomDelay() {
        // Random minutes between 1 and 14 (inclusive)
        int minutes = 1 + RANDOM.nextInt(14);
        System.out.println("⏱️ Next keep-alive in " + minutes + " minutes");
        return minutes * 60 * 1000L;
    }

    // Use a fixed delay with a static method reference
    @Scheduled(fixedDelayString = "#{T(com.example.watchlist_service.util.KeepAliveScheduler).getRandomDelay()}")
    public void keepAlivePing() {
        try {
            restTemplate.getForObject(RENDER_URL, String.class);
            System.out.println("💓 Keep-alive ping sent successfully at " + new java.util.Date());
        } catch (Exception e) {
            System.err.println("⚠️ Keep-alive ping failed: " + e.getMessage());
        }
    }
}
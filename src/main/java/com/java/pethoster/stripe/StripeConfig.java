package com.java.pethoster.stripe;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class StripeConfig {

    @Value("${stripe.secret-key}")  // Injection de la clé secrète Stripe
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;  // Configuration de la clé API Stripe
        log.info("Stripe API Key configured: {}", secretKey);  // Log de la clé API
    }
}
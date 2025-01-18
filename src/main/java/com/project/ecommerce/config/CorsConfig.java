package com.project.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow only specific origins
        config.addAllowedOrigin("http://localhost:5173"); // Add your frontend domain
        config.addAllowedOrigin("https://ecommerce-client-git-main-rajeshs-projects-153896cc.vercel.app");
        config.addAllowedOrigin("https://bizarre-nelly-project-1241-b12faa1a.koyeb.app");
        config.addAllowedOrigin("https://grumpy-termite-stamphub-31a22ab5.koyeb.app");
        config.addAllowedOrigin("https://forthcoming-brear-ecomorg-0c964907.koyeb.app");

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        // Allow specific HTTP methods
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        // Allow specific headers
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");

        // Allow credentials (only if necessary)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

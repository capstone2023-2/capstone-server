package com.capstone.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3002");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://www.csessence.site");
        config.addAllowedOrigin("https://www.csessence.site");
        config.addAllowedOrigin("https://www.csessence.site:3000");
        config.addAllowedOrigin("https://www.csessence.site:3002");
        config.addAllowedOrigin("https://csessence.site");
        config.addAllowedOrigin("https://csessence.site:3000");
        config.addAllowedOrigin("https://csessence.site:3002");
        config.addAllowedOrigin("http://43.200.87.142");
        config.addAllowedOrigin("http://43.200.87.142:3000");
        config.addAllowedOrigin("http://43.200.87.142:3002");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(1800L);
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

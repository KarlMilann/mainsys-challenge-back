package com.mainsys.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Profile("development")
public class DevCorsConfiguratioh implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS");
    }
}

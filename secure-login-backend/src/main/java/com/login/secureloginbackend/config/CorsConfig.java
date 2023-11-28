package com.login.secureloginbackend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to configure the CORS policy for the application.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * This method is used to configure the CORS policy for the application.
     * @return - CORS filter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        //configuration.addAllowedOrigin("*");
        //configuration.addAllowedMethod("*");
        //configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        //return source;
        return new CorsFilter(source);
    }

    /**
     * This method is used to configure the CORS policy for the application.
     * @param registry - CORS registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Allow all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                //.allowCredentials(true)
                .maxAge(3600);
    }


}
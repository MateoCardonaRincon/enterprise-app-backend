package com.mateocr.enterpriseapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

@Configuration
@EnableWebFlux
public class WebFluxConfig {
    @Bean
    public WebFluxConfigurer corsConfigure() {
        return new WebFluxConfigurerComposite() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")  // 0.0.0.0/0
                        .allowedMethods("*"); // GET, POST, PUT, DELETE, ETC...
            }
        };
    }
}
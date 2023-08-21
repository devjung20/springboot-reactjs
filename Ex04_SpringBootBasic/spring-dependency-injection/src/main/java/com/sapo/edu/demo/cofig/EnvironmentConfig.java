package com.sapo.edu.demo.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EnvironmentConfig {
    @Bean
    @Profile("dev")
    public void devConfig() {
        System.out.println("Môi trường DEV!");
    }

    @Bean
    @Profile("pro")
    public void proConfig() {
        System.out.println("Môi trường Product!");
    }
}

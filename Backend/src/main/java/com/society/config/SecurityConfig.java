package com.society.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // VERY IMPORTANT for Postman
            .csrf(csrf -> csrf.disable())

            // allow everything
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // disable default login
            .formLogin(form -> form.disable())

            // disable basic auth popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}

package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    )throws Exception{
        //haap
        //h<cd>2
        http.csrf().disable().cors().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/login","/api/v1/otp/generate","/api/v1/otp/validate", "/api/v1/user-signup", "/api/v1/contentManager-signup", "/api/v1/blogManager-signup")
                .permitAll()
                .requestMatchers("/api/v1/cars/addcar")
                .hasRole("USER")
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        return http.build();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

package com.orana.appstockexchange.config;

import com.orana.appstockexchange.model.enums.AppUserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/h2-console/**").hasAnyAuthority(AppUserRole.ADMIN.getValue())
                        .requestMatchers("/api/v1/user/**").hasAnyAuthority(AppUserRole.ADMIN.getValue())
                        .requestMatchers(HttpMethod.GET, "/api/v1/stock/**").hasAnyAuthority(AppUserRole.USER.getValue(), AppUserRole.ADMIN.getValue())
                        .requestMatchers("/api/v1/stock/**").hasAnyAuthority(AppUserRole.ADMIN.getValue())
                        .requestMatchers(HttpMethod.GET, "/api/v1/stock-exchange/**").hasAnyAuthority(AppUserRole.USER.getValue(), AppUserRole.ADMIN.getValue())
                        .requestMatchers("/api/v1/stock-exchange/**").hasAnyAuthority(AppUserRole.ADMIN.getValue())
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
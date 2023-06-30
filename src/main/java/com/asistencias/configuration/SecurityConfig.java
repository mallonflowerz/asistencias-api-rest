package com.asistencias.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.asistencias.configuration.filters.JwtAuthenticationFilter;
import com.asistencias.configuration.filters.JwtValidationFilter;
import com.asistencias.variables.Origin;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authConfig;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authManager() throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers("/register").hasAnyRole("ADMIN")
                .requestMatchers("/files/**").permitAll()
                // .requestMatchers(HttpMethod.POST, "/register").permitAll()
                // .requestMatchers(HttpMethod.PUT, "/register").permitAll()
                // .requestMatchers(HttpMethod.DELETE, "/register").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/asistencia/**").permitAll()
                .requestMatchers("/asistencia/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/usuarios").hasAnyRole("ADMIN")
                .requestMatchers("/usuarios/**").permitAll()
                .requestMatchers("/usuarios").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authConfig.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authConfig.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfig()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(Origin.origen()));
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfig()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

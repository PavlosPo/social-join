package com.social.join.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/login", "POST"),
                                new AntPathRequestMatcher("/register", "POST")).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/posts", "POST"),
                                new AntPathRequestMatcher("/posts", "GET"),
                                new AntPathRequestMatcher("/posts/**")).authenticated()
                        .requestMatchers(
                                new AntPathRequestMatcher("/posts", "GET"),
                                new AntPathRequestMatcher("/posts", "POST"),
                                new AntPathRequestMatcher("/posts", "PATCH"),
                                new AntPathRequestMatcher("/posts", "DELETE")).authenticated()
                        .anyRequest().authenticated())
        ;
        return http.build();
    }


}
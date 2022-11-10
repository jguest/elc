package com.jguest.explorelearning.challenge.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
public class SecurityConfiguration {

    @Value("${application.api-key}")
    private String apiKey;

    @Value("${application.api-key-header-name}")
    private String apiKeyHeaderName;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        APIKeyPreAuthenticatedProcessingFilter apiKeyFilter = new APIKeyPreAuthenticatedProcessingFilter(apiKeyHeaderName);
        apiKeyFilter.setAuthenticationManager(new APIKeyAuthenticationManager(apiKey));

        httpSecurity.csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(apiKeyFilter, AbstractPreAuthenticatedProcessingFilter.class);

        return httpSecurity.build();
    }
}

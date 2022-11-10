package com.jguest.explorelearning.challenge.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class APIKeyAuthenticationManager implements AuthenticationManager {

    private final String apiKey;

    public APIKeyAuthenticationManager(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        authentication.setAuthenticated(principal.equals(apiKey));
        return authentication;
    }
}

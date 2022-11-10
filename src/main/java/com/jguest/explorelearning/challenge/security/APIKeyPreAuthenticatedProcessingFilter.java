package com.jguest.explorelearning.challenge.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class APIKeyPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final String headerName;

    public APIKeyPreAuthenticatedProcessingFilter(String headerName) {
        this.headerName = headerName;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}

package com.appdirect.teleport.security;

import org.springframework.security.core.Authentication;

/**
 * A Facade to retrieve login information
 */
public interface AuthenticationFacade {
    Authentication getAuthentication();
}


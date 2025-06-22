package br.com.ff.arch_beaver.common.error.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationSeifException extends AuthenticationException {

    public AuthenticationSeifException(String message) {
        super(message);
    }

    public AuthenticationSeifException(String message, Exception e) {
        super(message, e);
    }

}
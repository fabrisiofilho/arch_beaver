package br.com.ff.arch_beaver.common.error.exception.auth;

public class PasswordException extends RuntimeException {

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(Exception e) {
        super(e);
    }

    public PasswordException(String message, Exception e) {
        super(message, e);
    }
}

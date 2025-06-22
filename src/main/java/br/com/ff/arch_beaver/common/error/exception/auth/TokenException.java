package br.com.ff.arch_beaver.common.error.exception.auth;

public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(Exception e) {
        super(e);
    }

    public TokenException(String message, Exception e) {
        super(message, e);
    }
}

package br.com.ff.arch_beaver.common.error.exception.general;

public class PropertyValueException extends RuntimeException {

    public PropertyValueException(String message) {
        super(message);
    }

    public PropertyValueException(String message, Exception e) {
        super(message, e);
    }
}


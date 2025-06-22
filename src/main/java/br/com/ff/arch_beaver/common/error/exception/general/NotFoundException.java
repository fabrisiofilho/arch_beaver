package br.com.ff.arch_beaver.common.error.exception.general;

public class NotFoundException extends RuntimeException {

    public static final String NOT_FOUND_ERROR = "Não foi possível encontrar o(a) %s informado(a).";

    public NotFoundException(String entidade) {
        super(String.format(NOT_FOUND_ERROR, entidade));
    }

    public NotFoundException(String messageCustom, String entidade) {
        super(String.format(messageCustom, entidade));
    }

    public NotFoundException(Exception e) {
        super(e);
    }

    public NotFoundException(String entidade, Exception e) {
        super(String.format(NOT_FOUND_ERROR, entidade), e);
    }

    public NotFoundException(String messageCustom, String entidade, Exception e) {
        super(String.format(messageCustom, entidade), e);
    }

}

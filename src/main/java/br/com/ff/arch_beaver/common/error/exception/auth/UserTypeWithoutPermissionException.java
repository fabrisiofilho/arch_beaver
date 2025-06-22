package br.com.ff.arch_beaver.common.error.exception.auth;

public class UserTypeWithoutPermissionException extends RuntimeException {

    public static final String USER_TYPE_WITHOUT_PERMISSION = "O seu tipo de usuário não tem permissão para acessar este recurso.";

    public UserTypeWithoutPermissionException() {
        super(USER_TYPE_WITHOUT_PERMISSION);
    }
}

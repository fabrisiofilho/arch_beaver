package br.com.ff.arch_beaver.common.utils.validator;

import br.com.ff.arch_beaver.common.constants.PatternsRegex;
import br.com.ff.arch_beaver.common.error.exception.general.PropertyValueException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordValidator {

    public static String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(validate(password));
    }

    public static Boolean isEquals(CharSequence newPassowrd, String password) {
        return new BCryptPasswordEncoder().matches(newPassowrd, password);
    }

    public static String validate(String password) {
        if (password.matches(PatternsRegex.VALIDATION_PASS_REGEX)) {
            return password;
        }
        throw new PropertyValueException("Senha inválida, a senha deve ter de 8 a 255 caracteres com letras maiúsculas, minúsculas, números e caracteres especiais");
    }
}

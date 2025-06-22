package br.com.ff.arch_beaver.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatternsRegex {

    public static final String EMAIL_VALIDATION_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String VALIDATION_PASS_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()--+={}\\[\\]|\\\\:;\"'<>,.?/_]).{8,255}";
    public static final String REGEX_SIMPLE_LEFT_BAR = "\\\\";
    public static final String REGEX_LEFT_BAR_WITH_LINE_BREAK = "\\\\n";
    public static final String REGEX_DOT = "\\.";
    public static final String REGEX_DOLLAR_SIGN = "\\$";

}

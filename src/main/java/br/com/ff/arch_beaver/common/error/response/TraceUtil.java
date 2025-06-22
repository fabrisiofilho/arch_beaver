package br.com.ff.arch_beaver.common.error.response;

import br.com.ff.arch_beaver.common.constants.PatternsRegex;
import br.com.ff.arch_beaver.common.constants.ResponseValues;
import br.com.ff.arch_beaver.common.utils.validator.GenericValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceUtil {

    public static List<String> filterTrace(List<StackTraceElement> stackTraceElements) {
        var stack = new ArrayList<String>();
        for (StackTraceElement traceElement : stackTraceElements) {
            var containPackage = traceElement.getClassName().contains(ResponseValues.STACK_FOR_PACKAGE);
            var containDollarSing = GenericValidator.validate(traceElement.getClassName(), PatternsRegex.REGEX_DOLLAR_SIGN);

            if (containPackage && !containDollarSing) {
                stack.add(traceElement.toString());
            }
        }
        return stack;
    }

    public static Exception catchRootException(Throwable throwable) {
        while (throwable.getCause() != null && throwable.getCause() != throwable) {
            throwable = throwable.getCause();
        }
        return new Exception(throwable.getMessage(), throwable);
    }
}

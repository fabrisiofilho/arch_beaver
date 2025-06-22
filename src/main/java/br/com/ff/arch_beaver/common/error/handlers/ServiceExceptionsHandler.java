package br.com.ff.arch_beaver.common.error.handlers;

import br.com.ff.arch_beaver.common.error.exception.auth.AuthenticationSeifException;
import br.com.ff.arch_beaver.common.error.exception.auth.TokenException;
import br.com.ff.arch_beaver.common.error.response.ErrorResponse;
import br.com.ff.arch_beaver.common.error.treatment.AbstractTreatment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceExceptionsHandler {

    public static void handler(HttpServletRequest request, HttpServletResponse response,
                               TokenException e, HttpStatus status) throws IOException {
        summary(request, response, e, status);
    }

    public static void handler(HttpServletRequest request, HttpServletResponse response,
                               AuthenticationSeifException e, HttpStatus status) throws IOException {
        summary(request, response, e, status);
    }

    public static void handler(HttpServletRequest request, HttpServletResponse response,
                               Exception e, HttpStatus status) throws IOException {
        summary(request, response, e, status);
    }

    private static void summary(HttpServletRequest request, HttpServletResponse response,
                                Exception e, HttpStatus status) throws IOException {
        Map<String, AbstractTreatment> map = ErrorResponse.handle(e, request);
        var jsonObject = new JSONObject(map);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().append(jsonObject.toString());
    }
}

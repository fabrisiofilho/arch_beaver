package br.com.ff.arch_beaver.common.security.filters;

import br.com.ff.arch_beaver.common.error.exception.auth.AuthenticationSeifException;
import br.com.ff.arch_beaver.common.error.response.ErrorResponse;
import br.com.ff.arch_beaver.common.security.details.AuthUserDetails;
import br.com.ff.arch_beaver.common.security.models.Credential;
import br.com.ff.arch_beaver.common.security.models.LoginWeb;
import br.com.ff.arch_beaver.common.security.models.UserLogin;
import br.com.ff.arch_beaver.common.security.utils.TokenJWTSecurity;
import br.com.ff.arch_beaver.modules.user.domain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class AuthenticationLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenJWTSecurity jwtSecurity;
    private final UserService userService;

    public AuthenticationLoginFilter(AuthenticationManager authenticationManager, TokenJWTSecurity jwtSecurity, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtSecurity = jwtSecurity;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Credential credentialsDTO = new ObjectMapper().readValue(request.getInputStream(), Credential.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new AuthenticationSeifException("Não foi possível autenticar: Email ou senha está incorreto");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((AuthUserDetails) authResult.getPrincipal()).getUsername();
        UserLogin dto = userService.login(username);

        String token = jwtSecurity.generateTokenWeb(username);
        String refreshToken = jwtSecurity.generateRefreshToken(username);

        updateTokenInDatabase(token, username);
        setCookies(response, token, refreshToken);
        writeResponse(response, new LoginWeb(token, refreshToken, dto));
    }

    private void updateTokenInDatabase(String token, String username) {
        userService.updateToken(token, username);
    }

    private void writeResponse(HttpServletResponse response, LoginWeb login) {
        try {
            String json = new ObjectMapper().writer().writeValueAsString(login);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (Exception e) {
            throw new AuthenticationSeifException("Ocorreu um erro a tentar executar o login, tente novamente");
        }
    }

    private void setCookies(HttpServletResponse response, String token, String refreshToken) {
        Cookie tokenCookie = createCookie("TOKEN", token);
        Cookie refreshCookie = createCookie("REFRESH_TOKEN", refreshToken);

        response.addCookie(tokenCookie);
        response.addCookie(refreshCookie);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        var error = ErrorResponse.handle(failed.getCause() instanceof AuthenticationSeifException ? failed : new AuthenticationSeifException("Usuário inexistente ou senha inválida."), request);
        String json = new ObjectMapper().writer().writeValueAsString(error);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(json);
        response.getWriter().flush();
    }

}

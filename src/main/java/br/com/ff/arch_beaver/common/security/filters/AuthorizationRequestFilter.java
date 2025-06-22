package br.com.ff.arch_beaver.common.security.filters;

import br.com.ff.arch_beaver.common.config.UserContext;
import br.com.ff.arch_beaver.common.error.handlers.ServiceExceptionsHandler;
import br.com.ff.arch_beaver.common.security.constants.SecurityConfigurationConstants;
import br.com.ff.arch_beaver.common.security.utils.TokenJWTSecurity;
import br.com.ff.arch_beaver.modules.user.domain.entity.UserEntity;
import br.com.ff.arch_beaver.modules.user.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class AuthorizationRequestFilter extends BasicAuthenticationFilter {

    private final TokenJWTSecurity jwtSecurity;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public AuthorizationRequestFilter(AuthenticationManager authenticationManager, TokenJWTSecurity jwtSecurity,
                                      UserService userService, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtSecurity = jwtSecurity;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        String header = request.getHeader("AUTHORIZATION");

        try {
            if (Objects.nonNull(header)) {
                UsernamePasswordAuthenticationToken token = getAuthentication(header);
                if (token != null) {
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception exception) {
            UserContext.clearAll();
            SecurityContextHolder.clearContext();
            ServiceExceptionsHandler.handler(request, response, exception, HttpStatus.UNAUTHORIZED);
        } finally {
            UserContext.clearAll();
            SecurityContextHolder.clearContext();
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return ignoreUrl(request, SecurityConfigurationConstants.getPublicMatchersUrlIgnore());
    }

    @Override
    protected boolean isIgnoreFailure() {
        return true;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtSecurity.tokenValid(token)) {
            String email = jwtSecurity.getUserEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UserEntity user = userService.findByToken(token);

            UserContext.setCurrentUser(user);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return null;
    }

    private boolean ignoreUrl(HttpServletRequest request, String[] urlIgnore) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(urlIgnore).anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

}

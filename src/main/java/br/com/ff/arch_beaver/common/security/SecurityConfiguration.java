package br.com.ff.arch_beaver.common.security;

import br.com.ff.arch_beaver.common.security.constants.SecurityConfigurationConstants;
import br.com.ff.arch_beaver.common.security.filters.AuthenticationLoginFilter;
import br.com.ff.arch_beaver.common.security.filters.AuthorizationRequestFilter;
import br.com.ff.arch_beaver.common.security.utils.TokenJWTSecurity;
import br.com.ff.arch_beaver.modules.user.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Profile("!test")
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http,
                                                           PasswordEncoder passwordEncoder,
                                                           UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           TokenJWTSecurity tokenJWTSecurity,
                                           UserDetailsService userDetailsService,
                                           UserService userService,
                                           PasswordEncoder passwordEncoder) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
            authorizationManagerRequestMatcherRegistry
                .requestMatchers(SecurityConfigurationConstants.getPublicMatchersUrlIgnore()).permitAll()
                .anyRequest().authenticated()
        );

        http.addFilter(
            new AuthenticationLoginFilter(
                authenticationManagerBean(http, passwordEncoder, userDetailsService),
                tokenJWTSecurity,
                userService
            )
        );
        http.addFilter(
            new AuthorizationRequestFilter(
                authenticationManagerBean(http, passwordEncoder, userDetailsService),
                tokenJWTSecurity,
                userService,
                userDetailsService
            )
        );

        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

}

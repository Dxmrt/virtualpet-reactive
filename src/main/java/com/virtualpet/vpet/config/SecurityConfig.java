package com.virtualpet.vpet.config;

import com.virtualpet.vpet.util.JwtAuthenticationFilter;
import com.virtualpet.vpet.util.JwtUtil;
import com.virtualpet.vpet.repository.r2dbc.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;



@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Autowired
    public SecurityConfig(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login", "/virtualpet/register").permitAll()
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/webjars/**").permitAll()
                        .pathMatchers("/virtualpet/game-sessions/**").permitAll()
                        .pathMatchers("/virtualpet/users/**", "/virtualpet/accessories/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userRepository), SecurityWebFiltersOrder.AUTHENTICATION) // Cambiar aquí
                .build();
    }



    private Mono<AuthorizationDecision> authorize(Mono<Authentication> authenticationMono, AuthorizationContext context) {
        return authenticationMono.flatMap(authentication -> {
            // Verificamos si el usuario tiene el rol ADMIN
            boolean hasAdminRole = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            // Si el usuario tiene rol ADMIN, se le permite el acceso
            if (hasAdminRole) {
                return Mono.just(new AuthorizationDecision(true));
            }

            String sessionId = (String) context.getVariables().get("sessionId");

            // Si no tiene el rol ADMIN, denegamos el acceso
            return Mono.just(new AuthorizationDecision(false));
        });
    }
}

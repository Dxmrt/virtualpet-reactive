package com.virtualpet.vpet.util;

import com.virtualpet.vpet.repository.r2dbc.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null) {
                return userRepository.findByUsername(username)
                        .flatMap(user -> {
                            if (jwtUtil.validateToken(token, user.getPassword())) {
                                UsernamePasswordAuthenticationToken authentication =
                                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                                // Establecer el contexto de seguridad reactivo
                                return ReactiveSecurityContextHolder.getContext()
                                        .flatMap(securityContext -> {
                                            securityContext.setAuthentication(authentication);
                                            return chain.filter(exchange);
                                        });
                            }
                            return chain.filter(exchange);
                        });
            }
        }

        return chain.filter(exchange);
    }
}

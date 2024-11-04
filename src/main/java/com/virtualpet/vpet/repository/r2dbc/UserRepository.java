package com.virtualpet.vpet.repository.r2dbc;

import com.virtualpet.vpet.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Integer> {
    Mono<User> findByUsername(String username);
}

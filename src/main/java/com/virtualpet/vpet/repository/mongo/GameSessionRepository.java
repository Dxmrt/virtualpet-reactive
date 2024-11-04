package com.virtualpet.vpet.repository.mongo;

import com.virtualpet.vpet.model.GameSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameSessionRepository extends ReactiveMongoRepository<GameSession, String> {
    Flux<GameSession> findByUsername(String username);
}

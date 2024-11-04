package com.virtualpet.vpet.repository.mongo;

import com.virtualpet.vpet.model.Accessory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccessoryRepository extends ReactiveMongoRepository<Accessory, String> {
    Mono<Accessory> findByName(String name);
}
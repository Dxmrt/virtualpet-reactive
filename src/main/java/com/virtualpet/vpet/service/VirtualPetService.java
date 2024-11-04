package com.virtualpet.vpet.service;

import com.virtualpet.vpet.excepcions.PetNotFoundException;
import com.virtualpet.vpet.model.Accessory;
import com.virtualpet.vpet.model.GameSession;
import com.virtualpet.vpet.model.Pet;
import com.virtualpet.vpet.model.User;
import com.virtualpet.vpet.model.enums.PetType;
import com.virtualpet.vpet.repository.mongo.AccessoryRepository;
import com.virtualpet.vpet.repository.r2dbc.UserRepository;
import com.virtualpet.vpet.repository.mongo.GameSessionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

@Service
public class VirtualPetService {
    private final UserRepository userRepository;
    private final GameSessionRepository gameSessionRepository;
    private final AccessoryRepository accessoryRepository;

    public VirtualPetService(UserRepository userRepository, GameSessionRepository gameSessionRepository, AccessoryRepository accessoryRepository) {
        this.userRepository = userRepository;
        this.gameSessionRepository = gameSessionRepository;
        this.accessoryRepository = accessoryRepository;
    }

    public Mono<Object> createUser(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.error(new RuntimeException("Username already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setRole("ROLE_USER");
                    return userRepository.save(newUser);
                }));
    }


    public Mono<GameSession> createGameSession(String username, String petName, PetType petType) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    Pet pet = new Pet(petName, petType);
                    GameSession gameSession = new GameSession(username, pet);
                    gameSession.setCreationDate(LocalDateTime.now());
                    return gameSessionRepository.save(gameSession);
                });
    }


    public Flux<GameSession> getUserGameSessions(String username) {
        return gameSessionRepository.findByUsername(username);
    }

    public Mono<GameSession> feedPet(String sessionId) {
        return gameSessionRepository.findById(sessionId)
                .flatMap(session -> {
                    Pet pet = session.getPet();
                    if (pet == null) {
                        return Mono.error(new PetNotFoundException("Pet not found in game session"));
                    }
                    pet.feed();
                    return gameSessionRepository.save(session);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Session not found")));
    }


    public Mono<GameSession> dancePet(String sessionId) {
        return gameSessionRepository.findById(sessionId)
                .flatMap(session -> {
                    Pet pet = session.getPet();
                    if (pet == null) {
                        return Mono.error(new RuntimeException("Pet not found in game session"));
                    }
                    pet.dance();
                    return gameSessionRepository.save(session);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Session not found")));
    }

    public Mono<GameSession> bathePet(String sessionId) {
        return gameSessionRepository.findById(sessionId)
                .flatMap(session -> {
                    Pet pet = session.getPet();
                    if (pet == null) {
                        return Mono.error(new RuntimeException("Pet not found in game session"));
                    }
                    pet.bathe();
                    return gameSessionRepository.save(session);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Session not found")));
    }


    public Mono<GameSession> getPetStatus(String sessionId) {
        return gameSessionRepository.findById(sessionId)
                .flatMap(session -> {
                    Pet pet = session.getPet();
                    if (pet == null) {
                        return Mono.error(new RuntimeException("Pet not found in game session"));
                    }
                    return Mono.just(session);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Session not found")));
    }


    public Mono<GameSession> addAccessoryToPet(String sessionId, String accessoryName) {
        return gameSessionRepository.findById(sessionId)
                .flatMap(session -> {
                    Pet pet = session.getPet();
                    if (pet == null) {
                        return Mono.error(new RuntimeException("Pet not found in game session"));
                    }
                    return accessoryRepository.findByName(accessoryName)
                            .flatMap(accessory -> {
                                session.addAccessory(accessory);
                                return gameSessionRepository.save(session);
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Session or accessory not found")));
    }

    public Mono<GameSession> removeAccessoryFromPet(String sessionId, String accessoryName) {
        return gameSessionRepository.findById(sessionId)
                .flatMap(session -> {
                    Pet pet = session.getPet();
                    if (pet == null) {
                        return Mono.error(new RuntimeException("Pet not found in game session"));
                    }
                    return accessoryRepository.findByName(accessoryName)
                            .flatMap(accessory -> {
                                session.removeAccessory(accessory);
                                return gameSessionRepository.save(session);
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Session or accessory not found")));
    }

    public Mono<Accessory> createAccessory(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }
}

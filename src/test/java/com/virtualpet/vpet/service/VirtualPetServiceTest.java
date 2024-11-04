package com.virtualpet.vpet.service;

import com.virtualpet.vpet.model.GameSession;
import com.virtualpet.vpet.model.Accessory;
import com.virtualpet.vpet.model.Pet;
import com.virtualpet.vpet.model.User;
import com.virtualpet.vpet.model.enums.PetType;
import com.virtualpet.vpet.repository.mongo.AccessoryRepository;
import com.virtualpet.vpet.repository.mongo.GameSessionRepository;
import com.virtualpet.vpet.repository.r2dbc.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class VirtualPetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameSessionRepository gameSessionRepository;

    @Mock
    private AccessoryRepository accessoryRepository;

    @InjectMocks
    private VirtualPetService virtualPetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGameSession() {
        User user = new User();
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);

        when(userRepository.findByUsername(anyString())).thenReturn(Mono.just(user));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.createGameSession("username", "petName", PetType.SHRIMP);

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testGetUserGameSessions() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        when(gameSessionRepository.findByUsername(anyString())).thenReturn(Flux.just(gameSession));

        Flux<GameSession> result = virtualPetService.getUserGameSessions("username");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testFeedPet() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        when(gameSessionRepository.findById(anyString())).thenReturn(Mono.just(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.feedPet("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testDancePet() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        when(gameSessionRepository.findById(anyString())).thenReturn(Mono.just(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.dancePet("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testBathePet() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        when(gameSessionRepository.findById(anyString())).thenReturn(Mono.just(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.bathePet("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testGetPetStatus() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        when(gameSessionRepository.findById(anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.getPetStatus("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testCreateAccessory() {
        Accessory accessory = new Accessory();
        when(accessoryRepository.save(any(Accessory.class))).thenReturn(Mono.just(accessory));

        Mono<Accessory> result = virtualPetService.createAccessory(accessory);

        StepVerifier.create(result)
                .expectNext(accessory)
                .verifyComplete();
    }

    @Test
    public void testAddAccessoryToPet() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        Accessory accessory = new Accessory();
        when(gameSessionRepository.findById(anyString())).thenReturn(Mono.just(gameSession));
        when(accessoryRepository.findByName(anyString())).thenReturn(Mono.just(accessory));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.addAccessoryToPet("sessionId", "hat");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testRemoveAccessoryFromPet() {
        GameSession gameSession = new GameSession("username", new Pet("petName", PetType.SHRIMP));
        when(gameSessionRepository.findById(anyString())).thenReturn(Mono.just(gameSession));
        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetService.removeAccessoryFromPet("sessionId", "hat");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }
}

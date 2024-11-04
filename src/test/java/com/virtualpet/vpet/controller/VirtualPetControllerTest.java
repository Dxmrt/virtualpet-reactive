package com.virtualpet.vpet.controller;

import com.virtualpet.vpet.model.GameSession;
import com.virtualpet.vpet.model.Accessory;
import com.virtualpet.vpet.model.Pet;
import com.virtualpet.vpet.model.enums.PetType;
import com.virtualpet.vpet.service.VirtualPetService;
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

public class VirtualPetControllerTest {

    @Mock
    private VirtualPetService virtualPetService;

    @InjectMocks
    private VirtualPetController virtualPetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGameSession() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.createGameSession(anyString(), anyString(), any(PetType.class)))
                .thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.createGameSession("username", "petName", PetType.SHRIMP);

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testGetUserGameSessions() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.getUserGameSessions(anyString())).thenReturn(Flux.just(gameSession));

        Flux<GameSession> result = virtualPetController.getUserGameSessions("username");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testFeedPet() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.feedPet(anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.feedPet("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testDancePet() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.dancePet(anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.dancePet("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testBathePet() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.bathePet(anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.bathePet("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testGetPetStatus() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.getPetStatus(anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.getPetStatus("sessionId");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testCreateAccessory() {
        Accessory accessory = new Accessory();
        when(virtualPetService.createAccessory(any(Accessory.class))).thenReturn(Mono.just(accessory));

        Mono<Accessory> result = virtualPetController.createAccessory(accessory);

        StepVerifier.create(result)
                .expectNext(accessory)
                .verifyComplete();
    }

    @Test
    public void testAddAccessoryToPet() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.addAccessoryToPet(anyString(), anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.addAccessoryToPet("sessionId", "accessoryName");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }

    @Test
    public void testRemoveAccessoryFromPet() {
        Pet pet = new Pet("petName", PetType.SHRIMP);
        GameSession gameSession = new GameSession("username", pet);
        when(virtualPetService.removeAccessoryFromPet(anyString(), anyString())).thenReturn(Mono.just(gameSession));

        Mono<GameSession> result = virtualPetController.removeAccessoryFromPet("sessionId", "accessoryName");

        StepVerifier.create(result)
                .expectNext(gameSession)
                .verifyComplete();
    }
}

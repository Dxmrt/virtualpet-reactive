package com.virtualpet.vpet.controller;

import com.virtualpet.vpet.model.GameSession;
import com.virtualpet.vpet.service.VirtualPetService;
import com.virtualpet.vpet.model.Accessory;
import com.virtualpet.vpet.model.enums.PetType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/virtualpet")
@RequiredArgsConstructor
@Tag(name = "Virtual Pet Controller", description = "Controller for managing Virtual Pet game sessions")
public class VirtualPetController {
    private final VirtualPetService virtualPetService;


    @PostMapping("/game-sessions")
    @Operation(summary = "Creates a pet and game session", description = "Creates a pet and game session on MongoDB")
    public Mono<GameSession> createGameSession(@RequestParam String username,
                                               @RequestParam String petName,
                                               @RequestParam PetType petType) {
        return virtualPetService.createGameSession(username, petName, petType);
    }

    @GetMapping("/game-sessions/{username}")
    @Operation(summary = "Get a game", description = "Fetch a game by its ID on MongoDB")
    public Flux<GameSession> getUserGameSessions(@PathVariable String username) {
        return virtualPetService.getUserGameSessions(username);
    }

    @PostMapping("/game-sessions/{sessionId}/feed")
    @Operation(summary = "Feed your pet", description = "Feeds your pet and identifies your sessionid")
    public Mono<GameSession> feedPet(@PathVariable String sessionId) {
        return virtualPetService.feedPet(sessionId);
    }

    @PostMapping("/game-sessions/{sessionId}/dance")
    @Operation(summary = "Dance with your pet", description = "Dance with your pet, identifies your sessionid")
    public Mono<GameSession> dancePet(@PathVariable String sessionId) {
        return virtualPetService.dancePet(sessionId);
    }

    @PostMapping("/game-sessions/{sessionId}/bathe")
    @Operation(summary = "Take a shower", description = "Clean your pet, identifies sessionid")
    public Mono<GameSession> bathePet(@PathVariable String sessionId) {
        return virtualPetService.bathePet(sessionId);
    }

    @GetMapping("/game-sessions/{sessionId}/status")
    @Operation(summary = "Gets the stats", description = "Gets the stats of your pet")
    public Mono<GameSession> getPetStatus(@PathVariable String sessionId) {
        return virtualPetService.getPetStatus(sessionId);
    }

    @PostMapping("/accessories")
    @Operation(summary = "Creates an accessory", description = "Creates a hat, glasses or collar")
    public Mono<Accessory> createAccessory(@RequestBody Accessory accessory) {
        return virtualPetService.createAccessory(accessory);
    }

    @PostMapping("/game-sessions/{sessionId}/accessories/{accessoryName}")
    @Operation(summary = "Wear an accessory", description = "Places a hat, glasses or collar to your pet")
    public Mono<GameSession> addAccessoryToPet(@PathVariable String sessionId, @PathVariable String accessoryName) {
        return virtualPetService.addAccessoryToPet(sessionId, accessoryName);
    }

    @DeleteMapping("/game-sessions/{sessionId}/accessories/{accessoryName}")
    @Operation(summary = "Delete accessory", description = "Deletes accessory by name")
    public Mono<GameSession> removeAccessoryFromPet(@PathVariable String sessionId, @PathVariable String accessoryName) {
        return virtualPetService.removeAccessoryFromPet(sessionId, accessoryName);
    }
}

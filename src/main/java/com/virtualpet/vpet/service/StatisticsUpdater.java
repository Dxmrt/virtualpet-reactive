package com.virtualpet.vpet.service;

import com.virtualpet.vpet.model.GameSession;
import com.virtualpet.vpet.repository.mongo.GameSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StatisticsUpdater {

    private final GameSessionRepository gameSessionRepository;

    public StatisticsUpdater(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void updateAllPetStatistics() {
        gameSessionRepository.findAll()
                .flatMap(this::updatePetStatistics)
                .subscribe();
    }

    private Mono<GameSession> updatePetStatistics(GameSession session) {
        session.getPet().increaseHunger(1);
        session.getPet().decreaseHappiness(1);
        session.getPet().decreaseCleanliness(1);

        return gameSessionRepository.save(session);
    }


}

package com.virtualpet.vpet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "game_sessions")
public class GameSession {
    private String username;
    private Pet pet;
    private LocalDateTime creationDate;
    private List<Accessory> accessories = new ArrayList<>();

    public GameSession(String username, Pet pet) {
        this.username = username;
        this.pet = pet;
        this.creationDate = LocalDateTime.now();
    }

    public void addAccessory(Accessory accessory) {
        this.accessories.add(accessory);
    }

    public void removeAccessory(Accessory accessory) {
        this.accessories.remove(accessory);
    }
}

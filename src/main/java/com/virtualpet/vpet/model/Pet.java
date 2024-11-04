package com.virtualpet.vpet.model;


import com.virtualpet.vpet.model.enums.PetType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class Pet {
    private String name;
    private PetType type;
    private int hunger;
    private int cleanliness;
    private int happiness;

    public Pet(String name, PetType type) {
        this.name = name;
        this.type = type;
        this.hunger = 100;
        this.cleanliness = 100;
        this.happiness = 100;
    }

    public void feed() {
        this.hunger = Math.min(100, this.hunger + 30);
        this.happiness = Math.min(100, this.happiness + 10);
    }

    public void dance() {
        this.happiness = Math.min(100, this.happiness + 20);
        this.hunger = Math.max(0, this.hunger - 10);
    }

    public void bathe() {
        this.cleanliness = 100;
        this.happiness = Math.min(100, this.happiness + 15);
    }

    public void increaseHunger(int amount) {
        this.hunger = Math.max(0, Math.min(100, this.hunger - amount));
    }

    public void decreaseHappiness(int amount) {
        this.happiness = Math.max(0, Math.min(100, this.happiness - amount));
    }

    public void decreaseCleanliness(int amount) {
        this.cleanliness = Math.max(0, Math.min(100, this.cleanliness - amount));
    }
}

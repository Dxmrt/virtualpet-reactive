package com.virtualpet.vpet.excepcions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String petId) {
        super("Pet with ID " + petId + " not found.");
    }
}

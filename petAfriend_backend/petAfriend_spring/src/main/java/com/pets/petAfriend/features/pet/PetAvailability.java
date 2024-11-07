package com.pets.petAfriend.features.pet;

import lombok.Getter;

@Getter
public enum PetAvailability {

    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE");

    PetAvailability(final String avail) {
        this.avail = avail;
    }

    private String avail;
}

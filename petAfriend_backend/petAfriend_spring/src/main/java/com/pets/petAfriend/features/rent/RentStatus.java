package com.pets.petAfriend.features.rent;

import lombok.Getter;

@Getter
public enum RentStatus {
    CREATED("CREATED"), STARTED("STARTED"), CLOSED("CLOSED"), CANCELED("CANCELED");

    private final String status;

    RentStatus(String status) {
        this.status = status;
    }

}

package com.pets.petAfriend.features.rent;

import lombok.Getter;

import java.io.Serial;

@Getter
public class RentException extends Exception {

    @Serial
    private static final long serialVersionUID = 857737211986736347L;

    private Integer code;

    public RentException(final String message, int code) {
        super(message);
        this.code = code;
    }

    public RentException(String message) {
        super(message);
        this.code = 400;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

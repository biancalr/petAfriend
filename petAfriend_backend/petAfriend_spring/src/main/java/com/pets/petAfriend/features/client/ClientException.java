package com.pets.petAfriend.features.client;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ClientException extends Exception {

    @Serial
    private static final long serialVersionUID = -1411390142114946205L;

    private Integer code;

    public ClientException(final String message, int code) {
        super(message);
        this.code = code;
    }

    public ClientException(String message) {
        super(message);
        this.code = 400;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

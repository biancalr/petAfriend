package com.pets.petAfriend.features.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisteredDTO {

    private String id;
    private Integer status;
    private String message;
    private String entity;
}

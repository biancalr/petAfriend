package com.pets.petAfriend.features.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisteredDTO {

    private Long id;
    private Integer status;
    private String message;
    private String entity;
}

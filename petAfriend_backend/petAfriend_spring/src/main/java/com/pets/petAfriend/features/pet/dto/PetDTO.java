package com.pets.petAfriend.features.pet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetDTO {

    private Long id;
    private String name;
    private String specie;
    private String breed;
    private String personality;
    private String status;
}

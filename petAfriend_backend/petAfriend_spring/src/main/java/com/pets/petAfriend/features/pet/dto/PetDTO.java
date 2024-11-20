package com.pets.petAfriend.features.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

    private String id;
    private String name;
    private String specie;
    private String breed;
    private String personality;
    private String status;
}

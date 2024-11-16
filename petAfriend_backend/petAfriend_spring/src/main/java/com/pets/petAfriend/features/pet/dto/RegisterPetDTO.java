package com.pets.petAfriend.features.pet.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPetDTO {

    @NotBlank(message = "validation.pet.nameNotBlank")
    @Size(min = 1, max = 30, message = "limite [1:30]")
    @Parameter(description = "The pet's name")
    private String name;
    @NotBlank(message = "validation.pet.typeNotBlank")
    @Size(min = 1, max = 30, message = "limite [1:30]")
    @Parameter(description = "the specie of your pet", example = "dog")
    private String specie;
    @NotBlank(message = "validation.pet.specieNotBlank")
    @Size(min = 1, max = 100, message = "limite [1:100]")
    @Parameter(description = "the breed of your pet", example = "Golden Retriever")
    private String breed;
    @Size(min = 1, max = 5000, message = "limite [1:5000]")
    @NotBlank(message = "validation.pet.personalityNotBlank")
    @Parameter(description = "describe the pet personality")
    private String personality;

}

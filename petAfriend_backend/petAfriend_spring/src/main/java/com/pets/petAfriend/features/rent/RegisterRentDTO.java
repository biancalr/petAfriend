package com.pets.petAfriend.features.rent;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRentDTO {

    @Min(value = 1, message = "Minimal time rent is 1")
    @Max(value = 60 * 24 * 30 * 365, message = "Time rent too long")
    private Integer hours;
    private String clientId;
    private String petId;
    private String startsAt;

}

package com.pets.petAfriend.features.client.dto;

import jakarta.validation.constraints.Email;
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
public class RegisterClientDTO {

    @NotBlank(message = "validation.client.usernameNotBlank")
    @Size(min = 1, max = 20, message = "limit [1:20]")
    private String username;
    @NotBlank(message = "validation.client.emailNotBlank")
    @Size(min = 1, max = 50, message = "limit [1:50]")
    @Email(message = "validation.client.mailNotValid")
    private String email;

}

package com.pets.petAfriend.features.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {

    private Long id;
    private String username;
    private String email;

}

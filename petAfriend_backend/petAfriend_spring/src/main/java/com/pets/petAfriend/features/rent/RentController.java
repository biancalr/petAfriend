package com.pets.petAfriend.features.rent;

import com.pets.petAfriend.features.client.ClientException;
import com.pets.petAfriend.features.pet.PetException;
import com.pets.petAfriend.features.shared.ApiExamples;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
@Slf4j
public class RentController {

    private RentService service;

    @Operation(summary = "Register a rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rent registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisteredDTO.class),
                            examples = @ExampleObject(value = ApiExamples.RENT_RESPONSE_REGISTERED_DTO_SUCCESS)
                    )}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = {@Content(mediaType = "application/json",
                            examples = @ExampleObject(value = ApiExamples.RENT_RESPONSE_REGISTERED_DTO_ERROR))}),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/register")
    public ResponseEntity<RegisteredDTO> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data of a rent", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRentDTO.class),
                            examples = @ExampleObject(value = ApiExamples.RENT_REQUEST_REGISTER_DTO_SUCCESS)))
            @RequestBody @Valid final RegisterRentDTO rentDTO) throws PetException, ClientException, RentException, ParseException {
        log.info("{} requested a rent to {} during {}", rentDTO.getClientId(), rentDTO.getPetId(), rentDTO.getHours());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(rentDTO));
    }

    @Operation(summary = "Cancel a rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rent canceled successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisteredDTO.class),
                            examples = @ExampleObject(value = ApiExamples.RENT_RESPONSE_CANCELED_DTO_SUCCESS)
                    )}),
            @ApiResponse(responseCode = "400", description = "Rent not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/cancel")
    public ResponseEntity<RegisteredDTO> cancel(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Id of a rent",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)
                    ),}
            )
            @RequestBody final Long idRent) throws RentException {
        log.info("Canceling request of rent id {}", idRent);
        return ResponseEntity.status(HttpStatus.OK).body(service.cancel(idRent));
    }

}

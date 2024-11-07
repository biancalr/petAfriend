package com.pets.petAfriend.features.pet;

import com.pets.petAfriend.features.pet.dto.PetDTO;
import com.pets.petAfriend.features.pet.dto.RegisterPetDTO;
import com.pets.petAfriend.features.shared.ApiExamples;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Slf4j
public class PetController {

    private final PetService service;

    @Operation(summary = "Register a pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisteredDTO.class),
                            examples = @ExampleObject(value = ApiExamples.PET_RESPONSE_REGISTERED_DTO_SUCCESS)
                    )}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = {@Content(mediaType = "application/json",
                            examples = @ExampleObject(value = ApiExamples.RENT_RESPONSE_REGISTERED_DTO_ERROR))}),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping(path = "/register")
    public ResponseEntity<RegisteredDTO> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Pet to register", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterPetDTO.class),
                            examples = @ExampleObject(value = ApiExamples.PET_REQUEST_REGISTER_DTO_SUCCESS)))
            @RequestBody @Valid final RegisterPetDTO petDTO) {
        log.info("Register the pet {}", petDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(petDTO));
    }

    @Operation(summary = "list the pets based on filters of specie and breed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pets listed according to filter",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PetDTO.class)),
                            examples = @ExampleObject(value = ApiExamples.PET_RESPONSE_LIST_PAGE_SUCESS)
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid value for parameter 'availability'",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = ApiExamples.PET_RESPONSE_LIST_PAGE_ERROR))),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping(path = "/show")
    public ResponseEntity<Page<PetDTO>> show(
            @Parameter(description = "The availability status to search",
                    schema = @Schema(implementation = PetAvailability.class)) @RequestParam(required = false, defaultValue = "AVAILABLE") final String status,
            @Parameter(description = "The specie to search", example = "dog") @RequestParam(required = false) final String specie,
            @ParameterObject Pageable pageable
    ) throws PetException {
        log.info("listing all {} pets", status);
        return ResponseEntity.ok(service.list(status, specie, pageable));
    }

    @Operation(summary = "Find the pet with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find a Pet with given Id", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PetDTO.class),
                    examples = @ExampleObject(value = ApiExamples.PET_RESPONSE_DTO_SUCCESS)
            )),
            @ApiResponse(responseCode = "400", description = "Pet with given id not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping(path = "/get")
    public ResponseEntity<PetDTO> get(
            @Parameter(description = "The id to search") @RequestParam(required = false) final Long id) throws PetException {
        log.info("listing pet with id {}", id);
        return ResponseEntity.ok(service.get(id));
    }

}

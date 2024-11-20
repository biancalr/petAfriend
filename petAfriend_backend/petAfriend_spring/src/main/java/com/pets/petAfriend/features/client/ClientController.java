package com.pets.petAfriend.features.client;

import com.pets.petAfriend.features.client.dto.ClientDTO;
import com.pets.petAfriend.features.client.dto.RegisterClientDTO;
import com.pets.petAfriend.features.shared.ApiExamples;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService service;

    @Operation(summary = "Register a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisteredDTO.class),
                            examples = @ExampleObject(value = ApiExamples.CLIENT_RESPONSE_REGISTERED_DTO_SUCCESS)
                    )}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = {@Content(mediaType = "application/json",
                            examples = @ExampleObject(value = ApiExamples.CLIENT_RESPONSE_REGISTERED_DTO_ERROR)
                    )}),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client to register", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterClientDTO.class),
                            examples = @ExampleObject(value = ApiExamples.CLIENT_REQUEST_REGISTER_DTO_SUCCESS)))
            @RequestBody @Valid RegisterClientDTO clientDto) throws ClientException {
        log.info("register client {}", clientDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(clientDto));
    }

    @Operation(summary = "Find the client with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find a Client with given Id", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ClientDTO.class),
                    examples = @ExampleObject(value = ApiExamples.CLIENT_RESPONSE_DTO_SUCCESS))),
            @ApiResponse(responseCode = "400", description = "Client with given id not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "Data Integrity violation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping(path = "/get")
    public ResponseEntity<ClientDTO> get(
            @Parameter(description = "The id to search") @RequestParam final String id) throws ClientException {
        log.info("listing client with id {}", id);
        return ResponseEntity.ok(service.get(id));
    }

}

package com.pets.petAfriend.features.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import com.pets.petAfriend.features.client.dto.ClientDTO;
import com.pets.petAfriend.features.client.dto.RegisterClientDTO;
import com.pets.petAfriend.features.pet.dto.RegisterPetDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@AutoConfigureMockMvc
@WebMvcTest(ClientController.class)
public class ClientControllerTests {

    @MockBean
    private ClientController controller;

    @Autowired
    private MockMvc mockMvc;

    private final String CLIENT_API = "/api/clients";

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Should return a valid client with given id")
    @Test
    public void getClientDetails() throws Exception {

        // cenário
        final ClientDTO dto = createDetailedClient();
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";
        final String uri = CLIENT_API + "/get";
        dto.setId(id);

        BDDMockito.given(controller.get(id))
                .willReturn(ResponseEntity.ok(dto));

        // execução
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(uri).queryParam("id", id);

        // verificação
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("username").value(dto.getUsername()))
                .andExpect(jsonPath("email").value(dto.getEmail()))
        ;

    }

    @DisplayName("Should throw a PetException when not found")
    @Test
    public void getClientDetailsShouldReturnClientExceptionClientNotExist() throws Exception {

        // cenário
        final String id = UUID.randomUUID().toString();
        final String uri = CLIENT_API + "/get";

        BDDMockito.given(controller.get(Mockito.anyString()))
                .willThrow(new ClientException("Client does not exist"));

        // execução
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(uri).queryParam("id", id);

        // verificação
        final var thrown = Assertions.assertThrows(Exception.class,
                () -> mockMvc.perform(request));
        Assertions.assertTrue(thrown.getMessage().contains("Client does not exist"));

    }

    @DisplayName("Should register a Client successfully")
    @Test
    public void registerClientSuccessfully() throws Exception {

        // cenário
        final String json, uri = CLIENT_API + "/register";
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";
        final RegisterClientDTO clientDTO = createNewClient();
        final RegisteredDTO savedClient = RegisteredDTO.builder()
                .id(id)
                .status(201)
                .message("success")
                .entity("Client")
                .build();

        // execução
        BDDMockito.given(controller.register(Mockito.any(RegisterClientDTO.class)))
                .willReturn(ResponseEntity.status(201).body(savedClient));

        json = new ObjectMapper().writeValueAsString(clientDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        // verificação
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.entity").value("Client"))
        ;
    }

    @DisplayName("Deve laçar erro de validação quando não houver dados suficiente para registrar um cliente")
    @Test
    public void registerInvalidClient() throws Exception {
        final String uri = CLIENT_API + "/register";
        final String json = new ObjectMapper().writeValueAsString(new RegisterClientDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result -> Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));
    }

    private RegisterClientDTO createNewClient() {
        final RegisterClientDTO dto = new RegisterClientDTO();
        dto.setEmail(Faker.instance().internet().emailAddress());
        dto.setUsername(Faker.instance().name().username());
        return dto;
    }

    private ClientDTO createDetailedClient() {
        final ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail(Faker.instance().internet().emailAddress());
        clientDTO.setUsername(Faker.instance().name().username());
        return clientDTO;
    }

}

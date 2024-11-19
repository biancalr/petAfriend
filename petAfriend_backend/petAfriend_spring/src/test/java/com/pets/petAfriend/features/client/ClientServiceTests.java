package com.pets.petAfriend.features.client;

import com.github.javafaker.Faker;
import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import com.pets.petAfriend.features.client.dto.ClientDTO;
import com.pets.petAfriend.features.client.dto.RegisterClientDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@SpringBootTest
public class ClientServiceTests {

    @MockBean
    private ClientService service;

    @Test
    void contextLoads() {
        assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("Should return a valid client with given id")
    public void getClientDetails() throws Exception {

        // cenário
        final Long id = 2L;

        final ClientDTO client = createDetailedClient();
        client.setId(id);

        Mockito.when(service.get(id)).thenReturn(client);

        // execução
        final var foundClient = service.get(id);

        // verificação
        assertThat(foundClient.getId()).isEqualTo(id);
        assertThat(foundClient.getUsername()).isEqualTo(client.getUsername());
        assertThat(foundClient.getEmail()).isEqualTo(client.getEmail());

    }

    @DisplayName("Should throw a ClientException when not found")
    @Test
    public void getClientDetailsShouldReturnClientExceptionClientNotExist() throws Exception {

        // cenário
        final Long id = 2L;

        Mockito.when(service.get(id)).thenThrow(new ClientException("Client does not exist"));

        // execução
        Throwable exception = Assertions.catchException(() -> service.get(id));

        // verificação
        assertThat(exception)
                .isInstanceOf(ClientException.class)
                .hasMessage("Client does not exist");
    }

    @DisplayName("Should register a Client successfully")
    @Test
    public void registerClientSuccessfully() throws Exception {

        // cenário
        final Long id = 1L;
        final RegisterClientDTO dto = createNewClient();
        final RegisteredDTO saved = RegisteredDTO.builder()
                .id(id)
                .status(201)
                .message("success")
                .entity("Client")
                .build();

        Mockito.when(service.save(dto)).thenReturn(saved);

        // execução
        final RegisteredDTO result = service.save(dto);

        // verificação
        org.junit.jupiter.api.Assertions.assertEquals(saved.getId(), result.getId());
        org.junit.jupiter.api.Assertions.assertEquals(saved.getStatus(), result.getStatus());
        assertThat(saved.getEntity()).isEqualTo(result.getEntity());
        assertThat(saved.getMessage()).isEqualTo(result.getMessage());
    }

    @DisplayName("Deve laçar erro de validação quando não houver dados suficiente para registrar um pet")
    @Test
    public void registerInvalidPet() throws Exception {

        // cenário
        final RegisterClientDTO dto = new RegisterClientDTO();
        log.info("> DTO {}", dto);

        Mockito.when(service.save(Mockito.any(RegisterClientDTO.class)))
                .thenThrow(new ClientException("Client email is blank;Client username is blank"));

        // execução
        final Throwable exception = Assertions.catchException(() -> service.save(dto));

        // verificação
        log.info("> Exception {}", exception.getMessage());
        assertThat(exception)
                .isInstanceOf(ClientException.class);

        org.junit.jupiter.api.Assertions.assertEquals(
                2, exception.getMessage().split(";").length)
        ;

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

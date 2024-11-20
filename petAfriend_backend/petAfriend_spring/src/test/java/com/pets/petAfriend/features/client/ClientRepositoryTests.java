package com.pets.petAfriend.features.client;

import com.github.javafaker.Faker;
import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestPropertySource(value = {"file:src/test/resources/application-test.properties"}, encoding = "UTF-8")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@DataJpaTest
@EnableAutoConfiguration
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository repository;

    private final Date now = new Date();

    @Test
    public void contextLoads() {
        assertThat(repository).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um cliente na base com o id informado")
    public void returnTrueWhenClientExists() {

        // cenário
        final Client client = createNewClient();
        final var saved = repository.saveAndFlush(client);

        // execução
        boolean exists = repository.existsById(saved.getId());

        // verificação
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando existir um client na base com o id informado")
    public void returnFalseWhenClientDoesNotExists() {

        // cenário
        final UUID id = UUID.randomUUID();

        // execução
        boolean exists = repository.existsById(id);

        // verificação
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um cliente na base com o username informado")
    public void returnTrueWhenClientUsernameExists() {

        // cenário
        final Client client = createNewClient();
        repository.saveAndFlush(client);

        // execução
        boolean exists = repository.existsByUsername(client.getUsername());

        // verificação
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando existir um client na base com o id informado")
    public void returnFalseWhenClientUsernameDoesNotExist() {

        // cenário
        final String username = Faker.instance().name().username();

        // execução
        boolean exists = repository.existsByUsername(username);

        // verificação
        assertThat(exists).isFalse();
    }

    @DisplayName("Should register a Client successfully")
    @Test
    public void registerClientSuccessfully() throws Exception {
        // cenário
        final Client client = createNewClient();

        // execução
        final var saved = repository.saveAndFlush(client);

        // verificação
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isEqualTo(now.toInstant());
    }

    private Client createNewClient() {
        final Client client = new Client();
        client.setEmail(Faker.instance().internet().emailAddress());
        client.setUsername(Faker.instance().name().username());
        client.setCreatedAt(now);
        return client;
    }

}

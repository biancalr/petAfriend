package com.pets.petAfriend.features.pet.configs.validations;

import com.pets.petAfriend.features.pet.configs.migrations.FlywayCallbackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(value = {"file:src/test/resources/application-test.properties"}, encoding = "UTF-8")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@SpringBootTest
@Slf4j
public class PropertiesValidationUnitTests {

    @Value("${server.port}")
    private String port;

    @Value("${validation.pet.nameNotBlank}")
    private String petNameNotBlank;

    @Value("${validation.pet.typeNotBlank}")
    private String petTypeNotBlank;

    @Value("${validation.pet.specieNotBlank}")
    private String petSpecieNotBlank;

    @Value("${validation.pet.personalityNotBlank}")
    private String petPersonalityNotBlank;

    @Value("${validation.client.usernameNotBlank}")
    private String clientUsernameNotBlank;

    @Value("${validation.client.emailNotBlank}")
    private String clientEmailNotBlank;

    @Value("${validation.client.mailNotValid}")
    private String clientMailNotValid;

    @DisplayName("Should show the port configured")
    @Test
    public void getServerPort() {
        assertEquals("8087", port);
    }

    @DisplayName("Should validate all validation messages")
    @Test
    public void getValidationMessages(){
        assertEquals("O nome do pet é obrigatório", petNameNotBlank);
        assertEquals("A espécie é obrigatória", petTypeNotBlank);
        assertEquals("A raça é obrigatória", petSpecieNotBlank);
        assertEquals("A personalidade é obrigatória", petPersonalityNotBlank);
        assertEquals("O nome do usuário é obrigatório", clientUsernameNotBlank);
        assertEquals("O e-mail é obrigatório", clientEmailNotBlank);
        assertEquals("Formato do e-mail inválido", clientMailNotValid);
    }

}

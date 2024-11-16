package com.pets.petAfriend.features.pet;

import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import com.pets.petAfriend.features.pet.dto.PetDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@SpringBootTest
public class PetServiceTests {

    @MockBean
    private PetService service;

    @MockBean
    private PetRepository repository;

    private final Date now = new Date();

    @Test
    void contextLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    @Test
    @DisplayName("Deve obter um pet por id")
    public void getByIdTest() throws PetException {
        final Long id = 2L;

        final Pet pet = createValidPet();
        pet.setId(id);

        Mockito.when(service.get(id)).thenReturn(toDTO(pet));

        final PetDTO foundPet = service.get(id);

        assertThat(foundPet.getId()).isEqualTo(id);
        assertThat(foundPet.getName()).isEqualTo(pet.getName());
        assertThat(foundPet.getSpecie()).isEqualTo(pet.getSpecie());
        assertThat(foundPet.getBreed()).isEqualTo(pet.getBreed());
        assertThat(foundPet.getPersonality()).isEqualTo(pet.getPersonality());
        assertThat(foundPet.getStatus()).isEqualTo(pet.getStatus());

    }

    private Pet createValidPet() {
        final Pet pet = new Pet();
        pet.setName("Catty");
        pet.setBreed("Scottish Fold");
        pet.setPersonality("quiet");
        pet.setSpecie("cat");
        pet.setCreatedAt(now);
        pet.setStatus("AVAILABLE");
        return pet;
    }

    private PetDTO toDTO(final Pet pet) {
        return PetDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .breed(pet.getBreed())
                .specie(pet.getSpecie())
                .personality(pet.getPersonality())
                .status(pet.getStatus())
                .build();
    }

}

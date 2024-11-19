package com.pets.petAfriend.features.pet;

import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import com.pets.petAfriend.features.pet.dto.PetDTO;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestPropertySource(value = {"file:src/test/resources/application-test.properties"}, encoding = "UTF-8")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@DataJpaTest
@EnableAutoConfiguration
public class PetRepositoryTests {

    @Autowired
    private PetRepository repository;

    private final Date now = new Date();

    @Test
    public void contextLoads() {
        assertThat(repository).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um pet na base com o id informado")
    public void returnTrueWhenPetExists() {

        // cenário
        final Pet pet = createNewPet();
        final Long id = 1L;

        pet.setCreatedAt(now);
        repository.saveAndFlush(pet);

        // execução
        boolean exists = repository.existsById(id);

        // verificação
        assertThat(exists).isTrue();

    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um pet na base com o id informado")
    public void returnFalseWhenPetDoesNotExists() {

        // cenário
        final Long id = 1L;

        // execução
        boolean exists = repository.existsById(id);

        // verificação
        assertThat(exists).isFalse();

    }

    @Test
    @DisplayName("Should return a valid pet with given id")
    public void getPetDetails() throws PetException {

        // cenário
        final Long id = 1L;
        final Pet pet = createNewPet();
        repository.save(pet);

        // execução
        final var foundPet = repository.findById(id);

        // verificação
        assertThat(foundPet.isPresent()).isTrue();

    }

    @DisplayName("Should register a Pet successfully")
    @Test
    public void registerPetSuccessfully() throws Exception {

        // cenário
        final Pet pet = createNewPet();

        // execução
        final var savedPet = repository.saveAndFlush(pet);

        // verificação
        assertThat(savedPet.getId()).isNotNull();
        assertThat(savedPet.getCreatedAt()).isEqualTo(now.toInstant());
    }

    @DisplayName("Should List The Pets Based of a Filter")
    @Test
    public void shouldListThePetsBasedOnFilter() throws Exception {

        // cenário
        final Pet pet = createNewPet();
        final Pet petExample = new Pet();
        petExample.setStatus("AVAILABLE");
        petExample.setSpecie("cat");
        petExample.setBreed("Scottish Fold");

        final List<Pet> list = new ArrayList<>();
        final Example<Pet> example = Example.of(petExample);

        final PageRequest pageRequest = PageRequest.of(0, 10);
        final Page<Pet> page = new PageImpl<>(list, pageRequest, 1);
        final Pageable pageable = page.getPageable();

        repository.saveAndFlush(pet);

        // execução
        final var foundPet = repository.findAll(example, pageable);

        // verificação
        assertThat(foundPet.getTotalElements()).isEqualTo(1);
    }

    private Pet createNewPet() {
        final Pet pet = new Pet();
        pet.setName("Catty");
        pet.setBreed("Scottish Fold");
        pet.setPersonality("quiet");
        pet.setSpecie("cat");
        pet.setStatus("AVAILABLE");
        pet.setCreatedAt(now);
        return pet;
    }

}

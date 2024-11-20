package com.pets.petAfriend.features.pet;

import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import com.pets.petAfriend.features.pet.dto.PetDTO;
import com.pets.petAfriend.features.pet.dto.RegisterPetDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@SpringBootTest
public class PetServiceTests {

    @MockBean
    private PetService service;

    @Test
    void contextLoads() {
        assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("Should return a valid pet with given id")
    public void getPetDetails() throws PetException {

        // cenário
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";

        final PetDTO pet = createDetailedPet();
        pet.setId(id);

        Mockito.when(service.get(id)).thenReturn(pet);

        // execução
        final var foundPet = service.get(id);

        // verificação
        assertThat(foundPet.getId()).isEqualTo(id);
        assertThat(foundPet.getName()).isEqualTo(pet.getName());
        assertThat(foundPet.getSpecie()).isEqualTo(pet.getSpecie());
        assertThat(foundPet.getBreed()).isEqualTo(pet.getBreed());
        assertThat(foundPet.getPersonality()).isEqualTo(pet.getPersonality());
        assertThat(foundPet.getStatus()).isEqualTo(pet.getStatus());

    }

    @DisplayName("Should throw a PetException when not found")
    @Test
    public void getPetDetailsShouldReturnPetExceptionPetNotExist() throws Exception {

        // cenário
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";

        Mockito.when(service.get(id))
                .thenThrow(new PetException("Pet does not exist"));

        // execução
        Throwable exception = Assertions.catchException(() -> service.get(id));

        // verificação
        assertThat(exception)
                .isInstanceOf(PetException.class)
                .hasMessage("Pet does not exist");

    }

    @DisplayName("Should List The Pets Based of a Filter")
    @Test
    public void shouldListThePetsBasedOnFilter() throws Exception {

        // cenário
        final PetDTO pet = createDetailedPet();
        final String status = "AVAILABLE";
        final String specie = "cat";
        final List<PetDTO> list = new ArrayList<>();

        list.add(pet);

        final PageRequest pageRequest = PageRequest.of(0, 10);
        final Page<PetDTO> page = new PageImpl<>(list, pageRequest, 1);

        Mockito.when(service.list(Mockito.anyString(), Mockito.anyString(), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        // execução
        Page<PetDTO> result = service.list(status, specie, pageRequest);

        // verificação
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(list);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @DisplayName("Should register a Pet successfully")
    @Test
    public void registerPetSuccessfully() throws Exception {

        // cenário
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";
        final RegisterPetDTO petDTO = createNewPet();
        final RegisteredDTO savedPet = RegisteredDTO.builder()
                                                    .id(id)
                                                    .status(201)
                                                    .message("success")
                                                    .entity("Pet")
                                                    .build();
        Mockito.when(service.save(petDTO)).thenReturn(savedPet);

        // execução
        final RegisteredDTO result = service.save(petDTO);

        // verificação
        org.junit.jupiter.api.Assertions.assertEquals(savedPet.getId(), result.getId());
        org.junit.jupiter.api.Assertions.assertEquals(savedPet.getStatus(), result.getStatus());
        assertThat(savedPet.getEntity()).isEqualTo(result.getEntity());
        assertThat(savedPet.getMessage()).isEqualTo(result.getMessage());
    }

    @DisplayName("Deve laçar erro de validação quando não houver dados suficiente para registrar um pet")
    @Test
    public void registerInvalidPet() throws Exception {

        // cenário
        final RegisterPetDTO petDTO = new RegisterPetDTO();

        Mockito.when(service.save(Mockito.any(RegisterPetDTO.class)))
                .thenThrow(new PetException("Pet name is blank;"+
                        "Pet specie is blank;"+
                        "Pet breed is blank;"+
                        "Pet personality is blank"));

        // execução
        final Throwable exception = Assertions.catchException(() -> service.save(petDTO));

        // verificação
        assertThat(exception)
                .isInstanceOf(PetException.class)
        ;

        org.junit.jupiter.api.Assertions.assertEquals(
                4, exception.getMessage().split(";").length)
        ;
    }

    private PetDTO createDetailedPet() {
        final PetDTO pet = new PetDTO();
        pet.setName("Catty");
        pet.setBreed("Scottish Fold");
        pet.setPersonality("quiet");
        pet.setSpecie("cat");
        pet.setStatus("AVAILABLE");
        return pet;
    }

    private RegisterPetDTO createNewPet() {
        final RegisterPetDTO pet = new RegisterPetDTO();
        pet.setName("Doggy");
        pet.setSpecie("dog");
        pet.setBreed("Shitzu");
        pet.setPersonality("bright");
        return pet;
    }

}

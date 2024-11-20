package com.pets.petAfriend.features.pet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.pets.petAfriend.configs.migrations.FlywayCallbackTestConfig;
import com.pets.petAfriend.features.pet.dto.PetDTO;
import com.pets.petAfriend.features.pet.dto.RegisterPetDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
@AutoConfigureMockMvc
@WebMvcTest(PetController.class)
public class PetControllerTests {

    @MockBean
    private PetController controller;

    @Autowired
    private MockMvc mockMvc;

    private final String PET_API = "/api/pets";

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Should return a valid pet with given id")
    @Test
    public void getPetDetails() throws Exception {
        final PetDTO dto = getDetailedPet();
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";
        final String uri = PET_API + "/get";
        dto.setId(id);

        BDDMockito.given(controller.get(id)).willReturn(ResponseEntity.ok(dto));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(uri).queryParam("id", id);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(dto.getName()))
                .andExpect(jsonPath("specie").value(dto.getSpecie()))
                .andExpect(jsonPath("breed").value(dto.getBreed()))
                .andExpect(jsonPath("personality").value(dto.getPersonality()))
        ;

    }

    @DisplayName("Should throw a PetException when not found")
    @Test
    public void getPetDetailsShouldReturnPetExceptionPetNotExist() throws Exception {

        final String id = UUID.randomUUID().toString();
        final String uri = PET_API + "/get";

        BDDMockito.given(controller.get(Mockito.anyString())).willThrow(new PetException("Pet does not exist"));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(uri).queryParam("id", id);

        final var thrown = Assertions.assertThrows(Exception.class,
                () -> mockMvc.perform(request));
        Assertions.assertTrue(thrown.getMessage().contains("Pet does not exist"));

    }

    @DisplayName("Should List The Pets Based On Filter")
    @Test
    public void shouldListThePetsBasedOnFilter() throws Exception {
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";
        final PetDTO petDTO = getDetailedPet();
        final String queryString = "?specie=cat&page=0&size=10&status=available";
        final String uri = PET_API + "/show";

        petDTO.setId(id);

        BDDMockito.given(controller.show(Mockito.anyString(), Mockito.anyString(), Mockito.any(Pageable.class)))
                .willReturn(
                        ResponseEntity.ok(
                                new PageImpl<>(
                                        List.of(petDTO),
                                        PageRequest.of(0, 10),
                                        1)))
        ;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.totalElements").value(1))
        ;
    }

    @DisplayName("Should register a Pet successfully")
    @Test
    public void registerPetSuccessfully() throws Exception {
        final String json, uri = PET_API + "/register";
        final String id = "3431fe85-fb20-4e01-a10f-c159cd80fdcd";
        final RegisterPetDTO petDTO = createNewPet();
        final RegisteredDTO savedPet = RegisteredDTO.builder()
                                                    .id(id)
                                                    .status(201)
                                                    .message("success")
                                                    .entity("Pet")
                                                    .build();

        BDDMockito.given(controller.register(Mockito.any(RegisterPetDTO.class)))
                .willReturn(ResponseEntity.status(201).body(savedPet));

        json = new ObjectMapper().writeValueAsString(petDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.entity").value("Pet"))
                ;
    }

    @DisplayName("Deve laçar erro de validação quando não houver dados suficiente para registrar um pet")
    @Test
    public void registerInvalidPet() throws Exception {
        final String uri = PET_API + "/register";
        final String json = new ObjectMapper().writeValueAsString(new RegisterPetDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result -> Assertions.assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()));

    }

    private PetDTO getDetailedPet() {
        final PetDTO petDto = new PetDTO();
        petDto.setName(Faker.instance().animal().name());
        petDto.setBreed("Scottish Fold");
        petDto.setPersonality("quiet");
        petDto.setSpecie("cat");
        return petDto;
    }

    private RegisterPetDTO createNewPet() {
        final RegisterPetDTO pet = new RegisterPetDTO();
        pet.setName(Faker.instance().animal().name());
        pet.setSpecie("dog");
        pet.setBreed("Shitzu");
        pet.setPersonality("bright");
        return pet;
    }

}

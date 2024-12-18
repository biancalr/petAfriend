package com.pets.petAfriend.features.pet;

import com.pets.petAfriend.features.pet.dto.PetDTO;
import com.pets.petAfriend.features.pet.dto.RegisterPetDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    /**
     * @param registerPetDTO
     * @return
     */
    public RegisteredDTO save(final RegisterPetDTO registerPetDTO) throws PetException {
        validateInput(registerPetDTO);
        final var pet = fromDTO(registerPetDTO);
        pet.setStatus(PetAvailability.AVAILABLE.getAvail());
        pet.setCreatedAt(new Date());
        final var saved = repository.save(pet);
        return new RegisteredDTO(saved.getId().toString(), 201, "success", "Pet");
    }

    private void validateInput(final RegisterPetDTO registerPetDTO) throws PetException {

        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(registerPetDTO.getName())) {
            errors.add("Pet name is blank");
        }

        if (StringUtils.isNotBlank(registerPetDTO.getName()) && registerPetDTO.getName().length() < 3) {
            errors.add("Pet name is too shot: limit [3:30]");
        }

        if (StringUtils.isNotBlank(registerPetDTO.getName()) && registerPetDTO.getName().length() > 30) {
            errors.add("Pet name is too big: limit [3:30]");
        }

        if (StringUtils.isBlank(registerPetDTO.getSpecie())) {
            errors.add("Pet specie is blank");
        }

        if (StringUtils.isBlank(registerPetDTO.getSpecie()) && registerPetDTO.getSpecie().length() < 3) {
            errors.add("Pet specie is too short: limit [3:30]");
        }

        if (StringUtils.isBlank(registerPetDTO.getSpecie()) && registerPetDTO.getSpecie().length() > 30) {
            errors.add("Pet specie is too big: limit [3:30]");
        }

        if (StringUtils.isBlank(registerPetDTO.getBreed())) {
            errors.add("Pet breed is blank");
        }

        if (StringUtils.isBlank(registerPetDTO.getBreed()) && registerPetDTO.getBreed().length() < 3) {
            errors.add("Pet breed is too short: limit [3:100]");
        }

        if (StringUtils.isBlank(registerPetDTO.getBreed()) && registerPetDTO.getBreed().length() > 100) {
            errors.add("Pet breed is too big: limit [3:100]");
        }

        if (StringUtils.isBlank(registerPetDTO.getPersonality())) {
            errors.add("Pet personality is too big");
        }

        if (StringUtils.isBlank(registerPetDTO.getPersonality()) && registerPetDTO.getPersonality().length() < 3) {
            errors.add("Pet personality is too short: limit [3:5000]");
        }

        if (StringUtils.isBlank(registerPetDTO.getPersonality()) && registerPetDTO.getPersonality().length() > 100) {
            errors.add("Pet personality is too big: limit [3:5000]");
        }

        if (!errors.isEmpty()) {
            throw new PetException(String.join(";", errors));
        }

    }

    /**
     * @param status
     * @param specie
     * @param pageable
     * @return
     * @throws PetException
     */
    public Page<PetDTO> list(final String status, final String specie, final Pageable pageable) throws PetException {
        if (Arrays.stream(PetAvailability.values()).noneMatch(a -> StringUtils.equalsIgnoreCase(status, a.getAvail()))) {
            throw new PetException("Invalid value for parameter 'availability': " + status);
        }
        final Pet pet = new Pet();
        pet.setStatus(Arrays.stream(PetAvailability.values()).filter(a -> StringUtils.equalsIgnoreCase(a.getAvail(), status)).findFirst().get().getAvail());
        pet.setSpecie(StringUtils.isNotBlank(specie) ? specie : null);
        final Example<Pet> example = Example.of(pet);
        return repository.findAll(example, pageable).map(this::toDTO);
    }

    /**
     * @param id
     * @return
     */
    public PetDTO get(final String id) throws PetException {
        final UUID convertId;
        try {
            convertId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new PetException("Invalid id " + id);
        }

        if (!repository.existsById(convertId)) {
            throw new PetException("Pet does not exist");
        }
        return repository.findById(convertId).map(this::toDTO).orElseThrow(() -> new PetException("Object not found"));
    }

    private Pet fromDTO(final RegisterPetDTO registerPetDTO) {
        final Pet pet = new Pet();
        pet.setName(registerPetDTO.getName().toUpperCase());
        pet.setBreed(registerPetDTO.getBreed().toUpperCase());
        pet.setSpecie(registerPetDTO.getSpecie().toUpperCase());
        pet.setPersonality(registerPetDTO.getPersonality().toUpperCase());
        return pet;
    }

    private PetDTO toDTO(final Pet pet) {
        return PetDTO.builder()
                .id(pet.getId().toString())
                .name(pet.getName())
                .breed(pet.getBreed())
                .specie(pet.getSpecie())
                .personality(pet.getPersonality())
                .status(pet.getStatus())
                .build();
    }

}

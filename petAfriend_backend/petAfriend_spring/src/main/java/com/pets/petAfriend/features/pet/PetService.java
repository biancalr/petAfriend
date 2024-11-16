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

import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    /**
     *
     * @param registerPetDTO
     * @return
     */
    public RegisteredDTO save(final RegisterPetDTO registerPetDTO) {
        final var pet = fromDTO(registerPetDTO);
        pet.setStatus(PetAvailability.AVAILABLE.getAvail());
        pet.setCreatedAt(new Date());
        final var saved = repository.save(pet);
        return new RegisteredDTO(saved.getId(), 201, "success", "Pet");
    }

    /**
     *
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
     *
     * @param id
     * @return
     */
    public PetDTO get(final Long id) throws PetException {
        if (!repository.existsById(id)){
            throw new PetException("Pet does not exist");
        }
        return repository.findById(id).map(this::toDTO).orElseThrow(()-> new PetException("Object not found"));
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
                .id(pet.getId())
                .name(pet.getName())
                .breed(pet.getBreed())
                .specie(pet.getSpecie())
                .personality(pet.getPersonality())
                .status(pet.getStatus())
                .build();
    }

}

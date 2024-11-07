package com.pets.petAfriend.features.client;

import com.pets.petAfriend.features.client.dto.ClientDTO;
import com.pets.petAfriend.features.client.dto.RegisterClientDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    /**
     *
     * @param clientDto
     * @return
     * @throws ClientException
     */
    public RegisteredDTO save(final RegisterClientDTO clientDto) throws ClientException {
        verifyUsernameAlreadyExists(clientDto.getUsername());
        final var client = fromDTO(clientDto);
        client.setCreatedAt(new Date());
        final var saved = repository.saveAndFlush(client);
        return new RegisteredDTO(saved.getId(), 201, "success", "Client");
    }

    private void verifyUsernameAlreadyExists(final String username) throws ClientException {
        if (repository.existsByUsername(username)) {
            throw new ClientException("Username already exists");
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ClientException
     */
    public ClientDTO get(final Long id) throws ClientException {
        if (!repository.existsById(id)) {
            throw new ClientException("Client with id " + id + " not found");
        }

        return repository.findById(id).map(this::toDTO).orElseThrow(() -> new ClientException("Object not found"));
    }

    private ClientDTO toDTO(final Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .username(client.getUsername())
                .email(client.getEmail())
                .build();
    }

    private Client fromDTO(final RegisterClientDTO dto) {
        final Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setUsername(dto.getUsername());
        return client;
    }

}

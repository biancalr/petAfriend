package com.pets.petAfriend.features.client;

import com.pets.petAfriend.features.client.dto.ClientDTO;
import com.pets.petAfriend.features.client.dto.RegisterClientDTO;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    private static final String REGEX_MAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    /**
     * @param clientDto
     * @return
     * @throws ClientException
     */
    public RegisteredDTO save(final RegisterClientDTO clientDto) throws ClientException {
        validateInput(clientDto);
        verifyUsernameAlreadyExists(clientDto.getUsername());
        final var client = fromDTO(clientDto);
        client.setCreatedAt(new Date());
        final var saved = repository.saveAndFlush(client);
        return new RegisteredDTO(saved.getId(), 201, "success", "Client");
    }

    private void validateInput(final RegisterClientDTO clientDto) throws ClientException {

        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(clientDto.getEmail())) {
            errors.add("Client email is blank");
        }

        if (validateMailRegex(clientDto.getEmail())) {
            errors.add("Client email is not valid");
        }

        if (StringUtils.isBlank(clientDto.getUsername())) {
            errors.add("Client username is blank");
        }

        if (clientDto.getUsername().length() > 20) {
            errors.add("Client username is too long: limit [3:20]");
        }

        if (clientDto.getUsername().length() < 3) {
            errors.add("Client username is too short: limit [3:20]");
        }

        if (!errors.isEmpty()) {
            throw new ClientException(String.join(";", errors));
        }

    }

    private boolean validateMailRegex(final String email) {
        return Pattern.compile(REGEX_MAIL)
                .matcher(email)
                .matches();
    }

    private void verifyUsernameAlreadyExists(final String username) throws ClientException {
        if (repository.existsByUsername(username)) {
            throw new ClientException("Username already exists");
        }
    }

    /**
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

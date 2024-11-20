package com.pets.petAfriend.features.rent;

import com.pets.petAfriend.features.client.ClientException;
import com.pets.petAfriend.features.client.ClientService;
import com.pets.petAfriend.features.notification.NotificationDTO;
import com.pets.petAfriend.features.notification.NotificationService;
import com.pets.petAfriend.features.pet.PetAvailability;
import com.pets.petAfriend.features.pet.PetException;
import com.pets.petAfriend.features.pet.PetService;
import com.pets.petAfriend.features.shared.RegisteredDTO;
import com.pets.petAfriend.utils.Constants;
import com.pets.petAfriend.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository repository;
    private final PetService petService;
    private final ClientService clientService;
    private final NotificationService notificationService;

    /**
     *
     * @param rentDTO
     * @return
     * @throws PetException
     * @throws ClientException
     * @throws RentException
     * @throws ParseException
     */
    public RegisteredDTO register(final RegisterRentDTO rentDTO) throws PetException, ClientException, RentException, ParseException {
        if (isPetNotAvailable(rentDTO.getPetId())) {
            throw new RentException("The Pet is not available");
        }
        if (isPetTimeNotAvailable(rentDTO.getPetId(), rentDTO.getHours(), rentDTO.getStartsAt())) {
            throw new RentException("Time selected is in conflict with the pet next rents.", 400);
        }
        final var rent = fromDTO(rentDTO);
        rent.setCreatedAt(new Date());
        rent.setUpdatedAt(new Date());
        rent.setStatus(RentStatus.CREATED.getStatus());
        final var saved = repository.saveAndFlush(rent);
        notificationService.sendToQueue(Constants.NOTIFICATION_MAIL, new NotificationDTO(clientService.get(rentDTO.getClientId()).getEmail(), petService.get(rentDTO.getPetId()).getName(), rentDTO.getStartsAt(), rentDTO.getHours(), Constants.CREATED));
        return new RegisteredDTO(saved.getId().toString(), 201, "success", "Rent");
    }

    /**
     *
     * @param id
     * @return
     * @throws RentException
     */
    public RegisteredDTO cancel(final String id) throws RentException {
        final UUID convertId;

        try {
            convertId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new RentException("Invalid Id " + id);
        }

        if (!repository.existsById(convertId)) {
            throw new RentException("Rent n° " + id + " doesn't exist");
        }

        repository.findById(convertId).ifPresent(r -> {
            r.setStatus(RentStatus.CANCELED.getStatus());
            repository.saveAndFlush(r);
        });

        return new RegisteredDTO(id, 200, "canceled", "Rent");
    }

    /**
     *
     * @return
     */
    public List<Rent> findAllFinishedByNow() {
        return repository.findAllFinishedByNow();
    }

    /**
     *
     * @param rent
     */
    public void finish(final Rent rent) {
        rent.setStatus(Constants.FINISHED);
        rent.setUpdatedAt(new Date());
        repository.saveAndFlush(rent);
    }

    public List<Rent> findAllStartedByNow() {
        final Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return repository.findAllStartedByNow(now.getTime());
    }

    public void start(final Rent rent) {
        rent.setStatus(Constants.STARTED);
        rent.setUpdatedAt(new Date());
        repository.saveAndFlush(rent);
    }

    private boolean isPetTimeNotAvailable(final String petId, final Integer hours, final String startsAt) throws PetException, ParseException {
        final var pet = petService.get(petId);
        final Date actualDate = DateUtils.toDate(startsAt);
        final List<Rent> nextRents = repository.findByStatusAndPet_Id(RentStatus.CREATED.getStatus(), UUID.fromString(pet.getId()));
        boolean notAvailable = false;
        if (nextRents.isEmpty()) {
            return false;
        }

        for (final Rent next : nextRents) {
            notAvailable = isRentTimeNotAvailable(hours, next, actualDate);
            if (notAvailable) {
                break;
            }
        }

        return notAvailable;
    }

    private boolean isRentTimeNotAvailable(final Integer hours, final Rent next, final Date actualDate) {
        final Calendar currentRentStartAt = Calendar.getInstance();
        currentRentStartAt.setTime(actualDate);
        final Calendar currentRentEndsAt = Calendar.getInstance();
        currentRentEndsAt.setTime(actualDate);
        currentRentEndsAt.add(Calendar.HOUR, hours);

        final Calendar nextRentStartAt = Calendar.getInstance();
        nextRentStartAt.setTime(next.getStartsAt());
        final Calendar nextRentEndsAt = Calendar.getInstance();
        nextRentEndsAt.setTime(next.getStartsAt());
        nextRentEndsAt.add(Calendar.HOUR, next.getHours());

        if (currentRentStartAt.before(nextRentStartAt)) {
            return currentRentEndsAt.compareTo(nextRentStartAt) > 0;
        } else if (currentRentStartAt.after(nextRentStartAt)) {
            return currentRentStartAt.compareTo(nextRentEndsAt) < 0;
        } else {
            return false;
        }
    }

    private boolean isPetNotAvailable(final String petId) throws PetException {
        final var pet = petService.get(petId);
        return Objects.equals(pet.getStatus(), PetAvailability.UNAVAILABLE.getAvail());
    }

    private Rent fromDTO(final RegisterRentDTO rentDTO) throws PetException, ClientException, ParseException {
        final Rent rent = new Rent();
        rent.setHours(rent.getHours());
        rent.setStartsAt(DateUtils.toDate(rentDTO.getStartsAt()));
        petService.get(rentDTO.getPetId());
        clientService.get(rentDTO.getClientId());
        return rent;
    }

}

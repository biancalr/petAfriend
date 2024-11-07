package com.pets.petAfriend.features.notification.stream;

import com.pets.petAfriend.features.mail.MailService;
import com.pets.petAfriend.features.notification.NotificationDTO;
import com.pets.petAfriend.utils.Constants;
import com.pets.petAfriend.utils.DateUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StreamCreatedNotification {

    private final MailService mail;

    @RabbitListener(queues = Constants.NOTIFICATION_MAIL)
    public void executeStream(final NotificationDTO notificationDTO) {
        String mailMessage;
        try {
            mailMessage = createMessage(notificationDTO);
        } catch (Exception e) {
            mailMessage = "";
        }

        try {
            mail.send(notificationDTO.getClientEmail(), mailMessage, "You have a new pet friend request " + notificationDTO.getType().toUpperCase());
            log.info("The mail to {} was sent", List.of(notificationDTO.getClientEmail()));
        } catch (MessagingException e) {
            log.info("{}", e.getMessage());
        }


    }

    private String createMessage(final NotificationDTO notificationDTO) throws ParseException {
        final Calendar endAt = Calendar.getInstance();
        endAt.setTime(DateUtils.toDate(notificationDTO.getRentStartsAt()));
        endAt.add(Calendar.HOUR, notificationDTO.getRentedHours());

        return switch (notificationDTO.getType()) {
            case Constants.CREATED ->
                    "You have a new pet friend. '" + notificationDTO.getPetName().toUpperCase() + "' has received your request. For " + notificationDTO.getRentedHours() + " hours, you'll be having your best time together. Come get your friend at " + notificationDTO.getRentStartsAt() + " and enjoy your time until " + endAt + "!";
            case Constants.STARTED ->
                    "Your time with your pet friend has started! Hope you have a great time with " + notificationDTO.getPetName() + ". Remember, your time is limited, you'll only have until " + endAt + " to enjoy. So have the best time with " + notificationDTO.getPetName() + " and build a good pet friendship!";
            case Constants.FINISHED ->
                    "Your time with your pet friend has unfortunately come to an end. Say goodbye to " + notificationDTO.getPetName() + ", now it have another friend to take care of. See you at the next friend!";
            default -> "Ignore this message.";
        };
    }

}

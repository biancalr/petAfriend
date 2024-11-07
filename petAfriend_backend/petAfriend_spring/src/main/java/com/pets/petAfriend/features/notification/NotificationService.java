package com.pets.petAfriend.features.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {

    private final RabbitTemplate template;
    private final ObjectMapper mapper;

    public void sendToQueue(final String queue, final NotificationDTO notificationDTO) {
        final MessageProperties props = new MessageProperties();
        props.setContentType("application/json");
        log.info("Sending to Queue {}", queue);

        try {
            template.send(queue, new Message(mapper.writeValueAsBytes(notificationDTO), props));
        } catch (JsonProcessingException e) {
            log.error("", e);
        }

    }
}

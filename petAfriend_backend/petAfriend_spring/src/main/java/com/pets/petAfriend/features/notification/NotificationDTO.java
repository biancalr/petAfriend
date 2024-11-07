package com.pets.petAfriend.features.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NotificationDTO {

    private String clientEmail;
    private String petName;
    private String rentStartsAt;
    private Integer rentedHours;
    private String type;

}

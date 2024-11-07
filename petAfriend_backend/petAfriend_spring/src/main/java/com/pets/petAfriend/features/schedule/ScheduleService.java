package com.pets.petAfriend.features.schedule;

import com.pets.petAfriend.features.notification.NotificationDTO;
import com.pets.petAfriend.features.notification.NotificationService;
import com.pets.petAfriend.features.rent.Rent;
import com.pets.petAfriend.features.rent.RentService;
import com.pets.petAfriend.utils.Constants;
import com.pets.petAfriend.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduleService {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final RentService rentService;
    private final NotificationService notificationService;
    private final CronTrigger cronTrigger;

    public void execute() {
        final var allStarted = rentService.findAllStartedByNow();
        final var allFinished = rentService.findAllFinishedByNow();

        allStarted.forEach(rent -> {
            taskScheduler.schedule(getRunnable(rent, Constants.STARTED), cronTrigger);
            rentService.start(rent);
        });

        allFinished.forEach(rent -> {
            taskScheduler.schedule(getRunnable(rent, Constants.FINISHED), cronTrigger);
            rentService.finish(rent);
        });

    }

    private Runnable getRunnable(final Rent rent, final String queue) {

        return () -> notificationService
                .sendToQueue(
                        Constants.NOTIFICATION_MAIL,
                        new NotificationDTO(rent.getClient().getEmail(),
                                rent.getPet().getName(),
                                DateUtils.toString(rent.getStartsAt()),
                                rent.getHours(),
                                queue));
    }


}

package com.qorvia.eventmanagementservice.clients;

import com.qorvia.eventmanagementservice.dto.BookingCompletedNotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE", url = "http://localhost:8884")
public interface NotificationClient {

    @PostMapping("/notification/booking-completed")
    void sendBookingCompletedNotification(@RequestBody BookingCompletedNotificationDTO notificationDTO);

}

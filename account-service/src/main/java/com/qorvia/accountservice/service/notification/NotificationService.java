//package com.qorvia.accountservice.service.notification;
//
//import com.qorvia.accountservice.dto.admin.request.OrganizerStatusChangeMailRequest;
//import com.qorvia.accountservice.dto.message.*;
//import com.qorvia.accountservice.publisher.NotificationPublisher;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class NotificationService {
//
//    @Autowired
//    private NotificationPublisher notificationPublisher;
//
//    public void sendOtp(String email) {
//        OtpMessage message = new OtpMessage(email);
//        log.info("otp is sending from account service");
//        notificationPublisher.sendOtp(message);
//    }
//
//    public void organizerEmailVerificationRequest(String email) {
//        OrganizerEmailVerificationMessage message = new OrganizerEmailVerificationMessage(email);
//        notificationPublisher.organizerEmailVerificationRequest(message);
//    }
//
//    public void organizerStatusChangeMail(OrganizerStatusChangeMailRequest mailRequest) {
//        OrganizerStatusChangeMailMessage message = new OrganizerStatusChangeMailMessage(mailRequest);
//        notificationPublisher.organizerStatusChangeMail(message);
//    }
//}

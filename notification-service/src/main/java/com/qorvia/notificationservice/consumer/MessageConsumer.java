//package com.qorvia.notificationservice.consumer;
//
//import com.qorvia.notificationservice.dto.message.*;
//import com.qorvia.notificationservice.service.NotificationService;
//import com.qorvia.notificationservice.service.VerificationService;
//import com.qorvia.notificationservice.utils.ResponseUtil;
//import lombok.AllArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class MessageConsumer {
//
//    private final NotificationService notificationService;
//    private final VerificationService verificationService;
//
//    @RabbitListener(queues = "message_queue")
//    public void receiveOtpMessage(OtpMessage message) {
//        System.out.println("Received OTP message: " + message);
//        verificationService.setOtp(message.getEmail());
//
//    }
//
//    @RabbitListener(queues = "message_queue")
//    public void receiveOrganizerEmailVerificationMessage(OrganizerEmailVerificationMessage message) {
//
//        System.out.println("Received Organizer Email Verification message: " + message);
//        verificationService.organizerEmailVerification(message.getEmail());
//
//    }
//
//    @RabbitListener(queues = "message_queue")
//    public void receiveOrganizerStatusChangeMailMessage(OrganizerStatusChangeMailMessage message) {
//
//        System.out.println("Received Organizer Status Change Mail message: " + message);
//
//            notificationService.sendStatusChangeEmail(message.getMailRequest());
//
//
//    }
//}

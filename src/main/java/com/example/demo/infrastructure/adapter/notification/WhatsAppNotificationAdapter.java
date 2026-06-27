package com.example.demo.infrastructure.adapter.notification;

import com.example.demo.domain.port.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppNotificationAdapter implements NotificationService {
    @Override
    public void sendWhatsAppConfirmation(String patientId, String message) {
        // Simulación de envío de WhatsApp
        System.out.println("Enviando WhatsApp a " + patientId + ": " + message);
    }
}

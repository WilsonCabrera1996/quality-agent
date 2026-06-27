package com.example.demo.domain.port;

public interface NotificationService {
    void sendWhatsAppConfirmation(String patientId, String message);
}

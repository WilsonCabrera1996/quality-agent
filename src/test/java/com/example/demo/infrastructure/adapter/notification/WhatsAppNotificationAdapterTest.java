package com.example.demo.infrastructure.adapter.notification;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhatsAppNotificationAdapterTest {

    @Test
    void shouldPrintNotificationMessage() {
        WhatsAppNotificationAdapter adapter = new WhatsAppNotificationAdapter();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        adapter.sendWhatsAppConfirmation("patient-1", "Hello");

        assertTrue(outContent.toString().contains("Enviando WhatsApp a patient-1: Hello"));
        System.setOut(System.out);
    }
}

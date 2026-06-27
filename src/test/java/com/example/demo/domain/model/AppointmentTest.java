package com.example.demo.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void appointmentCreationWorks() {
        UUID id = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        Appointment appointment = new Appointment(id, patientId, doctorId, now, Appointment.AppointmentStatus.CONFIRMED);
        
        assertEquals(id, appointment.getId());
        assertEquals(patientId, appointment.getPatientId());
        assertEquals(doctorId, appointment.getDoctorId());
        assertEquals(now, appointment.getDateTime());
        assertEquals(Appointment.AppointmentStatus.CONFIRMED, appointment.getStatus());
    }
}

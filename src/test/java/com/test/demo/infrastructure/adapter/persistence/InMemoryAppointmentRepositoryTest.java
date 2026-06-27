package com.example.demo.infrastructure.adapter.persistence;

import com.example.demo.domain.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryAppointmentRepositoryTest {

    private InMemoryAppointmentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAppointmentRepository();
    }

    @Test
    void shouldSaveAndFindSlot() {
        UUID doctorId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        
        assertTrue(repository.isSlotAvailable(doctorId, time));
        
        Appointment appointment = new Appointment(null, UUID.randomUUID(), doctorId, time, Appointment.AppointmentStatus.CONFIRMED);
        repository.save(appointment);
        
        assertFalse(repository.isSlotAvailable(doctorId, time));
    }

    @Test
    void shouldGenerateIdWhenNull() {
        Appointment appointment = new Appointment(null, UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), Appointment.AppointmentStatus.CONFIRMED);
        Appointment saved = repository.save(appointment);
        
        assertNotNull(saved.getId());
    }
}

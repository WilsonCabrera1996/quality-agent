package com.example.demo.domain.port;

import com.example.demo.domain.model.Appointment;
import java.time.LocalDateTime;
import java.util.UUID;

public interface AppointmentRepository {
    boolean isSlotAvailable(UUID doctorId, LocalDateTime dateTime);
    Appointment save(Appointment appointment);
}

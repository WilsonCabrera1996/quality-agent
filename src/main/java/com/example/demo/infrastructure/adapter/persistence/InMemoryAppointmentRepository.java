package com.example.demo.infrastructure.adapter.persistence;

import com.example.demo.domain.model.Appointment;
import com.example.demo.domain.port.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryAppointmentRepository implements AppointmentRepository {
    private final Map<UUID, Appointment> database = new HashMap<>();

    @Override
    public boolean isSlotAvailable(UUID doctorId, LocalDateTime dateTime) {
        return database.values().stream()
                .noneMatch(a -> a.getDoctorId().equals(doctorId) && a.getDateTime().equals(dateTime));
    }

    @Override
    public Appointment save(Appointment appointment) {
        UUID id = appointment.getId() == null ? UUID.randomUUID() : appointment.getId();
        Appointment toSave = new Appointment(
                id,
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getDateTime(),
                appointment.getStatus()
        );
        database.put(id, toSave);
        return toSave;
    }
}

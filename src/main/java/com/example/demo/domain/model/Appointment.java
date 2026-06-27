package com.example.demo.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private final UUID id;
    private final UUID patientId;
    private final UUID doctorId;
    private final LocalDateTime dateTime;
    private final AppointmentStatus status;

    public Appointment(UUID id, UUID patientId, UUID doctorId, LocalDateTime dateTime, AppointmentStatus status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getPatientId() { return patientId; }
    public UUID getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public AppointmentStatus getStatus() { return status; }

    public enum AppointmentStatus {
        CONFIRMED, CANCELLED, PENDING
    }
}

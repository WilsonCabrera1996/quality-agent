package com.example.demo.application.usecase;

import com.example.demo.domain.model.Appointment;
import com.example.demo.domain.port.AppointmentRepository;
import com.example.demo.domain.port.NotificationService;
import java.util.UUID;

public class CreateAppointmentUseCase {
    private final AppointmentRepository repository;
    private final NotificationService notificationService;

    public CreateAppointmentUseCase(AppointmentRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public Appointment execute(Appointment appointment) {
        if (!repository.isSlotAvailable(appointment.getDoctorId(), appointment.getDateTime())) {
            throw new RuntimeException("Slot not available");
        }
        Appointment appointmentToSave = new Appointment(
            appointment.getId(),
            appointment.getPatientId(),
            appointment.getDoctorId(),
            appointment.getDateTime(),
            Appointment.AppointmentStatus.CONFIRMED
        );
        
        Appointment savedAppointment = repository.save(appointmentToSave);
        notificationService.sendWhatsAppConfirmation(
            savedAppointment.getPatientId().toString(), 
            "Tu cita ha sido confirmada para el " + savedAppointment.getDateTime()
        );
        
        return savedAppointment;
    }
}

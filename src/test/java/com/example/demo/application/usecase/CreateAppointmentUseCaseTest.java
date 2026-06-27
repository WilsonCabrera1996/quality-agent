package com.example.demo.application.usecase;

import com.example.demo.domain.model.Appointment;
import com.example.demo.domain.port.AppointmentRepository;
import com.example.demo.domain.port.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAppointmentUseCaseTest {

    private AppointmentRepository repository;
    private NotificationService notificationService;
    private CreateAppointmentUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(AppointmentRepository.class);
        notificationService = mock(NotificationService.class);
        useCase = new CreateAppointmentUseCase(repository, notificationService);
    }

    @Test
    void shouldExecuteSuccessfully() {
        UUID doctorId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Appointment appointment = new Appointment(null, UUID.randomUUID(), doctorId, time, Appointment.AppointmentStatus.PENDING);
        
        when(repository.isSlotAvailable(doctorId, time)).thenReturn(true);
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Appointment result = useCase.execute(appointment);

        assertNotNull(result);
        assertEquals(Appointment.AppointmentStatus.CONFIRMED, result.getStatus());
        verify(notificationService).sendWhatsAppConfirmation(anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenSlotNotAvailable() {
        UUID doctorId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Appointment appointment = new Appointment(null, UUID.randomUUID(), doctorId, time, Appointment.AppointmentStatus.PENDING);
        
        when(repository.isSlotAvailable(doctorId, time)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> useCase.execute(appointment));
        assertEquals("Slot not available", exception.getMessage());
        verify(repository, never()).save(any());
    }
}

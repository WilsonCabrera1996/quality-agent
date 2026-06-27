package com.example.demo.infrastructure.adapter.web;

import com.example.demo.application.usecase.CreateAppointmentUseCase;
import com.example.demo.domain.model.Appointment;
import com.example.demo.infrastructure.api.model.AppointmentRequest;
import com.example.demo.infrastructure.api.model.AppointmentResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class AppointmentControllerTest {

    @Test
    void createAppointmentReturnsCreatedStatus() {
        CreateAppointmentUseCase useCase = Mockito.mock(CreateAppointmentUseCase.class);
        Appointment saved = new Appointment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), Appointment.AppointmentStatus.CONFIRMED);
        Mockito.when(useCase.execute(any())).thenReturn(saved);

        AppointmentController controller = new AppointmentController(useCase);
        
        AppointmentRequest request = new AppointmentRequest();
        request.setPatientId(UUID.randomUUID());
        request.setDoctorId(UUID.randomUUID());
        request.setDateTime(OffsetDateTime.now());

        ResponseEntity<AppointmentResponse> response = controller.createAppointment(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved.getId(), response.getBody().getId());
    }
}

package com.example.demo.bdd;

import com.example.demo.application.usecase.CreateAppointmentUseCase;
import com.example.demo.domain.model.Appointment;
import com.example.demo.domain.port.AppointmentRepository;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.application.usecase.CreateAppointmentUseCase;
import com.example.demo.infrastructure.adapter.web.GlobalExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class AppointmentStepDefinitions {

    @Autowired
    private CreateAppointmentUseCase useCase;

    @Autowired
    private AppointmentRepository repository;

    private UUID doctorId;
    private LocalDateTime dateTime;
    private Appointment result;
    private Exception exception;

    @Before
    public void setup() {
        this.result = null;
        this.exception = null;
    }

    @Dado("que el paciente accede al sistema fuera del horario de atencion telefonica")
    public void fueraHorarioAtencion() {
    }

    @Dado("que el paciente elige el médico con ID {string}")
    @Dado("elige el médico con ID {string}")
    public void eligeMedico(String id) {
        this.doctorId = UUID.nameUUIDFromBytes(id.getBytes());
    }

    @Dado("elige la fecha y hora {string} que está disponible")
    public void eligeFechaHoraDisponible(String dateTimeStr) {
        this.dateTime = LocalDateTime.parse(dateTimeStr.replace("Z", ""));
    }

    @Dado("la franja horaria {string} ya está ocupada")
    public void franjaOcupada(String dateTimeStr) {
        this.dateTime = LocalDateTime.parse(dateTimeStr.replace("Z", ""));
        Appointment existing = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                this.doctorId,
                this.dateTime,
                Appointment.AppointmentStatus.CONFIRMED
        );
        repository.save(existing);
    }

    @Cuando("confirma la reserva")
    public void confirmaReserva() {
        Appointment request = new Appointment(
                null,
                UUID.randomUUID(),
                this.doctorId,
                this.dateTime,
                Appointment.AppointmentStatus.PENDING
        );
        this.result = useCase.execute(request);
    }

    @Cuando("intenta confirmar la reserva")
    public void intentaConfirmarReserva() {
        try {
            Appointment request = new Appointment(
                    null,
                    UUID.randomUUID(),
                    this.doctorId,
                    this.dateTime,
                    Appointment.AppointmentStatus.PENDING
            );
            this.result = useCase.execute(request);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Entonces("la cita queda registrada exitosamente")
    public void citaRegistrada() {
        assertNotNull(result);
        assertEquals(Appointment.AppointmentStatus.CONFIRMED, result.getStatus());
    }

    @Entonces("el paciente recibe una confirmacion por WhatsApp")
    public void recibeWhatsApp() {
    }

    @Entonces("el sistema muestra que la franja no está disponible")
    public void sistemaMuestraNoDisponible() {
        assertNotNull(exception);
        assertEquals("Slot not available", exception.getMessage());
        
        // Simulación de lo que haría el GlobalExceptionHandler
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<com.example.demo.infrastructure.api.model.ErrorResponse> errorResponse = 
            handler.handleRuntimeException((RuntimeException) exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertEquals("BUSINESS_ERROR", errorResponse.getBody().getCode());
    }

    @Entonces("lo invita a elegir otra franja")
    public void invitaOtraFranja() {
    }
}

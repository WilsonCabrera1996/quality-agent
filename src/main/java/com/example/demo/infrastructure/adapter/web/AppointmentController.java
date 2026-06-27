package com.example.demo.infrastructure.adapter.web;

import com.example.demo.application.usecase.CreateAppointmentUseCase;
import com.example.demo.domain.model.Appointment;
import com.example.demo.infrastructure.api.AppointmentsApi;
import com.example.demo.infrastructure.api.model.AppointmentRequest;
import com.example.demo.infrastructure.api.model.AppointmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.UUID;

@RestController
public class AppointmentController implements AppointmentsApi {

    private final CreateAppointmentUseCase createAppointmentUseCase;

    public AppointmentController(CreateAppointmentUseCase createAppointmentUseCase) {
        this.createAppointmentUseCase = createAppointmentUseCase;
    }

    @Override
    public ResponseEntity<AppointmentResponse> createAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment(
                null,
                request.getPatientId(),
                request.getDoctorId(),
                request.getDateTime().toLocalDateTime(),
                Appointment.AppointmentStatus.CONFIRMED
        );

        Appointment saved = createAppointmentUseCase.execute(appointment);

        AppointmentResponse response = new AppointmentResponse();
        response.setId(saved.getId());
        response.setPatientId(saved.getPatientId().toString());
        response.setDoctorId(saved.getDoctorId().toString());
        response.setDateTime(saved.getDateTime().atOffset(ZoneOffset.UTC));
        response.setStatus(AppointmentResponse.StatusEnum.valueOf(saved.getStatus().name()));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

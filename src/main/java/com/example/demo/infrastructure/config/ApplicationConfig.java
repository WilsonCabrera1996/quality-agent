package com.example.demo.infrastructure.config;

import com.example.demo.application.usecase.CreateAppointmentUseCase;
import com.example.demo.domain.port.AppointmentRepository;
import com.example.demo.domain.port.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CreateAppointmentUseCase createAppointmentUseCase(
            AppointmentRepository repository,
            NotificationService notificationService) {
        return new CreateAppointmentUseCase(repository, notificationService);
    }
}

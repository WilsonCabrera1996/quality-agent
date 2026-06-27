package com.example.demo.infrastructure.config;

import com.example.demo.application.usecase.CreateAppointmentUseCase;
import com.example.demo.domain.port.AppointmentRepository;
import com.example.demo.domain.port.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationConfigTest {

    @Test
    void shouldCreateUseCaseBean() {
        ApplicationConfig config = new ApplicationConfig();
        AppointmentRepository repository = Mockito.mock(AppointmentRepository.class);
        NotificationService notificationService = Mockito.mock(NotificationService.class);
        
        CreateAppointmentUseCase useCase = config.createAppointmentUseCase(repository, notificationService);
        
        assertNotNull(useCase);
    }
}

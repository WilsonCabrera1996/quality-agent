# Sistema de Reserva de Citas Médicas - US-01

Este proyecto es una implementación de referencia para un sistema de reserva de citas médicas en línea, diseñado bajo estándares de alta ingeniería de software y siguiendo los principios de la **Arquitectura Limpia**.

## 🚀 Tecnologías y Estándares

El proyecto cumple estrictamente con la **Constitución del Proyecto** definida:

1.  **Arquitectura Limpia (Robert C. Martin):** Separación clara entre Dominio, Aplicación e Infraestructura para garantizar independencia de frameworks.
2.  **API First:** Desarrollo basado en contrato OpenAPI 3.0. Generación automática de código mediante `openapi-generator`.
3.  **BDD (Behavior Driven Development):** Pruebas funcionales escritas en Gherkin y ejecutadas con Cucumber.
4.  **Buenas Prácticas:** Aplicación rigurosa de principios **SOLID, YAGNI y DRY**.
5.  **Calidad:** Control de cobertura mediante **JaCoCo**, con un umbral objetivo del **80%** de cobertura real.

---

## 🏗️ Estructura del Proyecto

```text
src/main/java/com/example/demo/
├── domain/            # Reglas de negocio puras (Entidades y Puertos)
├── application/       # Casos de uso (Orquestación)
└── infrastructure/    # Adaptadores (Web, Persistencia, Notificaciones, Config)
```

---

## 🛠️ Comandos Principales

### Generación de Código API
Para generar los controladores y DTOs a partir del contrato `openapi.yaml`:
```bash
./gradlew openApiGenerate
```

### Ejecución de Pruebas y Cobertura
Ejecuta las pruebas unitarias y de comportamiento (Cucumber), generando el reporte de JaCoCo:
```bash
./gradlew test jacocoTestReport
```
*   **Reporte de Cobertura:** `build/reports/jacoco/test/html/index.html`
*   **Reporte de Pruebas:** `build/reports/tests/test/index.html`

### Verificación de Calidad
Valida que el código cumpla con el umbral de cobertura configurado:
```bash
./gradlew verifyCoverage
```

### Iniciar la Aplicación
```bash
./gradlew bootRun
```

---

## 📖 Documentación de la API (Swagger)

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva en:
👉 `http://localhost:8080/swagger-ui/index.html`

---

## 📝 Historias de Usuario Implementadas

### US-01: Reserva de cita en línea 24/7
**Como** paciente, **quiero** reservar una cita en línea en cualquier momento del día, **para** no tener que llamar durante mi horario de almuerzo ni acumular intentos fallidos.

**Criterios de Aceptación:**
- Registro de cita exitoso fuera de horario telefónico.
- Recepción de confirmación vía WhatsApp (Simulado).
- Validación de disponibilidad de franja horaria.

---

## ⚖️ Licencia
Este proyecto es para fines educativos y de demostración técnica.

# language: es
Característica: Reserva de cita en linea 24/7

  Como paciente
  Quiero reservar una cita en linea en cualquier momento del dia
  Para no tener que llamar durante mi horario de almuerzo ni acumular intentos fallidos

  Escenario: Reserva exitosa fuera del horario de atención telefónica
    Dado que el paciente accede al sistema fuera del horario de atencion telefonica
    Y elige el médico con ID "doctor-123"
    Y elige la fecha y hora "2026-07-01T10:00:00Z" que está disponible
    Cuando confirma la reserva
    Entonces la cita queda registrada exitosamente
    Y el paciente recibe una confirmacion por WhatsApp

  Escenario: Intento de reserva en franja ocupada
    Dado que el paciente elige el médico con ID "doctor-123"
    Y la franja horaria "2026-07-01T10:00:00Z" ya está ocupada
    Cuando intenta confirmar la reserva
    Entonces el sistema muestra que la franja no está disponible
    Y lo invita a elegir otra franja

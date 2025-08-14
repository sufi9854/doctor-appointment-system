package com.example.doctor_appointment_system.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull Long doctorId,
        @NotNull Long patientId,
        @NotNull LocalDateTime appointmentDateTime
) {}

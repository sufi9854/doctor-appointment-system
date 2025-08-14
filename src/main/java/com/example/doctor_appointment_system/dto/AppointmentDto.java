package com.example.doctor_appointment_system.dto;

import java.time.LocalDateTime;

public record AppointmentDto(Long id, Long doctorId, Long patientId, LocalDateTime appointmentDateTime) {}


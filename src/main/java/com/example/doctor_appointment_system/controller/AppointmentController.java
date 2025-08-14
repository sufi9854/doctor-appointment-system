package com.example.doctor_appointment_system.controller;

import com.example.doctor_appointment_system.dto.AppointmentDto;
import com.example.doctor_appointment_system.dto.AppointmentRequest;
import com.example.doctor_appointment_system.entity.Appointment;
import com.example.doctor_appointment_system.exception.NotFoundException;
import com.example.doctor_appointment_system.repository.AppointmentRepository;
import com.example.doctor_appointment_system.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentRepository repository;

    public AppointmentController(AppointmentService service, AppointmentRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public AppointmentDto book(@Valid @RequestBody AppointmentRequest req) {
        return service.book(req);
    }

    @GetMapping
    public Page<Appointment> all(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Appointment one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        repository.deleteById(id);
    }
}


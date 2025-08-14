package com.example.doctor_appointment_system.controller;

import com.example.doctor_appointment_system.entity.Doctor;
import com.example.doctor_appointment_system.exception.NotFoundException;
import com.example.doctor_appointment_system.repository.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorRepository repository;
    public DoctorController(DoctorRepository repository) { this.repository = repository; }

    @PostMapping
    public Doctor create(@Valid @RequestBody Doctor d) { return repository.save(d); }

    @GetMapping
    public Page<Doctor> all(Pageable pageable) { return repository.findAll(pageable); }

    @GetMapping("/{id}")
    public Doctor one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
    }

    @PutMapping("/{id}")
    public Doctor update(@PathVariable Long id, @Valid @RequestBody Doctor d) {
        Doctor existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
        existing.setName(d.getName());
        existing.setSpecialization(d.getSpecialization());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repository.deleteById(id); }
}


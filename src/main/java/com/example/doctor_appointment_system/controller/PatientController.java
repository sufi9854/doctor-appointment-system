package com.example.doctor_appointment_system.controller;

import com.example.doctor_appointment_system.entity.Patient;
import com.example.doctor_appointment_system.exception.NotFoundException;
import com.example.doctor_appointment_system.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientRepository repository;
    public PatientController(PatientRepository repository) { this.repository = repository; }

    @PostMapping
    public Patient create(@Valid @RequestBody Patient p) { return repository.save(p); }

    @GetMapping
    public Page<Patient> all(Pageable pageable) { return repository.findAll(pageable); }

    @GetMapping("/{id}")
    public Patient one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @Valid @RequestBody Patient p) {
        Patient existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
        existing.setName(p.getName());
        existing.setEmail(p.getEmail());
        existing.setPhone(p.getPhone());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repository.deleteById(id); }
}


package com.example.doctor_appointment_system.repository;

import com.example.doctor_appointment_system.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}

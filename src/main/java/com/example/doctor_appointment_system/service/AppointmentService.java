package com.example.doctor_appointment_system.service;

import com.example.doctor_appointment_system.dto.AppointmentDto;
import com.example.doctor_appointment_system.dto.AppointmentRequest;
import com.example.doctor_appointment_system.entity.Appointment;
import com.example.doctor_appointment_system.entity.Doctor;
import com.example.doctor_appointment_system.entity.Patient;
import com.example.doctor_appointment_system.exception.BusinessException;
import com.example.doctor_appointment_system.exception.NotFoundException;
import com.example.doctor_appointment_system.repository.AppointmentRepository;
import com.example.doctor_appointment_system.repository.DoctorRepository;
import com.example.doctor_appointment_system.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentDto book(AppointmentRequest req) {
        Doctor doctor = doctorRepository.findById(req.doctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found"));

        Patient patient = patientRepository.findById(req.patientId())
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        boolean clash = appointmentRepository
                .existsByDoctorIdAndAppointmentDateTime(doctor.getId(), req.appointmentDateTime());
        if (clash) throw new BusinessException("This doctor already has an appointment at that time.");

        Appointment a = new Appointment();
        a.setDoctor(doctor);
        a.setPatient(patient);
        a.setAppointmentDateTime(req.appointmentDateTime());
        a = appointmentRepository.save(a);

        return new AppointmentDto(a.getId(), doctor.getId(), patient.getId(), a.getAppointmentDateTime());
    }
}


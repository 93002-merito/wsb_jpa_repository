package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.util.List;

public interface PatientService {
    PatientTO findPatient(Long id);
    void deletePatient(Long id);
    List<PatientTO> findAllPatients();
}
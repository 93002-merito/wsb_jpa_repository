package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;

import java.util.List;

public interface PatientService {
    PatientTO findPatient(Long id);
    void deletePatient(Long id);
    List<PatientTO> findAllPatients();

    List<VisitTO> getVisitsByPatientId(Long patientId);
}

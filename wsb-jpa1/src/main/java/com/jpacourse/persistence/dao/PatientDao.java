package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao {
    PatientEntity findById(Long id);
    void delete(Long id);
    List<PatientEntity> findAll();
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime time, String description);

    List<PatientEntity> findByLastName(String lastName);
    List<VisitEntity> findVisitsByPatientId(Long patientId);
    List<PatientEntity> findPatientsWithMoreThanXVisits(long minVisits);
    List<PatientEntity> findByActiveBefore(boolean active);
}

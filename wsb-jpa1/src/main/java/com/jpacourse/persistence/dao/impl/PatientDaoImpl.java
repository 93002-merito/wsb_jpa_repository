package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.dao.PatientDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PatientEntity findById(Long id) {
        return entityManager.find(PatientEntity.class, id);
    }

    @Override
    public void delete(Long id) {
        PatientEntity patient = findById(id);
        if (patient != null) {
            entityManager.remove(patient);
        }
    }

    @Override
    public List<PatientEntity> findAll() {
        return entityManager
                .createQuery("SELECT p FROM PatientEntity p", PatientEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime time, String description) {
        PatientEntity patient = findById(patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient != null && doctor != null) {
            VisitEntity visit = new VisitEntity();
            visit.setTime(time);
            visit.setDescription(description);
            visit.setDoctor(doctor);
            visit.setPatient(patient);

            // Dodaj wizytę do pacjenta
            patient.getVisits().add(visit);

            // Kaskadowo zapisz pacjenta z nową wizytą
            entityManager.merge(patient);
        }
    }
}

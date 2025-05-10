package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testAddVisitToPatient() {
        Long patientId = 1L;
        Long doctorId = 1L;
        LocalDateTime time = LocalDateTime.now();
        String description = "Badanie kontrolne";

        patientDao.addVisitToPatient(patientId, doctorId, time, description);
        entityManager.flush();

        PatientEntity updatedPatient = patientDao.findById(patientId);
        List<VisitEntity> visits = updatedPatient.getVisits();

        assertNotNull(visits);
        assertFalse(visits.isEmpty());

        VisitEntity visit = visits.get(visits.size() - 1);
        assertEquals(description, visit.getDescription());
        assertEquals(time, visit.getTime());
        assertNotNull(visit.getDoctor());
        assertEquals(doctorId, visit.getDoctor().getId());
    }

    @Test
    public void testFindByLastName() {
        List<PatientEntity> patients = patientDao.findByLastName("Nowak");

        assertNotNull(patients);
        assertFalse(patients.isEmpty());

        for (PatientEntity patient : patients) {
            assertEquals("Nowak", patient.getLastName());
        }
    }

    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        long minVisits = 0L;

        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanXVisits(minVisits);

        assertNotNull(patients, "Wynik nie powinien być nullem");
        assertFalse(patients.isEmpty(), "Lista pacjentów nie powinna być pusta");

        for (PatientEntity patient : patients) {
            assertTrue(patient.getVisits().size() > minVisits,
                    "Pacjent powinien mieć więcej niż " + minVisits + " wizyt");
        }
    }

    @Test
    public void testFindByActiveBefore() {
        List<PatientEntity> patients = patientDao.findByActiveBefore(true);

        assertNotNull(patients);
        assertFalse(patients.isEmpty());

        for (PatientEntity patient : patients) {
            assertTrue(patient.isActive(), "Pacjent powinien być aktywny");
        }
    }

    @Test
    public void testOptimisticLocking() {
        PatientEntity patient1 = entityManager.find(PatientEntity.class, 1L);
        PatientEntity patient2 = entityManager.find(PatientEntity.class, 1L);

        patient1.setEmail("janek1@example.com");
        entityManager.flush();

        assertThrows(OptimisticLockException.class, () -> {
            patient2.setEmail("janek2@example.com");
            entityManager.flush();
        });
    }
}

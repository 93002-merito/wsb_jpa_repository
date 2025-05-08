package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import jakarta.persistence.EntityManager;
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
        // Zalozenie: w bazie istnieje pacjent o ID 1 i doktor o ID 1
        Long patientId = 1L;
        Long doctorId = 1L;
        LocalDateTime time = LocalDateTime.now();
        String description = "Badanie kontrolne";

        // Dodanie wizytę
        patientDao.addVisitToPatient(patientId, doctorId, time, description);
        entityManager.flush();  // Wymuszenie zapisu do bazy

        // Pobranie pacjenta z bazy
        PatientEntity updatedPatient = patientDao.findById(patientId);
        List<VisitEntity> visits = updatedPatient.getVisits();

        assertNotNull(visits);
        assertFalse(visits.isEmpty());

        VisitEntity visit = visits.get(visits.size() - 1); // Ostatnio dodana

        assertEquals(description, visit.getDescription());
        assertEquals(time, visit.getTime());
        assertNotNull(visit.getDoctor());
        assertEquals(doctorId, visit.getDoctor().getId());
    }
}

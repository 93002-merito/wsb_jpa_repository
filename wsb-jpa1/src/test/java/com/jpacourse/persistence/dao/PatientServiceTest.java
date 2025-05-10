package com.jpacourse.persistence.dao;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.service.PatientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void testGetVisitsByPatientId() {

        Long patientId = 1L;

        List<VisitTO> visits = patientService.getVisitsByPatientId(patientId);

        assertNotNull(visits, "Lista wizyt nie powinna być nullem");
        assertFalse(visits.isEmpty(), "Pacjent powinien mieć przynajmniej jedną wizytę");

        visits.forEach(visit -> {
            assertNotNull(visit.getTime(), "Wizyta powinna mieć datę");
            assertNotNull(visit.getDoctorFirstName(), "Wizyta powinna zawierać imię lekarza");
            assertNotNull(visit.getDoctorLastName(), "Wizyta powinna zawierać nazwisko lekarza");
        });
    }
}

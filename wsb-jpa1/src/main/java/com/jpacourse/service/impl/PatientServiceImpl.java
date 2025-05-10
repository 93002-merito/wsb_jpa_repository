package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO; // <-- TO DODAJ
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;

    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findPatient(Long id) {
        return PatientMapper.mapToTO(patientDao.findById(id));
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        patientDao.delete(id);
    }

    @Override
    public List<PatientTO> findAllPatients() {
        return patientDao.findAll().stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitTO> getVisitsByPatientId(Long patientId) {
        return patientDao.findVisitsByPatientId(patientId).stream()
                .map(visit -> {
                    VisitTO visitTO = new VisitTO();
                    visitTO.setTime(visit.getTime());
                    visitTO.setDoctorFirstName(visit.getDoctor().getFirstName());
                    visitTO.setDoctorLastName(visit.getDoctor().getLastName());
                    visitTO.setTreatmentTypes(
                            visit.getTreatments().stream()
                                    .map(t -> t.getType().toString())
                                    .collect(Collectors.toList())
                    );
                    return visitTO;
                })
                .collect(Collectors.toList());
    }

}

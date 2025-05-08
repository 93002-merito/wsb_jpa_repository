package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
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
}

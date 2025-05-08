package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {
    public static PatientTO mapToTO(PatientEntity entity) {
        if (entity == null) return null;

        PatientTO to = new PatientTO();
        to.setId(entity.getId());
        to.setFirstName(entity.getFirstName());
        to.setLastName(entity.getLastName());
        to.setActive(entity.isActive());

        if (entity.getVisits() != null) {
            List<VisitTO> visitTOS = entity.getVisits().stream().map(visit -> {
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
            }).collect(Collectors.toList());

            to.setVisits(visitTOS);
        }

        return to;
    }
}

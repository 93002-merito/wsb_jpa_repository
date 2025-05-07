-- ADDRESS
insert into address (id, address_line1, address_line2, city, postal_code)
values (901, 'xx', 'yy', 'miasto', '60-409');

insert into address (id, address_line1, address_line2, city, postal_code)
values (902, 'ul. Kwiatowa 1', '', 'Warszawa', '06-071');

insert into address (id, address_line1, address_line2, city, postal_code)
values (903, 'ul. Radosna 5', '', 'Wroclaw', '30-002');

-- DOCTOR
insert into doctor (id, first_name, last_name, specialization, address_id, doctor_number, telephone_number, email)
values (1, 'Anna', 'Kowalska', 'OCULIST', 902, 'DOC-1001', '123456789', 'anna.kowalska@example.com');

-- PATIENT
insert into patient (id, first_name, last_name, date_of_birth, address_id, patient_number, telephone_number, email)
values (1, 'Jan', 'Nowak', '1985-06-12', 903, 'PAT-2001', '987654321', 'jan.nowak@example.com');

-- VISIT
insert into visit (id, description, time, doctor_id, patient_id)
values (1, 'Wizyta kontrolna', '2024-05-07T10:00:00', 1, 1);

-- MEDICAL_TREATMENT
insert into medical_treatment (id, description, type, visit_id)
values (1, 'EKG', 'EKG', 1);

insert into medical_treatment (id, description, type, visit_id)
values (2, 'Porada lekarska', 'USG', 1);

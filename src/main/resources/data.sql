-- ======================
-- DOCTORS
-- ======================
INSERT INTO doctor (id, name, specialization, email) VALUES
                                                         (1, 'Dr. Sharma', 'Cardiology', 'sharma@gmail.com'),
                                                         (2, 'Dr. Mehta', 'Neurology', 'mehta@gmail.com'),
                                                         (3, 'Dr. Rao', 'Orthopedics', 'rao@gmail.com');

-- ======================
-- INSURANCE
-- ======================
INSERT INTO insurance (id, policy_number, provider, valid_until, created_at) VALUES
                                                                                 (1, 'POL123', 'SBI', '2030-12-31', NOW()),
                                                                                 (2, 'POL456', 'LIC', '2029-06-15', NOW());

-- ======================
-- PATIENT
-- ======================
INSERT INTO patient (id, name, birth_date, email, gender, blood_group, created_at, patient_insurance_id) VALUES
                                                                                                             (1, 'Rohan', '2003-05-10', 'rohan@gmail.com', 'Male', 'O_POSITIVE', NOW(), 1),
                                                                                                             (2, 'Amit', '2000-08-20', 'amit@gmail.com', 'Male', 'A_POSITIVE', NOW(), 2);

-- ======================
-- DEPARTMENT
-- ======================
INSERT INTO department (id, name, head_doctor_id) VALUES
                                                      (1, 'Cardiology', 1),
                                                      (2, 'Neurology', 2);

-- ======================
-- DEPARTMENT_DOCTORS (Many-to-Many)
-- ======================
INSERT INTO department_doctors (dpt_id, doctor_id) VALUES
                                                       (1, 1),
                                                       (1, 3),
                                                       (2, 2);

-- ======================
-- APPOINTMENTS
-- ======================
INSERT INTO appointment (id, appointment_time, reason, doctor_id, patient_id) VALUES
                                                                                  (1, NOW() + INTERVAL '1 day', 'Heart checkup', 1, 1),
                                                                                  (2, NOW() + INTERVAL '2 day', 'Brain MRI consultation', 2, 2),
                                                                                  (3, NOW() + INTERVAL '3 day', 'Knee pain', 3, 1);
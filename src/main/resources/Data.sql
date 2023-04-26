INSERT INTO users (id, name, email, password, type)
    VALUES ('698e415e-ca7c-11ed-afa1-0242ac120002', 'Yen Vengerberg', 'yen@gmail.com', 'password', 'physician');

INSERT INTO occupations (id, name)
VALUES
    ('bd1d85c8-ca7c-11ed-afa1-0242ac120002', 'Gynecologist'),
    ('d5e7c80c-ca7c-11ed-afa1-0242ac120002', 'Family physicians'),
    ('db898962-ca7c-11ed-afa1-0242ac120002', 'Neurologists'),
    ('c07baa19-9933-4faf-8489-b81a127e4408', 'Radiologists'),
    ('6128b07b-3108-486a-890b-b52f1e227e1f', 'Cryptologist'),
    ('4a1621e4-05a4-11ec-9a03-0242ac130003', 'Pulmonologist'),
    ('5fca1384-05a4-11ec-9a03-0242ac130003', 'Oncologist'),
    ('6df1266e-05a4-11ec-9a03-0242ac130003', 'Psychologist'),
    ('7c8ecb0c-05a4-11ec-9a03-0242ac130003', 'Geriatrician'),
    ('899f8fd2-05a4-11ec-9a03-0242ac130003', 'Emergency Medicine Physician'),
    ('9771dd68-05a4-11ec-9a03-0242ac130003', 'Nurse Practitioner'),
    ('a43c53f4-05a4-11ec-9a03-0242ac130003', 'General Surgeon'),
    ('b21c6d7c-05a4-11ec-9a03-0242ac130003', 'Ophthalmologist'),
    ('27004ebf-7c16-419e-8592-a4ae0be2cddd', 'Homeopath'),
    ('8d33cd28-e35a-4f50-8162-4af412f0ae8a', 'Mentalist'),
    ('bfc1ef0c-05a4-11ec-9a03-0242ac130003', 'Pediatric Cardiologist');



INSERT INTO users (id, name, email, password, type)
    VALUES ('e89e027e-cb1f-11ed-afa1-0242ac120002', 'Giedrius Jocius', 'giedriusJocius@devBridge.com',
            'password', 'patient'),
            ('f5e843ba-ce74-11ed-afa1-0242ac120002', 'Patient patient', 'patient@yahoo.com', 'passowrd', 'patient'),
            ('387cfae8-cc10-11ed-afa1-0242ac120002','Admin Admin', 'Admin@gmail.com', 'password', 'admin' ),
            ('c4d695d2-cc18-11ed-afa1-0242ac120002', 'Jane Patrick', 'Jane@gmail.com', 'password', 'physician');

INSERT INTO timeslot (physicianid, date)
    VALUES ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-20 06:00'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-20 06:30'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-20 07:30'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-20 08:00'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-25 08:00'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-25 08:30'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-25 09:00'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-25 09:30');

INSERT INTO timeslot (physicianid, date)
    VALUES ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-20 08:00'),
           ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-20 08:30'),
           ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-20 09:00'),
           ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-21 09:30'),
           ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-21 10:00'),
           ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-22 09:30'),
           ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-22 10:00');

--booked timeslots
INSERT INTO timeslot (physicianid, date, patientid)
    VALUES ('698e415e-ca7c-11ed-afa1-0242ac120002', '2023-06-20 10:00', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-06-25 10:00', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3');

--patients
INSERT INTO users (id, name, email, password, type)
    VALUES  ('52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3', 'Graham Chapman', 'gra@ml', 'grah', 'patient'),
            ('a2246d5b-c433-48c8-a82b-d338976417a0', 'John Cleese', 'john@ml', 'john', 'patient' ),
            ('eb5cd7ba-f1be-4595-9da8-995e1fe08ed5', 'Terry Gilliam', 'terr@ml', 'terr', 'patient' ),
            ('8568a15d-b29e-41ae-845c-50cc94421f53', 'Eric Idle', 'eric@ml', 'eric', 'patient' ),
            ('7014e98a-b93d-4ec7-801d-c73e0cf9d5c0', 'Jones Terry', 'jone@ml', 'jone', 'patient' ),
            ('041eb52a-f0b7-4114-97eb-2bea7876b03d', 'Michael Palin', 'mich@ml', 'mich', 'patient');

----physicians
INSERT INTO users (id, name, email, password, type)
    VALUES ('cae5c6a8-ea81-497b-a7cb-6a02aa54f623', 'Triss Merigold', 'tris@gmail.com', 'tris', 'physician'),
           ('9a6acba7-310e-4cf9-ba70-6289775938a9', 'Lara Dorren', 'lara@gmail.com', 'lara', 'physician'),
           ('c8d96722-c86f-4554-8081-f4b4ac1e70b6', 'Sabrina Glevissig', 'sab@gmail.com', 'sabr', 'physician'),
           ('5ab747c7-3094-4d04-a93e-a104e27d6f60', 'Vil Gefortz', 'vil@gmail.com', 'vilg', 'physician'),
           ('37a55d08-8747-4c25-bb24-b1e857009acf', 'Lytta Neyd', 'lytt@gmail.com', 'lytt', 'physician'),
           ('60867f57-9fb1-45e4-9a34-0715ec32f476', 'Sorel Degerlund', 'sore@gmail.com', 'sore', 'physician');

INSERT INTO additional_physician_info (user_id, occupation_id)
    VALUES ('698e415e-ca7c-11ed-afa1-0242ac120002', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '8d33cd28-e35a-4f50-8162-4af412f0ae8a'),
           ('cae5c6a8-ea81-497b-a7cb-6a02aa54f623', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('9a6acba7-310e-4cf9-ba70-6289775938a9', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('c8d96722-c86f-4554-8081-f4b4ac1e70b6', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('5ab747c7-3094-4d04-a93e-a104e27d6f60', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('37a55d08-8747-4c25-bb24-b1e857009acf', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('60867f57-9fb1-45e4-9a34-0715ec32f476', '27004ebf-7c16-419e-8592-a4ae0be2cddd');
INSERT INTO additional_patient_info (user_id, gender, birth_date, phone, street, city,
                                     postal_code, country, emergency_name, emergency_surname,
                                     emergency_phone, emergency_relation)
    VALUES ('f5e843ba-ce74-11ed-afa1-0242ac120002', 'male', '1999-09-09', 861234567, 'Zalgirio g.', 'Vilnius', 'LT-88345', 'Lithuania',
            'John', 'Doe', 867654321, 'Dad');
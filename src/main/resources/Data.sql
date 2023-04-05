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
            ('387cfae8-cc10-11ed-afa1-0242ac120002','Admin ', 'Admin@gmail.com', 'password', 'admin' ),
            ('c4d695d2-cc18-11ed-afa1-0242ac120002', 'Jane Patrick', 'Jane@gmail.com', 'password', 'physician');



INSERT INTO additional_physician_info (user_id, occupation_id)
    VALUES ('698e415e-ca7c-11ed-afa1-0242ac120002', '27004ebf-7c16-419e-8592-a4ae0be2cddd'),
           ('c4d695d2-cc18-11ed-afa1-0242ac120002', '8d33cd28-e35a-4f50-8162-4af412f0ae8a');

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

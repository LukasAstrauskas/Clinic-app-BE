INSERT INTO occupations (id, name) VALUES
             ('6128b07b-3108-486a-890b-b52f1e227e1f', 'Cryptologist'),
             ('7c12de6b-39da-45ab-b03b-4c1a9b89de46', 'Acupuncturist'),
             ('3e7e0766-65e8-4196-8346-d1271f59b2e3', 'Herbalist'),
             ('1bc6279d-1a95-42b8-93f3-929d1c3689b2', 'Naturopath'),
             ('27004ebf-7c16-419e-8592-a4ae0be2cddd', 'Homeopath'),
             ('8d33cd28-e35a-4f50-8162-4af412f0ae8a', 'Mentalist');

    --patients
INSERT INTO users (id, name, surname, email, password, type) VALUES
('81fe6628-7f9f-4f2e-be41-a190aa369831', 'Hilda' ,'Herbert', 'clinicgunit2023@gmail.com','$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient'),
('52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3', 'Graham' ,'Chapman', 'gra@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient'),
('a2246d5b-c433-48c8-a82b-d338976417a0', 'John' ,'Cleese', 'john@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient' );

    --admins
INSERT INTO users (id, name, surname, email, password, type) VALUES
('387cfae8-cc10-11ed-afa1-0242ac120002', 'Admin' ,'Admin', 'Admin@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'admin' );

    --physicians
INSERT INTO users (id, name, surname, email, password, type, occupation_id) VALUES
('c4d695d2-cc18-11ed-afa1-0242ac120002', 'Jane' ,'Patrick', 'jane@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'physician', '8d33cd28-e35a-4f50-8162-4af412f0ae8a'),
('698e415e-ca7c-11ed-afa1-0242ac120002', 'Yen','Vengerberg', 'yen@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'physician', '3e7e0766-65e8-4196-8346-d1271f59b2e3');


    --additional Info
INSERT INTO additional_patient_info (user_id, gender, birth_date, phone, street, city, postal_code,
country, emergency_name, emergency_last_name, emergency_phone, emergency_relation) VALUES
    --John Cleese
('a2246d5b-c433-48c8-a82b-d338976417a0', 'male', '1978-12-01', 37062345678, 'Zalgiris g. 112',
'Vilnius', '09300', 'Lithuania', 'Nicolas', 'Brown', 37068765432, 'Father'),
    --Hilda Herbert
('81fe6628-7f9f-4f2e-be41-a190aa369831', 'female', '1977-12-01', 37062345678, 'Zalgiris g. 111',
'Vilnius', '09300', 'Lithuania', 'Frida', 'Herbert', 37068765432, 'Mother'),
    --Graham Chapman
('52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3', 'male', '1941-01-08', 37064405353, 'Actors st. 98',
                'Vilnius', '09300', 'Lithuania', 'David', 'Sherlock', 37064405842, 'Partner');

    --booked timeslots
INSERT INTO timeslot (id, physician_id, date, patient_id) VALUES
--Graham Chapman upcoming appointments
('ca1ab2bb-a71a-4a51-8f69-1ad6bd9b0ec6', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-11-25 10:00', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('957570db-0726-4165-a181-248d4e53c08f', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-08 06:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('1729c497-347d-4d0c-a4fd-30383e23a4d0', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-10-10 11:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),

    --Graham Chapman past appointments
('848055e2-fb9e-4ff9-901f-b870af05aee2', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-08-25 10:00', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('54cd004a-8455-434a-89b9-cd48cc2738b2', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-08-10 06:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('264901df-8f91-4af9-bb59-033beabf74e1', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-08-28 11:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3');

    --non booked timeslots Jane Patrick
INSERT INTO timeslot (id, physician_id, date) VALUES
 ('02a7d520-ee20-42d8-b110-f47d182bbfd1', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-05 08:30'),
 ('e4d6ff43-a83b-4bac-9f37-09caed081b9a', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-12 09:00'),
 ('601d6fe6-b111-4df0-bff6-1e8f464b9d90', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-25 09:30');
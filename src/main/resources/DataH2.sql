INSERT INTO occupations (id, name) VALUES
             ('6128b07b-3108-486a-890b-b52f1e227e1f', 'Cryptologist'),
             ('7c12de6b-39da-45ab-b03b-4c1a9b89de46', 'Acupuncturist'),
             ('3e7e0766-65e8-4196-8346-d1271f59b2e3', 'Herbalist'),
             ('1bc6279d-1a95-42b8-93f3-929d1c3689b2', 'Naturopath'),
             ('27004ebf-7c16-419e-8592-a4ae0be2cddd', 'Homeopath'),
             ('23d7fd7a-bed8-482e-985e-bbe70496310b', 'Chiropractor'),
             ('8d33cd28-e35a-4f50-8162-4af412f0ae8a', 'Mentalist');

    --patients
INSERT INTO users (id, name, surname, email, password, type) VALUES
('81fe6628-7f9f-4f2e-be41-a190aa369831', 'Hilda' ,'Herbert', 'hilda@gmail.com','$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient'),
('52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3', 'Graham' ,'Chapman', 'gra@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient'),
('7c0e706c-e82f-407f-8e94-6aef55d775a2', 'Terry' ,'Jones', 'terry@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient'),
('7014e98a-b93d-4ec7-801d-c73e0cf9d5c0', 'Jones', 'Terry', 'jone@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient' ),
('041eb52a-f0b7-4114-97eb-2bea7876b03d', 'Michael', 'Palin', 'mich@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient'),
('8568a15d-b29e-41ae-845c-50cc94421f53', 'Eric', 'Idle', 'eric@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient' ),
('eb5cd7ba-f1be-4595-9da8-995e1fe08ed5', 'Terry', 'Gilliam', 'terr@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient' ),
('a2246d5b-c433-48c8-a82b-d338976417a0', 'John' ,'Cleese', 'john@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'patient' );

    --admins
INSERT INTO users (id, name, surname, email, password, type) VALUES
('387cfae8-cc10-11ed-afa1-0242ac120002', 'Admin' ,'Admin', 'Admin@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'admin' );

    --physicians
INSERT INTO users (id, name, surname, email, password, type, occupation_id) VALUES
('9a6acba7-310e-4cf9-ba70-6289775938a9', 'Lara', 'Dorren', 'lara@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'physician','1bc6279d-1a95-42b8-93f3-929d1c3689b2'),
('c8d96722-c86f-4554-8081-f4b4ac1e70b6', 'Sabrina', 'Glevissig', 'sab@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'physician','23d7fd7a-bed8-482e-985e-bbe70496310b'),
('5ab747c7-3094-4d04-a93e-a104e27d6f60', 'Vil', 'Gefortz', 'vil@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'physician','6128b07b-3108-486a-890b-b52f1e227e1f'),
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
('957570db-0726-4165-a181-248d4e53c08f', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-11 06:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('1729c497-347d-4d0c-a4fd-30383e23a4d0', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-10-10 11:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),

--John Cleese upcoming appointments
('12e6f3b8-5ba0-4cf7-b8f8-16d8ebb72bf7', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-11-05 08:30', 'a2246d5b-c433-48c8-a82b-d338976417a0'),
('79a0ff9f-9b82-4175-ada5-83bd05726ede', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-11-08 08:30', 'a2246d5b-c433-48c8-a82b-d338976417a0'),
('78d6cfbd-4a61-4367-ad76-988602449d31', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-11-02 12:00', 'a2246d5b-c433-48c8-a82b-d338976417a0'),

--Hilda Herbert past appointments
('e57b9848-937c-4d3d-aa5d-6d9c52ed318a', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-05 10:00', '81fe6628-7f9f-4f2e-be41-a190aa369831'),
('17eef1e3-0a82-4b71-b83a-dccb02de2983', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-06 06:30', '81fe6628-7f9f-4f2e-be41-a190aa369831'),
('b9a50116-adf5-4cc2-b9d6-c71ec274c6d5', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-10-01 11:30', '81fe6628-7f9f-4f2e-be41-a190aa369831'),

    --Graham Chapman past appointments
('848055e2-fb9e-4ff9-901f-b870af05aee2', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-08-25 10:00', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('54cd004a-8455-434a-89b9-cd48cc2738b2', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-08-10 06:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3'),
('264901df-8f91-4af9-bb59-033beabf74e1', '698e415e-ca7c-11ed-afa1-0242ac120002', '2023-08-28 11:30', '52e2fc8e-d5b1-43e0-bde6-5dca5f96ced3');

    --non booked timeslots Jane Patrick
INSERT INTO timeslot (id, physician_id, date) VALUES
 ('02a7d520-ee20-42d8-b110-f47d182bbfd1', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-05 08:30'),
 ('e4d6ff43-a83b-4bac-9f37-09caed081b9a', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-12 09:00'),
 ('601d6fe6-b111-4df0-bff6-1e8f464b9d90', 'c4d695d2-cc18-11ed-afa1-0242ac120002', '2023-10-25 09:30');
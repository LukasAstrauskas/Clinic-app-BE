-- liquibase formatted sql
-- changeset lukas:1
CREATE TABLE IF NOT EXISTS occupations (
    id VARCHAR(36),
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
-- rollback DROP TABLE occupations;

-- changeset lukas:2
INSERT INTO occupations (id, name) VALUES
             (UUID(), 'Cryptologist'),
             (UUID(), 'Acupuncturist'),
             (UUID(), 'Herbalist'),
             (UUID(), 'Naturopath'),
             (UUID(), 'Homeopath'),
             (UUID(), 'Chiropractor'),
             (UUID(), 'Mentalist');

-- changeset lukas:3
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36),
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULl UNIQUE,
    password VARCHAR(255),
    type ENUM('patient', 'physician', 'admin') NOT NULL,
    occupation_id VARCHAR(36),
    PRIMARY KEY (id),
    CONSTRAINT fk_occupation_id FOREIGN KEY (occupation_id) REFERENCES occupations(id)
    ON DELETE SET NULL
);
-- rollback DROP TABLE users
--comment: all users table

-- changeset lukas:4
CREATE TABLE IF NOT EXISTS additional_patient_info (
    user_id VARCHAR(36) PRIMARY KEY,
    gender VARCHAR(255),
    birth_date DATE,
    phone BIGINT,
    street VARCHAR(255),
    city VARCHAR(255),
    postal_code VARCHAR(12),
    country VARCHAR(255),
    emergency_name VARCHAR(255),
    emergency_last_name VARCHAR(255),
    emergency_phone BIGINT,
    emergency_relation VARCHAR(255),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
);
-- rollback DROP TABLE additional_patient_info
--comment: more info about patient

-- changeset lukas:5
CREATE TABLE IF NOT EXISTS timeslot (
     id VARCHAR(36) PRIMARY KEY,
     physician_id VARCHAR(36) NOT NULL,
     date TIMESTAMP NOT NULl,
     patient_id VARCHAR(36),
     CONSTRAINT fk_physician_id FOREIGN KEY (physician_id) REFERENCES users(id)
     ON DELETE CASCADE,
     CONSTRAINT fk_patient_id FOREIGN KEY (patient_id) REFERENCES users(id)
     ON DELETE SET NULL
);
-- rollback DROP TABLE timeslot
--comment: physicians timeslots and appointments

-- changeset lukas:6
INSERT INTO users (id, name, surname, email, password, type) VALUES
('387cfae8-cc10-11ed-afa1-0242ac120002', 'Admin' ,'Admin', 'Admin@gmail.com', '$2y$10$wEzx1MK4T9EP/AsBZwGAOuELBhsLHH.zlNY7tsocCxTBi/qkprm7e', 'admin' );
-- rollback DELETE FROM users WHERE id='387cfae8-cc10-11ed-afa1-0242ac120002'
--comment: add first user admin
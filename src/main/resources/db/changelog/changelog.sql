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


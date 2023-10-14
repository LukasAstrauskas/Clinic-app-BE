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

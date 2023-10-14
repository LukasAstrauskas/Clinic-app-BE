CREATE TABLE occupations (
    id UUID,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id UUID,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULl UNIQUE,
    password VARCHAR(255),
    type ENUM('patient', 'physician', 'admin') NOT NULL,
    occupation_id UUID,
    PRIMARY KEY (id),
    CONSTRAINT fk_occupation_id FOREIGN KEY (occupation_id) REFERENCES occupations(id)
    ON DELETE SET NULL
);

CREATE TABLE additional_patient_info (
    user_id UUID PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS timeslot (
     id UUID PRIMARY KEY,
     physician_id UUID NOT NULL,
     date TIMESTAMP NOT NULl,
     patient_id UUID,
     CONSTRAINT fk_physician_id FOREIGN KEY (physician_id) REFERENCES users(id)
     ON DELETE CASCADE,
     CONSTRAINT fk_patient_id FOREIGN KEY (patient_id) REFERENCES users(id)
     ON DELETE SET NULL
);
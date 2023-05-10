CREATE TABLE users (
    id UUID,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULl UNIQUE,
    password VARCHAR(255),
    type ENUM('patient', 'physician', 'admin') NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE occupations (
    id UUID,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE additional_physician_info (
    user_id UUID,
    occupation_id UUID,
    CONSTRAINT fk_occupation_id FOREIGN KEY (occupation_id) REFERENCES occupations(id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
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
    CONSTRAINT fk_user_id_patient FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS timeslot (
     physicianid UUID NOT NULL,
     date TIMESTAMP NOT NULl,
     patientid UUID ,
     PRIMARY KEY (physicianid, date),
     CONSTRAINT fk_physician_id FOREIGN KEY (physicianid) REFERENCES users(id)
     ON DELETE CASCADE,
     CONSTRAINT fk_patient_id FOREIGN KEY (patientid) REFERENCES users(id)
     ON DELETE SET NULL
);
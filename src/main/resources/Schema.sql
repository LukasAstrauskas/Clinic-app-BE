CREATE TABLE users (
    id UUID,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULl UNIQUE,
    password VARCHAR(255),
--     type VARCHAR(255) NOT NULL,
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


--
-- CREATE TABLE additionalPatientInfo(
--     id UUID,
--     emergencyContact  VARCHAR(255),
--     homeAddress VARCHAR(255),
-- )
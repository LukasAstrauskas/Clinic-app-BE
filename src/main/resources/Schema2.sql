CREATE TABLE user (
    id UUID,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULl UNIQUE,
    password VARCHAR(255),
    type ENUM('patient', 'physician', 'admin') NOT NULL,
    info_id UUID,
    PRIMARY KEY (id)
--    CONSTRAINT fk_info_id FOREIGN KEY (info_id) REFERENCES occupation(id)
);
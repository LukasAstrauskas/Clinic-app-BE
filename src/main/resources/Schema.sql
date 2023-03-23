CREATE TABLE users (
    id UUID,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULl UNIQUE,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
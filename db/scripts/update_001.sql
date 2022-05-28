CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    visible BOOLEAN,
    city_id INTEGER
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    photo BYTEA
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT UNIQUE ,
    password TEXT
);

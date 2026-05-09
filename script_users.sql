CREATE DATABASE IF NOT EXISTS auth_db;
USE auth_db;

DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

INSERT INTO usuarios (username, password, rol) VALUES
('Joako', '1234', 'Admin'),
('Emanuel', '1234', 'Admin'),
('Paulo', '1234', 'Admin'),
('Benjamin','1234','Admin');

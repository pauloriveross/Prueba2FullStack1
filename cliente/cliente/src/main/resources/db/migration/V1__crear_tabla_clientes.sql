CREATE TABLE clientes(
    idCliente INTEGER PRIMARY KEY AUTO_INCREMENT,
    rutCliente VARCHAR(13) NOT NULL,
    nombreCliente VARCHAR(100) NOT NULL,
    apellidoCliente VARCHAR(100) NOT NULL,
    direccionCliente VARCHAR(100) NOT NULL
);
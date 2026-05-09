# 🚘Automotora
El proyecto "Automotora" corresponde a la segunda evaluación de la asignatura de FullStack 1, nuestro proyecto esta enfocado automotoras que estan recien empezando o que estan pasando a un sistema digital. 

## 🏗️Arqutectura del Proyecto
El sistema es diseñado bajo un enfoque de **microservicios**.

## Entidades
Las entidades que contiene el repositorio son las entidades comunes dentro de una automotora las cuales son:
- Vehiculo
- Cliente
- Vendedor
- Mecanico
- Mantenciones
- Ventas
- Auth-service 

## Entorno de Base de Datos
El entorno de ejecución que se ha utilizado fue **Laragon** donde se utilizo MySQL para gestionar la base de datos.

## 🛅Configuración de la Base de Datos
Debido a la arquitectura de microservicios, se requiere la creación de bases de datos independientes.
Los cuales son:
```sql
CREATE DATABASE auth_db;
CREATE DATABASE db_cliente;
CREATE DATABASE db_vehiculo;
CREATE DATABASE db_vendedor;
CREATE DATABASE db_venta;
CREATE DATABASE db_mantencion;
CREATE DATABASE db_mecanicos;
```
## 🔐Servicio de Autenticación (`auth_db`)
Este script inicializa la tabla de usuarios y los administradores por defecto:
```sql
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
('Benjamin', '1234', 'Admin');
````

## 📋Tablas 
Debido a que las tablas a la hora de ejecutar el proyecto no contienen datos, se deben insertar datos a su respectivas tablas. Pero en el caso de **Cliente** no es necesario  ya que contiene datos desde su ejecución.

## 🚙Vehiculo
```sql
INSERT INTO vehiculo
(annio_vehiculo, estado_Vehiculo, kilometraje_vehiculo, marca_vehiculo, modelo_vehiculo, patente_vehiculo, precio_vehiculo, tipo_vehiculo)
VALUES
(2018, 'Disponible', 65000, 'Toyota', 'Corolla', 'ABCD12', 8500000, 'Sedán'),
(2020, 'Vendido', 30000, 'Honda', 'Civic', 'EFGH34', 10500000, 'Sedán'),
(2017, 'Disponible', 80000, 'Chevrolet', 'Spark', 'IJKL56', 5500000, 'Hatchback'),
(2021, 'Reservado', 15000, 'Ford', 'Ranger', 'MNOP78', 15500000, 'Camioneta'),
(2019, 'Disponible', 45000, 'Hyundai', 'Tucson', 'QRST90', 12500000, 'SUV');
````

## 🧑🏻‍💼Vendedor
```sql

INSERT INTO vendedores(apellido_vendedor, email_vendedor, nombre_vendedor, rut_vendedor, seccion_vendedor, turno_vendedor) VALUES 
('González', 'mgonzalez@automotora.cl', 'Martín', '12.345.678-9', 'Ventas Autos Nuevos', 'Mañana'),
('Pérez', 'jperez@automotora.cl', 'Javiera', '15.987.654-3', 'Ventas Autos Usados', 'Tarde'),
('Ramírez', 'aramirez@automotora.cl', 'Andrés', '18.456.789-1', 'Ventas Autos Nuevos', 'Mañana'),
('Fernández', 'cfernandez@automotora.cl', 'Carolina', '20.123.456-7', 'Ventas Autos Usados', 'Mañana'),
('López', 'flopez@automotora.cl', 'Felipe', '21.234.567-8', 'Ventas Autos Nuevos', 'Tarde')
````

## 🧑🏻‍🔧Mecanico
```sql
INSERT INTO mecanicos 
(apellido_mecanico, nombre_mecanico, rut_mecanico)
VALUES
('Soto', 'Alejandro', '12.345.678-9'),
('Martínez', 'Camila', '15.987.654-3'),
('Reyes', 'Jorge', '18.456.789-1'),
('Fuentes', 'Valentina', '20.123.456-7');
````

## 🔧Mantenciones
```sql
INSERT INTO mantenciones
(fecha_mantencion, id_cliente, id_mecanico, id_vehiculo, precio_mantencion, tipo_mantencion)
VALUES
('2024-01-10 09:30:00', 1, 2, 3, 120000, 'Cambio de aceite'),
('2024-02-18 11:15:00', 4, 5, 7, 250000, 'Revisión general'),
('2024-03-22 14:45:00', 2, 1, 5, 180000, 'Cambio de frenos'),
('2024-04-05 16:20:00', 7, 3, 9, 220000, 'Alineación y balanceo'),
('2024-05-12 10:00:00', 5, 4, 2, 300000, 'Cambio de neumáticos');
````

## 💵Ventas
```sql
INSERT INTO ventas 
(fecha_venta, id_cliente, id_vehiculo, id_vendedor, precio_vehiculo, tipo_pago)
VALUES
('2024-01-15 10:30:00', 1, 1, 5, 8500000, 'Transferencia'),
('2024-02-20 16:45:00', 2, 2, 2, 10500000, 'Crédito'),
('2024-03-05 11:15:00', 3, 3, 4, 5500000, 'Efectivo'),
('2024-04-12 14:00:00', 4, 4, 6, 15500000, 'Transferencia'),
('2024-05-18 09:20:00', 5, 5, 1, 12500000, 'Crédito');
````

# 🚘Automotora
El proyecto "Automotora" corresponde a la segunda evaluación de la asignatura de FullStack 1, utilizando metodos **Post** en **PostMan**. Nuestro proyecto está enfocado en automotoras que están recién empezando o que están pasando a un sistema digital. 

## 🏗️Arquitectura del Proyecto
El sistema es diseñado bajo un enfoque de **microservicios**.

## Entidades
Las entidades que contiene el repositorio son las comunes dentro de una automotora, las cuales son:
- Vehiculo
- Cliente
- Vendedor
- Mecanico
- Mantenciones
- Ventas
- Auth-service 

## Entorno de Base de Datos
El entorno de ejecución que se ha utilizado fue **Laragon** donde se utilizó MySQL para gestionar las bases de datos.

## 🛅Configuración de la Base de Datos
Debido a la arquitectura de microservicios, se requiere la creación de bases de datos independientes, las cuales son:
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
USE auth_db;
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
Debido a que, al momento de ejecutar el proyecto, las entidades no contienen datos, se deben inserta registros en sus respectivas **tablas**. Sin embargo, en el caso de **Cliente**, no es necesario, ya que cuenta con datos desde su inicio. Por otro lado, **Ventas** y **Mantenciones** estarán vacías para que el usuario pueda agregarlas y manipularlas.

## 🚙Vehiculo
```sql
USE DB_VEHICULO;
INSERT INTO VEHICULO (ANNIO_VEHICULO,ESTADO_VEHICULO,KILOMETRAJE_VEHICULO,MARCA_VEHICULO,MODELO_VEHICULO,PATENTE_VEHICULO,
					PRECIO_VEHICULO,TIPO_VEHICULO)
VALUES
(2024, "Nuevo", 0, "Toyota", "RAV4", "RZXW10", 28990000, "SUV"),
(2018, "Usado", 85000, "Hyundai", "Accent", "KJBC44", 8500000, "Sedán"),
(2022, "Seminuevo", 15000, "Suzuki", "Swift", "PLRT22", 11200000, "Hatchback"),
(2023, "Nuevo", 0, "Ford", "F-150", "STVG55", 45500000, "Camioneta"),
(2015, "Usado", 120000, "Kia", "Morning", "HRPY89", 5800000, "City Car"),
(2021, "Usado", 42000, "Mazda", "CX-5", "NWQD31", 19700000, "SUV"),
(2024, "Nuevo", 0, "Chevrolet", "Sail", "TXZZ09", 10490000, "Sedán");
````

## 🧑🏻‍💼Vendedor
```sql
USE DB_VENDEDOR;

INSERT INTO VENDEDORES (APELLIDO_VENDEDOR,EMAIL_VENDEDOR,NOMBRE_VENDEDOR,RUT_VENDEDOR,SECCION_VENDEDOR,TURNO_VENDEDOR,SUELDO_BASE_VENDEDOR
)
VALUES
("Alvarado", "c.alvarado@automotora.cl", "Carlos", "16.894.231-5", "Ventas Vehiculos", "Mañana", 650000),
("Mendoza", "a.mendoza@automotora.cl", "Ana", "19.432.108-K", "Repuestos", "Tarde", 550000),
("Guzmán", "j.guzman@automotora.cl", "Jaime", "14.553.890-2", "Ventas Vehiculos", "Mañana", 720000),
("Paredes", "e.paredes@automotora.cl", "Elena", "21.004.637-4", "Repuestos", "Tarde", 530000),
("Cárcamo", "f.carcamo@automotora.cl", "Fernando", "17.331.902-8", "Ventas Vehiculos", "Rotativo", 800000),
("Vergara", "v.vergara@automotora.cl", "Valentina", "20.115.448-3", "Repuestos", "Mañana", 580000),
("San Martín", "m.sanmartin@automotora.cl", "Matías", "18.772.309-1", "Ventas Vehiculos", "Rotativo", 950000);
````

## 🧑🏻‍🔧Mecanico
```sql
USE DB_MECANICOS;

INSERT INTO MECANICOS (
    APELLIDO_MECANICO,NOMBRE_MECANICO,RUT_MECANICO,SUELDO_BASE_MECANICO
)
VALUES
("González", "Pedro", "14.285.331-4", 750000),
("Sanhueza", "Mauricio", "17.992.405-2", 680000),
("Contreras", "Cristián", "12.664.118-7", 850000),
("Araya", "Javier", "19.003.552-6", 620000),
("Vásquez", "Roberto", "15.774.229-K", 900000),
("Pizarro", "Ignacio", "16.448.903-5", 550000),
("Lagos", "Andrés", "13.551.002-0", 1100000);
````

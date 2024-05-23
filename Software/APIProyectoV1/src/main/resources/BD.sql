DROP DATABASE IF EXISTS api_hoteles;
CREATE DATABASE api_hoteles;
USE api_hoteles;

CREATE TABLE Hotel (
                       id_Hotel INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL,
                       descripcion VARCHAR (100),
                       categoria VARCHAR(1),
                       tiene_piscina BOOLEAN,
                       localidad VARCHAR(50) NOT NULL
);

INSERT INTO Hotel VALUES (1,'Olid','Centro','4',true,'Leon'),
                         (2,'Paris', 'Centro','3',false,'Valladolid'),
                         (3,'Lasa Sport', 'Deportivo','4',true,'Valladolid'),
                         (4,'Felipe IV', 'Centro','2',false,'Valladolid');

CREATE TABLE Habitacion (
                            id_Habitacion INT AUTO_INCREMENT PRIMARY KEY,
                            id_Hotel INT,
                            tamano INT,
                            capacidad INT,
                            precio_Noche DECIMAL(10, 2),
                            incluye_Desayuno BOOLEAN,
                            ocupada BOOLEAN,
                            FOREIGN KEY (id_Hotel) REFERENCES Hotel(id_Hotel) on delete cascade
);

INSERT INTO Habitacion (id_Habitacion, id_Hotel, tamano, capacidad, precio_Noche, incluye_Desayuno, ocupada)
VALUES
    (101, 1, 20, 2, 150.00, true, false),
    (102, 1, 60, 1, 100.00, false, false),
    (201, 2, 20, 2, 300.00, true, true),
    (202, 2, 30, 2, 280.00, false, false),
    (301, 3, 40, 1, 80.00, true, false),
    (302, 4, 200, 2, 120.00, false, true);
DROP DATABASE IF EXISTS bibliotecavirtual;

CREATE DATABASE bibliotecavirtual;

USE bibliotecavirtual;

CREATE TABLE persona(
	id_persona INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(45),
	primer_apellido VARCHAR(45),
	segundo_apellido VARCHAR(45),
	email VARCHAR(60),
	telefono VARCHAR(20),
	edad INT,
    fecha_nacimiento DATE
);

CREATE TABLE usuario (
	id_usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(20),
    contrasenia VARCHAR(129),
    rol VARCHAR(25) NOT NULL DEFAULT 'Usuario',
    estatus BIT DEFAULT 1 
);

-- ------------- TABLA ALUMNO -------------- --
CREATE TABLE alumno(
	id_alumno INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_persona INT NOT NULL,
	id_usuario INT NOT NULL,
	matricula VARCHAR(10),
	estatus BIT DEFAULT 1
);

-- ------------- TABLA UNIVERSIDAD -------------- --
CREATE TABLE universidad(
	id_universidad INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre_universidad VARCHAR(255),
	pais VARCHAR(255),
	estatus BIT
);


-- ------------- TABLA LIBRO -------------- --
CREATE TABLE libro(
	id_libro INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_universidad INT NOT NULL,
    titulo VARCHAR(50),
    autor VARCHAR(50),
    editorial VARCHAR(50),
    idioma VARCHAR(30),
    genero VARCHAR(30),
    no_paginas INT,
    libro LONG NOT NULL,
	estatus BIT,
    derecho_autor BIT,
	CONSTRAINT fk_libro_univerisidad FOREIGN KEY (id_universidad) REFERENCES universidad(id_universidad)
);

-- ------------- TABLA CONVENIO -------------- --
CREATE TABLE convenio(
	id_convenio INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_universidad INT NOT NULL,
	id_alumno INT NOT NULL,
    CONSTRAINT fk_convenio_universidad FOREIGN KEY (id_universidad)  REFERENCES universidad(id_universidad),
    CONSTRAINT fk_convenio_alumno FOREIGN KEY (id_alumno)  REFERENCES alumno(id_alumno)
);
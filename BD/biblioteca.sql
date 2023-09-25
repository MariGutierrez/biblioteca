DROP DATABASE IF EXISTS biblioteca;

CREATE DATABASE biblioteca;

USE biblioteca;

-- ------------- TABLA PERSONA -------------- --
CREATE TABLE persona(
	id_persona					INT NOT NULL PRIMARY KEY,
	nombre						VARCHAR(50),
	primer_apellido				VARCHAR(50) NOT NULL,
	segundo_apellido			VARCHAR(50),
	movil            			VARCHAR(20) NOT NULL DEFAULT '',
	sexo              			CHAR NOT NULL, -- Genero: M; F; O;
	activo 						BIT DEFAULT 1,
	fecha_registro 				DATETIME,
	usuario_registro 			INT
);

-- ------------- TABLA USUARIO -------------- --
CREATE TABLE usuario(
	id_usuario					INT NOT NULL PRIMARY KEY,
	cve_persona 				INT NOT NULL,
	nombre_usuario 				VARCHAR(50),
	contrasenia 				VARCHAR(50),
	activo 						BIT DEFAULT 1,
	fecha_registro 				DATETIME,
	usuario_registro 			INT,
	CONSTRAINT fk_usuario_persona FOREIGN KEY (cve_persona) REFERENCES persona(cve_persona)
);

-- ------------- TABLA TIPOS DE USUARIOS -------------- --
CREATE TABLE tipo_usuario(
    id_tipo_usuario      		INT NOT NULL PRIMARY KEY,
    nombre 						VARCHAR(50),
    activo 						BIT DEFAULT 1,
	fecha_registro 				DATETIME,
	usuario_registro 			INT
);

-- -------------------- TABLA LIBROS ------------------------ --
CREATE TABLE libro(
	id_libro					INT NOT NULL PRIMARY KEY,
	titulo						VARCHAR(100),
    autor						VARCHAR(50),
    genero						VARCHAR(50),
    num_paginas					VARCHAR(50),
    editorial					VARCHAR(50),
    edicion						VARCHAR(50)
);

#Falta investigar como subir el pdf


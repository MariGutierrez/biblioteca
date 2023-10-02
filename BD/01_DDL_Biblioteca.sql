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
    CONSTRAINT fk_alumno_persona FOREIGN KEY (id_persona) 
                REFERENCES persona(id_Persona),
    CONSTRAINT fk_alumno_usuario FOREIGN KEY (id_Usuario) 
                REFERENCES usuario(id_Usuario)
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
	estatus BIT default 1,
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





-- Stored Procedure para insertar nuevos Alumnos.
DROP PROCEDURE IF EXISTS insertarAlumno;
DELIMITER $$
CREATE PROCEDURE insertarAlumno(
									IN var_nombre VARCHAR(45),          -- 1
									IN var_primer_apellido VARCHAR(45), -- 2
                                    IN var_segundo_apellido VARCHAR(45),-- 3
                                    IN var_email VARCHAR(60),           -- 4
                                    IN var_telefono VARCHAR(20),        -- 5
							        IN var_edad INT,                    -- 6
                                    IN var_fecha_nacimiento DATE,       -- 7 
                                    IN var_nombre_usuario VARCHAR(20),  -- 8
                                    IN var_contrasenia VARCHAR(129),    -- 9
                                    IN var_rol VARCHAR(25),             -- 10
                                    IN var_matricula VARCHAR(10),       -- 11
    
								    OUT	var_id_persona       INT,       -- 12       
                                    OUT	var_id_usuario       INT,       -- 13   
                                    OUT	var_id_alumno        INT        -- 14
)
BEGIN
       -- Insertar en la tabla "persona"
    INSERT INTO persona(nombre, primer_apellido, segundo_apellido, email, telefono, edad, fecha_nacimiento)
    VALUES (var_nombre, var_primer_apellido, var_segundo_apellido, var_email, var_telefono, var_edad, var_fecha_nacimiento);
    
    -- Obtener el ID de la última inserción en la tabla "persona"
    SET var_id_persona = LAST_INSERT_ID();
    
    -- Insertar en la tabla "usuario"
    INSERT INTO usuario(nombre_usuario, contrasenia, rol)
    VALUES (var_nombre_usuario, var_contrasenia, var_rol);
    
    -- Obtener el ID de la última inserción en la tabla "usuario"
    SET var_id_usuario = LAST_INSERT_ID();

    -- Insertar en la tabla "alumno" con los IDs obtenidos
    INSERT INTO alumno(id_persona, id_usuario, matricula)
    VALUES (var_id_persona, var_id_usuario, var_matricula);
    
END $$
DELIMITER ;


-- Stored Procedure para editar nuevos Alumnos.
DROP PROCEDURE IF EXISTS actualizarAlumno;
DELIMITER $$
CREATE PROCEDURE actualizarAlumno(
									IN var_nombre VARCHAR(45),          -- 1
									IN var_primer_apellido VARCHAR(45), -- 2
                                    IN var_segundo_apellido VARCHAR(45),-- 3
                                    IN var_email VARCHAR(60),           -- 4
                                    IN var_telefono VARCHAR(20),        -- 5
							        IN var_edad INT,                    -- 6
                                    IN var_fecha_nacimiento VARCHAR(50),       -- 7 
                                    IN var_nombre_usuario VARCHAR(20),  -- 8
                                    IN var_contrasenia VARCHAR(129),    -- 9
                                    IN var_rol VARCHAR(25),             -- 10
                                    IN var_matricula VARCHAR(10),       -- 11
    
								    IN	var_id_persona       INT,       -- 12       
                                    IN	var_id_usuario       INT,       -- 13   
                                    IN	var_id_alumno      INT          -- 14
)
BEGIN 

-- Primero actualizamos los datos de la Persona
			UPDATE persona SET nombre = var_nombre, primer_apellido = var_primer_apellido,
            segundo_apellido = var_segundo_apellido, email = var_email, telefono = var_telefono, 
            edad = var_edad, fecha_nacimiento = var_fecha_nacimiento
            WHERE id_Persona = var_id_Persona;
    
-- Después los datos de usuario    
            UPDATE usuario  SET nombre_usuario = var_nombre_Usuario, 
                            contrasenia = var_contrasenia, rol = var_rol 
                        WHERE id_Usuario = var_id_Usuario;      
    END
$$
DELIMITER ;
    

CREATE VIEW v_alumnos AS
SELECT
    p.id_persona,
    p.nombre,
    p.primer_apellido,
    p.segundo_apellido,
    p.edad,
    p.email,
    p.telefono,
    u.id_usuario,
    u.nombre_usuario,
    u.rol,
    u.estatus,
    a.id_alumno,
    a.matricula
FROM
    persona p
INNER JOIN usuario u ON p.id_persona = u.id_usuario
INNER JOIN alumno a ON p.id_persona = a.id_persona;

USE bibliotecavirtual;

INSERT INTO usuario (id_usuario, nombre_usuario, contrasenia, rol, estatus) VALUES (1,'Maria','Maria2011','Administrador',1);

INSERT INTO universidad (nombre_universidad, pais, estatus)VALUES ('UNAM','México',1);


-- Stored Procedure para insertar nuevos Alumnos.
DROP PROCEDURE IF EXISTS insertarAlumno;
DELIMITER $$
CREATE PROCEDURE insertarAlumno(
									IN var_nombre VARCHAR(45), -- 1
									IN var_primer_apellido VARCHAR(45), -- 2
                                    IN var_segundo_apellido VARCHAR(45),-- 3
                                    IN var_email VARCHAR(60),-- 4
                                    IN var_telefono VARCHAR(20),-- 5
							        IN var_edad INT,-- 6
                                    IN var_fecha_nacimiento DATE,-- 7 
                                    IN var_nombre_usuario VARCHAR(20), -- 8
                                    IN var_contrasenia VARCHAR(129),-- 9
                                    IN var_rol VARCHAR(25),-- 10
                                    IN var_matricula VARCHAR(10),-- 11
    
								    OUT	var_id_persona       INT,     -- 12       
                                    OUT	var_id_usuario       INT,         -- 13   
                                    OUT	var_id_alumno      INT-- 14
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


-- Stored Procedure para insertar nuevos Alumnos.
DROP PROCEDURE IF EXISTS actualizarAlumno;
DELIMITER $$
CREATE PROCEDURE actualizarAlumno(
									IN var_nombre VARCHAR(45), -- 1
									IN var_primer_apellido VARCHAR(45), -- 2
                                    IN var_segundo_apellido VARCHAR(45),-- 3
                                    IN var_email VARCHAR(60),-- 4
                                    IN var_telefono VARCHAR(20),-- 5
							        IN var_edad INT,-- 6
                                    IN var_fecha_nacimiento DATE,-- 7 
                                    IN var_nombre_usuario VARCHAR(20), -- 8
                                    IN var_contrasenia VARCHAR(129),-- 9
                                    IN var_rol VARCHAR(25),-- 10
                                    IN var_matricula VARCHAR(10),-- 11
    
								    IN	var_id_persona       INT,     -- 12       
                                    IN	var_id_usuario       INT,         -- 13   
                                    IN	var_id_alumno      INT-- 14
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
    
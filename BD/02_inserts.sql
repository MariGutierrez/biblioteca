USE bibliotecavirtual;

INSERT INTO usuario (id_usuario, nombre_usuario, contrasenia, rol, estatus) VALUES (1,'Maria','Maria2011','Administrador',1);
SELECT * FROM usuario WHERE nombre_usuario = 'Maria' AND contrasenia = 'Maria2011';

INSERT INTO universidad (nombre_universidad, pais, estatus )VALUES ('UNAM','México',1);
SELECT * FROM universidad;

	INSERT INTO libro (id_universidad, titulo, autor, editorial, idioma, genero, no_paginas, libro, estatus, derecho_autor) VALUES (1, 'Aura', 'Carlos Fuentes', 'Editorial','Español','Suspenso',
	100, 'jiji',1,1);
	SELECT libro FROM libro;
    
    SELECT * FROM libro;
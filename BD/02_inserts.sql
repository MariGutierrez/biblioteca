USE bibliotecavirtual;

INSERT INTO usuario (id_usuario, nombre_usuario, contrasenia, rol, estatus) VALUES (1,'Maria','Maria2011','Administrador',1);
SELECT * FROM usuario WHERE nombre_usuario = 'Maria' AND contrasenia = 'Maria2011';
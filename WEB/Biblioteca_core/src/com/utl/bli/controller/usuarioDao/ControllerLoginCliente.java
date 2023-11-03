package com.utl.bli.controller.usuarioDao;

import com.utl.bli.controller.*;
import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLoginCliente {

    public String login(String nombre_usuario, String contrasenia) throws Exception {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usu = usuarioDao.BuscarUser(nombre_usuario);

        if (usu != null) {
            if (usu.getContrasenia().equals(contrasenia)) {
                return usu.getRol();
            } else {
                return "1";
            }
        } else {
            return "0";
        }
    }

}

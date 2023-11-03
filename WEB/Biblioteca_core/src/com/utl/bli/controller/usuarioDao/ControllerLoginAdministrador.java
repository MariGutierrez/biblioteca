package com.utl.bli.controller.usuarioDao;

import com.utl.bli.appService.UsuariosAppService;
import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.utl.bli.controller.usuarioDao.UsuarioDao;
import com.utl.bli.viewModel.UsuarioPublicViewModel;

public class ControllerLoginAdministrador {

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
    
    public Usuario buscUs(String nombre_usuario) throws Exception {

        UsuariosAppService cl = new UsuariosAppService();
        Usuario usu = cl.buscarPorCorreo(nombre_usuario);

        if (usu != null) {
                return usu;
        } else {
            return usu;
        }
    }
    
    public UsuarioPublicViewModel buscUs_vm(String nombre_usuario) throws Exception {

        UsuariosAppService cl = new UsuariosAppService();
        UsuarioPublicViewModel usu = cl.buscarPorPublic(nombre_usuario);
        if (usu != null) {
                return usu;
        } else {
            return usu;
        }
    }
}

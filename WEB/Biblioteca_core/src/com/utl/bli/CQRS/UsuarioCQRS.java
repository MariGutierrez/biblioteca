/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utl.bli.CQRS;

import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.controller.usuarioDao.UsuarioDao;
import com.utl.bli.model.Alumno;
import com.utl.bli.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Gali
 */
public class UsuarioCQRS {

    public Usuario insert(Usuario u) throws SQLException, Exception {
        Usuario us = new Usuario(0, "a", "a", "a",  "a", "a", false);
        UsuarioDao usuarioDao = new UsuarioDao();
        if (u.getNombre_usuario() != null && u.getContrasenia() != null) {
            u.setRol("Cliente");
            u.setEstatus(false);
            int res = usuarioDao.insert(u);
            if (res != 0) {
                u.setId_usuario(res);
                return u;
            } else {
                return us;
            }
        } else {
            throw new Exception("El uuario y la cntraseña no deben estar vacios");
        }
    }

    public void reestablecerContrasenia(Usuario u) throws Exception {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario actual = usuarioDao.BuscarUser(u.getNombre_usuario());
        if(actual.getContrasenia() != u.getContrasenia())
        {
            actual.setContrasenia(u.getContrasenia());
            usuarioDao.update(actual);
        }
        else
        {
            throw new Exception("La contraseña es igual a la anterior");
        }
    }
}

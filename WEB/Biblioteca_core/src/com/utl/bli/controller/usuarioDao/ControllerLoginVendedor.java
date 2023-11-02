package com.utl.bli.controller;

import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* @author maria*/
public class ControllerLogin {
    public Usuario validarSesion(String usuario, String contrasenia) throws SQLException {
        String sql = "SELECT * FROM usuario "
                + "WHERE nombre_usuario = " + usuario + " AND contrasenia = " + contrasenia;

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        Usuario u = null;
        
        while (rs.next()) {
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre_usuario(rs.getString("nombre_usuario"));
            u.setContrasenia(rs.getString("contrasenia"));
            u.setRol(rs.getString("rol"));
            u.setEstatus(rs.getBoolean("estatus"));
        
        }
        return u;
    }

    public Usuario login(String nombre_usuario, String contrasenia) throws Exception {

        String sql = "SELECT * FROM usuario WHERE nombre_usuario = ? AND contrasenia = ?;";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = null;  
        
        pstmt.setString(1, nombre_usuario);
        pstmt.setString(2, contrasenia);
        
        rs = pstmt.executeQuery();
        
        Usuario usu= null;
        
        if (rs.next())
        {
            usu = fill(rs);
        }
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return usu;
    }
    
    private Usuario fill(ResultSet rs) throws Exception{
        Usuario u = new Usuario();
        
        u.setId_usuario(rs.getInt("id_usuario"));
        u.setNombre_usuario(rs.getString("nombre_usuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));
        u.setEstatus(rs.getBoolean("estatus"));
        
        return u;
    }
}

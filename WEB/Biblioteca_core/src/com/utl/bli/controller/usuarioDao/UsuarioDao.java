package com.utl.bli.controller.usuarioDao;

import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    public Usuario BuscarUser(String nombre_usuario) throws Exception {

        String sql = "SELECT * FROM usuario WHERE nombre_usuario = ?;";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = null;

        pstmt.setString(1, nombre_usuario);

        rs = pstmt.executeQuery();

        Usuario usu = null;

        if (rs.next()) {
            usu = fill(rs);
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return usu;
    }

    private Usuario fill(ResultSet rs) throws Exception {
        Usuario u = new Usuario();

        u.setId_usuario(rs.getInt("id_usuario"));
        u.setNombre_usuario(rs.getString("nombre_usuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));
        u.setEstatus(rs.getBoolean("estatus"));
        u.setTelefono(rs.getString("telefono"));
        u.setRfc(rs.getString("rfc"));

        return u;
    }

    public int insert(Usuario u) throws SQLException, Exception {
        if (u == null) {
            

            return 0;
        } else {
            String sql = "Insert into usuario values(0,?,?,?,?,?,?)";

            ConexionMySQL connMySQL = new ConexionMySQL();
            Connection conn = connMySQL.open();

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, u.getNombre_usuario());
            cs.setString(2, u.getContrasenia());
            cs.setString(3, u.getRol());
            cs.setBoolean(4, u.getEstatus());
            cs.setString(5, u.getTelefono());
            cs.setString(6, u.getRfc());

            cs.execute(); // Cambié cs.executeUpdate() a cs.execute()
            
            cs.close();
            connMySQL.close();
            Usuario  us = BuscarUser(u.getNombre_usuario());
            return us.getId_usuario();
        }
    }
    
    public int update(Usuario u) throws SQLException, Exception {
        if (u == null) {
            

            return 0;
        } else {
            String sql = "UPDATE usuario SET nombre_usuario = ?, contrasenia = ?, rol = ?, estatus = ?, telefono = ?, rfc = ? WHERE id_usuario ="+u.getId_usuario()+";";

            ConexionMySQL connMySQL = new ConexionMySQL();
            Connection conn = connMySQL.open();

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, u.getNombre_usuario());
            cs.setString(2, u.getContrasenia());
            cs.setString(3, u.getRol());
            cs.setBoolean(4, u.getEstatus());
            cs.setString(5, u.getTelefono());
            cs.setString(6, u.getRfc());

            cs.execute(); // Cambié cs.executeUpdate() a cs.execute()
            
            cs.close();
            connMySQL.close();
            Usuario  us = BuscarUser(u.getNombre_usuario());
            return us.getId_usuario();
        }
    }
}

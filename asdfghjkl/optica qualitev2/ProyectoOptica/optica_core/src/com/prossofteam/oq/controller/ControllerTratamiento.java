package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.Tratamiento;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author ximer
 */
public class ControllerTratamiento {
public void insertarTratamiento(Tratamiento t) throws SQLException{
        String sql = "INSERT INTO tratamiento (nombre, precioCompra, precioVenta) VALUES(?,?,?);";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement ctm = conn.prepareCall(sql);
        ctm.setString(1, t.getNombre());
        ctm.setDouble(2, t.getPrecioCompra());
        ctm.setDouble(3, t.getPrecioVenta());
        
        ctm.executeUpdate();
        
        ctm.close();
        connMySQL.close();
    }
    
    public void actualizarTratamiento(Tratamiento t) throws SQLException{
        String sql = "UPDATE tratamiento SET nombre = ?, precioCompra = ?, precioVenta = ? WHERE idTratamiento = ?;";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement ctm = conn.prepareCall(sql);
        ctm.setString(1, t.getNombre());
        ctm.setDouble(2, t.getPrecioCompra());
        ctm.setDouble(3, t.getPrecioVenta());
        ctm.setInt(4, t.getIdTratamiento());
        
        ctm.executeUpdate();
        
        ctm.close();
        connMySQL.close();
    }
    
    public void borrarTratamiento(int id) throws Exception {
        String sql = "UPDATE tratamiento SET estatus = 0 WHERE idTratamiento = "+id;
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        Statement stm = conn.createStatement();
        stm.executeUpdate(sql);
        stm.close();
        connMySQL.close();
    }
    
    public List<Tratamiento> obtenerTratamiento(String filtro) throws Exception {
        String sql = "SELECT * FROM tratamiento";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement ptm = conn.prepareStatement(sql);
        ResultSet rs = ptm.executeQuery();
        List<Tratamiento> tratamientos = new ArrayList<>();

        while (rs.next()) {
            tratamientos.add(fill(rs));
        }
        rs.close();
        ptm.close();
        connMySQL.close();
        return tratamientos;
    }
    
    private Tratamiento fill(ResultSet rs) throws Exception {
        Tratamiento t = new Tratamiento();
        
        t.setIdTratamiento(rs.getInt("idTratamiento"));
        t.setNombre(rs.getString("nombre"));
        t.setPrecioCompra(rs.getDouble("precioCompra"));
        t.setPrecioVenta(rs.getDouble("precioVenta"));
        t.setEstatus(rs.getInt("estatus"));
        return t;
    }    
}

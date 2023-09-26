package com.prossofteam.oq.controller;

/**
 *
 * @author ximer
 */
import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.Material;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
public class ControllerMaterial {
    
    public List<Material> getAll() throws Exception {
        String sql = "SELECT * FROM material";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement ptm = conn.prepareStatement(sql);
        ResultSet rs = ptm.executeQuery();
        List<Material> materiales = new ArrayList<>();

        while (rs.next()) {
            materiales.add(fill(rs));
        }
        rs.close();
        ptm.close();
        connMySQL.close();
        return materiales;
    }
    
    private Material fill(ResultSet rs) throws Exception {
        Material m = new Material();
        
        m.setIdMaterial(rs.getInt("idMaterial"));
        m.setNombre(rs.getString("nombre"));
        m.setPrecioCompra(rs.getDouble("precioCompra"));
        m.setPrecioVenta(rs.getDouble("precioVenta"));
        return m;
    }
    
}

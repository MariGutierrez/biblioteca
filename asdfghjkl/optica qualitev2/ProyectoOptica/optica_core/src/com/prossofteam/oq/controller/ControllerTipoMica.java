package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.TipoMica;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ximer
 */
public class ControllerTipoMica {
    
    
    public List<TipoMica> getAll() throws Exception {
        String sql = "SELECT * FROM tipo_mica";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement ptm = conn.prepareStatement(sql);
        ResultSet rs = ptm.executeQuery();
        List<TipoMica> tiposMica = new ArrayList<>();

        while (rs.next()) {
            tiposMica.add(fill(rs));
        }
        rs.close();
        ptm.close();
        connMySQL.close();
        return tiposMica;
    }
    
    private TipoMica fill(ResultSet rs) throws Exception {
        TipoMica tm = new TipoMica();
        tm.setIdTipoMica(rs.getInt("idTipoMica"));
        tm.setNombre(rs.getString("nombre"));
        tm.setPrecioCompra(rs.getDouble("precioCompra"));
        tm.setPrecioVenta(rs.getDouble("precioVenta"));
        return tm;
    }
}

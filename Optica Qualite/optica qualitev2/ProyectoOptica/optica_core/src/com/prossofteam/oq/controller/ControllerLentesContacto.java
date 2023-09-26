package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.LenteContacto;
import com.prossofteam.oq.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ControllerLentesContacto {
    public List<LenteContacto> getAll(String filtro) throws Exception
    {
        //aqui se ejecuta la consulta sql
        String sql = "SELECT * FROM v_lentes_contacto";
        
        //Con este objeto se conecta a la Base de Datos
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //aqui se abre la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //con esto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí se guardan los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();  
        
        List<LenteContacto> lenteContactos = new ArrayList<>();
        
        while (rs.next())
            lenteContactos.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return lenteContactos;
    }
    private LenteContacto fill(ResultSet rs) throws Exception
    {
        LenteContacto lc = new LenteContacto();
        Producto p = new Producto();
        
        lc.setFotografia(rs.getString("fotografia"));
        lc.setKeratometria(rs.getInt("keratometria"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setPrecioCompra(rs.getInt("precioCompra"));
        p.setPrecioVenta(rs.getInt("precioVenta"));
        p.setExistencias(rs.getInt("existencias"));
        p.setEstatus(rs.getInt("estatus"));
        p.setIdProducto(rs.getInt("idProducto"));
        lc.setIdLenteContacto(rs.getInt("idLenteContacto"));
       
        
        lc.setProducto(p);
        return lc;   
    }
    
}

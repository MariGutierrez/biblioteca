
package com.prossofteam.oq.controller;

import java.sql.Statement;
import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.DetalleVentaProducto;
import com.prossofteam.oq.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ximer
 */
public class ControllerVenta {
    
    public List<Producto> getAll (String filtro) throws SQLException{
        String sql = "SELECT * FROM productos WHERE estatus";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement ptm = conn.prepareStatement(sql);
        ResultSet rs = ptm.executeQuery();
        
        List<Producto> productos = new ArrayList<>();
        
        while(rs.next()){
            productos.add(fill(rs));
        }
        
        rs.close();
        ptm.close();
        connMySQL.close();
        return productos;
    }
    
    public List<Producto> buscar(String filtro) throws Exception {
        
        String sql;

        sql = "SELECT * FROM producto WHERE nombre like '%" + filtro + "%'"
                + "OR codigoBarras = '" + filtro + "'";

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí guardaremos los resultados de la consulta:
        //ResultSet rs = null;
        ResultSet rs = pstmt.executeQuery();

        List<Producto> productos = new ArrayList<>();

        while (rs.next()) {
            productos.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return productos;
    }
    
    
    private Producto fill(ResultSet rs) throws SQLException{
        
        Producto p = new Producto();
        
        p.setIdProducto(rs.getInt("idProducto"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setPrecioCompra(rs.getDouble("precioCompra"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));
        p.setExistencias(rs.getInt("existencias"));
        p.setEstatus(rs.getInt("estatus"));
        
        return p;       
        
    }
    
    public boolean generarVenta(DetalleVentaProducto objVP){
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        Statement stmt =null;
        ResultSet rs= null;
        
        boolean r=false;
        
        try {
            conn.setAutoCommit(false);
            String query1= "INSERT INTO venta(clave,idEmpleado) VALUES("+objVP.getVenta().getClave()+","+objVP.getVenta().getEmpleado().getIdEmpleado()+")";
            stmt = conn.createStatement();
            stmt.execute(query1);
            
            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmt.executeQuery(query2);
            if (rs.next()) {
                objVP.getVenta().setIdVenta(rs.getInt(1));
            }
            
            for (int i = 0; i < objVP.getListaProductos().size(); i++) {
                String query3 = "INSERT INTO venta_producto VALUES ("+objVP.getVenta().getIdVenta()
                                +","+objVP.getListaProductos().get(i).getProducto().getIdProducto()
                                +","+objVP.getListaProductos().get(i).getCantidad()
                                +","+objVP.getListaProductos().get(i).getPrecioUnitario()
                                +","+objVP.getListaProductos().get(i).getDescuento()+");";
                                stmt.execute(query3);
            }
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();
            connMySQL.close();
            r=true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
                r=false;
            } catch (SQLException ex1) {
                Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex1);
                r=false;
            }
        }
        
        return r;
    
    }
    
}
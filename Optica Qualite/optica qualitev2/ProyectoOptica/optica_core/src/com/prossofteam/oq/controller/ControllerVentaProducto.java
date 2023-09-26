/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.DetalleVentaProducto;
import com.prossofteam.oq.model.Producto;
import com.prossofteam.oq.model.VentaProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class ControllerVentaProducto {

    public List<Producto> getAll(String filtro) throws Exception {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM producto WHERE nombre LIKE '%" + filtro +"%'OR codigoBarras='"+filtro+"'";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí guardaremos los resultados de la consulta:
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
    
    private Producto fill(ResultSet rs) throws Exception {
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

    public List<Producto> buscar(String filtro, String estatus) throws Exception {
        //La consulta SQL a ejecutar:
        String sql;

        //sql=armarConultaSQL(filtro, estatus);
        sql = "SELECT * FROM producto WHERE (nombre like '%" + filtro + "%'"
                + "or codigoBarras like '%" + filtro + "%')"
                + "and estatus =" + estatus;

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

    

public boolean transaccionarVenta(DetalleVentaProducto dvp) {
        
            boolean r = false;
            ConexionMySQL conMysql = new ConexionMySQL();
            Connection conn = conMysql.open();
            Statement stmt= null;
            ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String query = "INSERT INTO venta(idEmpleado, clave) VALUES(" + dvp.getVenta().getEmpleado().getIdEmpleado()
                    + "," + dvp.getVenta().getClave()+");";
            System.out.println(query);
            stmt.execute(query);

            String query2 = "SELECT LAST_INSERT_ID();";
            rs = stmt.executeQuery(query2);
            if(rs.next())
            {
                dvp.getVenta().setIdVenta(rs.getInt(1));
            }
            
            for(VentaProducto vp : dvp.getListaProductos())
            {
                String query3 = "INSERT INTO venta_producto(idVenta, idproducto, precioUnitario, cantidad, descuento) VALUES(" 
                        + dvp.getVenta().getIdVenta()
                        + "," + vp.getProducto().getIdProducto()
                        + "," + vp.getPrecioUnitario()
                        + "," + vp.getCantidad()
                        + "," + vp.getDescuento()+");";
                System.out.println(query3);
                stmt.execute(query3);
                
                int exis = vp.getProducto().getExistencias()-vp.getCantidad();
                
                String query4 = "UPDATE producto SET existencias="+exis+" where idProducto="+vp.getProducto().getIdProducto()+";";
                System.out.println(query4);
                stmt.executeUpdate(query4);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();
            r=true;
            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ControllerVentaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return r;
    }
}
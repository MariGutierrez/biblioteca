package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.DetalleVentaPre;
import com.prossofteam.oq.model.ExamenVista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
public class ControllerVentaLC {

    
    public boolean generarLC(DetalleVentaPre dvpr) {
        boolean r = false;

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String query1 = "INSERT INTO venta(idEmpleado, clave) VALUES("
                    + dvpr.getVenta().getEmpleado().getIdEmpleado() + ","
                    + dvpr.getVenta().getClave() + ");";

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.execute(query1);

            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmt.executeQuery(query2);

            if (rs.next()) {
                dvpr.getVenta().setIdVenta(rs.getInt(1));
            }

            for (int i = 0; i < dvpr.getVentaPresupuesto().size(); i++) {

                String query3 = "INSERT INTO venta_presupuesto VALUES(idVenta, idPresupuesto, cantidad, precioUnitario,descuento) VALUES ("
                        + dvpr.getVenta().getIdVenta() + "," + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getIdPresupuestoLC() + ","
                        + dvpr.getVentaPresupuesto().get(i).getCantidad() + ","
                        + dvpr.getVentaPresupuesto().get(i).getPrecioUnitario() + ","
                        + dvpr.getVentaPresupuesto().get(i).getDescuento() + ");";

                stmt.execute(query3);

            }

            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();

            r = true;

        } catch (SQLException ex) {
            try {
                Logger.getLogger(ControllerVentaLC.class.getName()).log(Level.SEVERE, null, ex);

                conn.rollback();
                conn.setAutoCommit(true);
                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
                r = false;

            } catch (SQLException ex1) {
                Logger.getLogger(ControllerVentaLC.class.getName()).log(Level.SEVERE, null, ex1);

            }
        }

        return r;

    }

    public boolean generarVentaLC(DetalleVentaPre dvpr) {
        boolean r = false;
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        Statement stmnt = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            stmnt = conn.createStatement();
            String query1 = "INSERT INTO venta (idEmpleado, clave) "
                    + "VALUES (" + dvpr.getVenta().getEmpleado().getIdEmpleado()
                    + ',' + dvpr.getVenta().getClave() + ");";
            stmnt.execute(query1);
            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmnt.executeQuery(query2);
            if (rs.next()) {
                dvpr.getVenta().setIdVenta(rs.getInt(1));
            }
            for (int i = 0; i < dvpr.getVentaPresupuesto().size(); i++) {
                String query3 = "INSERT INTO presupuesto"
                        + "(idExamenVista, clave)"
                        + "VALUES (" + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getExamenVista().getIdExamenVista()
                        + "," + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getClave() + " );";
                stmnt.execute(query3);
                //Se obtiene el id del presupuesto generado
                rs = stmnt.executeQuery(query2);
                if (rs.next()) {
                    dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().setIdPresupuesto(rs.getInt(1));
                }
                //Se inserta en presupuesto_lentescontacto                
                String query4 = "INSERT INTO presupuesto_lentescontacto"
                        + "(idExamenVista, idLenteContacto, clave)"
                        + "VALUES (" + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getExamenVista().getIdExamenVista() + ","
                        + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getLenteContacto().getIdLenteContacto() + ","
                        + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getClave() + ");";
                stmnt.execute(query4);
                rs = stmnt.executeQuery(query2);
                if (rs.next()) {
                    dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().setIdPresupuestoLC(rs.getInt(1));
                }
                //Se insera en venta_presupuesto la relación entre la venta y el presupuesto
                String query5 = "INSERT INTO venta_presupuesto "
                        + "(idVenta, idPresupuesto, cantidad, precioUnitario, descuento) "
                        + "VALUES ("
                        + dvpr.getVenta().getIdVenta() + ","
                        + dvpr.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getIdPresupuesto() + ","
                        + dvpr.getVentaPresupuesto().get(i).getCantidad() + ","
                        + dvpr.getVentaPresupuesto().get(i).getPrecioUnitario() + ","
                        + dvpr.getVentaPresupuesto().get(i).getDescuento() + ");";
                stmnt.execute(query5);
            }

            conn.commit();
            conn.setAutoCommit(true);
            stmnt.close();
            conn.close();
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ControllerVentaLC.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
                r = false;
            } catch (SQLException ex1) {
                r = false;
            }

        }
        return r;
    }

}

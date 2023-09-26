package com.prossofteam.oq.controller;

/**
 *
 * @author ximer
 */
import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.Cliente;
import com.prossofteam.oq.model.Empleado;
import com.prossofteam.oq.model.ExamenVista;
import com.prossofteam.oq.model.Graduacion;
import java.sql.SQLException;
import com.prossofteam.oq.model.Persona;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ControllerExamenVista {

   private ExamenVista fill(ResultSet rs) throws SQLException {
         ExamenVista ev = new ExamenVista();
        Empleado e = new Empleado();
        Cliente c = new Cliente();
        Graduacion g = new Graduacion();

        e.setIdEmpleado(rs.getInt("idEmpleado"));
        c.setIdCliente(rs.getInt("idCliente"));
        g.setIdGraduacion(rs.getInt("idGraduacion"));
        ev.setIdExamenVista(rs.getInt("idExamenVista"));
        ev.setClave(rs.getString("clave"));
        ev.setFecha(rs.getString("fecha"));
        ev.setCliente(c);
        ev.setEmpleado(e);
        ev.setGraduacion(g);
        return ev;
    }
    public List<ExamenVista> obtenerEV(String idCliente) throws SQLException {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM optiqalumnos.examen_vista WHERE idCliente = " + idCliente;

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();

        
        List<ExamenVista> examenDeVistas = new ArrayList<>();
        while (rs.next()) {
            examenDeVistas.add(fill(rs));
        }
        
        rs.close();
        pstmt.close();
        connMySQL.close();

        return examenDeVistas;
    }

    public List<ExamenVista> getAll(String filtro) throws Exception {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_examenvista_cliente";

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();

        List<ExamenVista> examenDeVistas = new ArrayList<>();

        while (rs.next()) {
            examenDeVistas.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return examenDeVistas;
    }
}

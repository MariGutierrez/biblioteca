/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utl.bli.controller;

import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Universidad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gali
 */
public class ControllerUniversidad {

    public void insert(Universidad c) throws Exception {

        //aqui se define la consulta que se jala de la BD
        //es muy importante que se cuente con las llaves pq 
        //si no no funciona, se define la consulta y cada 
        //signo de interrogacion es un valor
        String sql = "INSERT INTO universidad(?,?,?,?)";  // Valores de Retorno   ? 17 ?

        // Aqui se define el bloque de variables para guardar los id
        //se crean variables para guardar los id que se van a generar(no son forzosas)
        int idUniversidadGenerado = 0;
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        // creeamos una instancia de tipo ConexxionMySQL para hacer la conexion 
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        //con lo anterior se abre la coneccion 

        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);

        //se definen los valores de los parametros de los datos
        //personales en el orden en el que se piden en el
        //procedimiento almacenado comenzando en 1
        cstmt.setInt(1, idUniversidadGenerado);
        cstmt.setString(2, c.getNombre_universidad());
        cstmt.setString(3, c.getPais());
        cstmt.setInt(4, c.getEstatus());

        cstmt.executeUpdate();

        cstmt.close();
        connMySQL.close();

    }

    public void update(Universidad c) throws Exception {
        String sql = "UPDATE universidad nombre_universidad=?, pais=?, estatus=? WHEN id_universidad = ?";

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        // creeamos una instancia de tipo ConexxionMySQL para hacer la conexion 
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        //con lo anterior se abre la coneccion 

        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);

        //se definen los valores de los parametros de los datos
        //personales en el orden en el que se piden en el
        //procedimiento almacenado comenzando en 1
        cstmt.setString(1, c.getNombre_universidad());
        cstmt.setString(2, c.getPais());
        cstmt.setString(3, c.getEstatus() + "");
        cstmt.setInt(4, c.getId_universidad());

        cstmt.executeUpdate();

        cstmt.close();
        connMySQL.close();
    }

    public void delete(int id) throws Exception {

        String sql = "UPDATE universidad SET estatus = 0 WHERE id_universidad= " + id;

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        Statement cstmt = conn.createStatement();
        cstmt.executeUpdate(sql);
        cstmt.close();
        connMySQL.close();

    }

    public List<Universidad> getAll(String filtro) throws Exception {
        //aqui se ejecuta la consulta sql
        String sql = "SELECT * FROM universidad";

        //Con este objeto se conecta a la Base de Datos
        ConexionMySQL connMySQL = new ConexionMySQL();

        //aqui se abre la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //con esto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí se guardan los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();

        List<Universidad> universidades = new ArrayList<>();

        while (rs.next()) {
            universidades.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return universidades;
    }

    private Universidad fill(ResultSet rs) throws Exception {
        Universidad c = new Universidad();

        c.setId_universidad(rs.getInt("id_universidad"));
        c.setNombre_universidad(rs.getString("nombre_universidad"));
        c.setEstatus(rs.getInt("Estatus"));
        c.setPais(rs.getString("pais"));

        return c;
    }
}

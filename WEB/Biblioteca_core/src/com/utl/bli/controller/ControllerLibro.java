package com.utl.bli.controller;

import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Libro;
import com.utl.bli.model.Universidad;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/*@author Maria*/
public class ControllerLibro {
    
    public void insert(Libro l) throws Exception {
        String sql = "INSERT INTO libro VALUES (0,?,?,?,?,?,?,?,?,?,1);" ;
        
        int idLibroG = -1;

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        //CallableStatement cstmt = conn.prepareCall(sql);
        PreparedStatement ps = conn.prepareStatement(sql);    
        
        //cstmt.setInt(1, l.getId_libro());
        ps.setInt(1, l.getUniversidad().getId_universidad());
        ps.setString(2,l.getTitulo());        
        ps.setString(3, l.getAutor());
        ps.setString(4, l.getEditorial());
        ps.setString(5, l.getIdioma());
        ps.setString(6, l.getGenero());
        ps.setInt(7, l.getNo_paginas());
        ps.setString(8, l.getLibro());
        ps.setBoolean(9, l.isDerecho_autor());
        ps.executeUpdate();
        // Cerrar la conexion 
        ps.close();
        connMySQL.close();
    }

    
    public void update(Libro l) throws Exception {
        String sql = "UPDATE libro SET libro='"+l.getLibro()+" WHERE id_libro ="+l.getId_libro();

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        Statement cstmt = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement(sql); 
        //cstmt.executeUpdate(sql);
        ps.close();
        connMySQL.close();
    }
    
    public void delete(int id) throws Exception {

        String sql = "UPDATE libro SET estatus = 0 WHERE id_libro= " + id;

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        Statement cstmt = conn.createStatement();
        cstmt.executeUpdate(sql);
        cstmt.close();
        connMySQL.close();

    }
    
    public List<Libro> buscar(String filtro) throws Exception {
        String sql = "SELECT * FROM libro WHERE titulo LIKE '%"+filtro+"%'";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        List<Libro> libros = new ArrayList<>();

        while (rs.next()) {
            libros.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return libros;
    }

    public void verPDF(int id) throws SQLException, IOException{
        String sql = "SELECT libro FROM libro WHERE id_libro = ?;" ;
       
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = null;
        byte[] b = null;
        
        ps.setInt(1,id);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            b = rs.getBytes(1);
        }
        
        InputStream bos = new ByteArrayInputStream(b);
        
        int tamanoInput = bos.available();
        byte[] datosPDF = new byte[tamanoInput];
        bos.read(datosPDF,0,tamanoInput);
        
        OutputStream out = new FileOutputStream("new.pdf");
        out.write(datosPDF);
        
        // Cerrar la conexion 
        out.close();
        bos.close();
        ps.close();
        rs.close();
        connMySQL.close();
    }

    
    public List<Libro> getAll() throws Exception {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM libro;";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery(); 
        List<Libro> libros = new ArrayList<>();

        while (rs.next()) {
            libros.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return libros;
    }
    
    
    private Libro fill(ResultSet rs) throws Exception {
        Libro l = new Libro();
        
        l.setId_libro(rs.getInt("id_libro"));
        l.setTitulo(rs.getString("titulo"));
        l.setAutor(rs.getString("autor"));
        l.setEditorial(rs.getString("editorial"));
        l.setIdioma(rs.getString("idioma"));
        l.setGenero(rs.getString("genero"));
        l.setNo_paginas(rs.getInt("no_paginas"));
        l.setLibro(rs.getString("libro"));
        l.setEstatus(rs.getBoolean("estatus"));
        l.setDerecho_autor(rs.getBoolean("derecho_autor"));
        
        l.setUniversidad(new Universidad());
        l.getUniversidad().setId_universidad(rs.getInt("id_universidad"));
        
        return l;
    }
    
}
 
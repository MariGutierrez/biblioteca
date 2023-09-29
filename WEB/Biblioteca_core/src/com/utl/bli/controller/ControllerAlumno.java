package com.utl.bli.controller;

import com.utl.bli.bd.ConexionMySQL;
import com.utl.bli.model.Alumno;
import com.utl.bli.model.Persona;
import com.utl.bli.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ximer
 */

public class ControllerAlumno {

    public int insert(Alumno a) throws SQLException {

        String sql = "{cal insertarAlumno(?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?)}";

        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idAlumnoGenerado = -1;

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();


        CallableStatement cstmt = conn.prepareCall(sql);


        cstmt.setString(1, a.getIdPersona().getNombre());
        cstmt.setString(2, a.getIdPersona().getPrimer_apellido());
        cstmt.setString(3, a.getIdPersona().getSegundo_apellido());
        cstmt.setString(4, a.getIdPersona().getEmail());
        cstmt.setString(5, a.getIdPersona().getTelefono());
        cstmt.setInt(6, a.getIdPersona().getEdad());
        cstmt.setString(7, a.getIdPersona().getFecha_nacimiento());
        cstmt.setString(8, a.getIdUsuario().getNombre_usuario());
        cstmt.setString(9, a.getIdUsuario().getContrasenia());
        cstmt.setString(10, a.getIdUsuario().getRol());
        cstmt.setString(11, a.getMatricula());

        cstmt.registerOutParameter(12, Types.INTEGER);
        cstmt.registerOutParameter(13, Types.INTEGER);
        cstmt.registerOutParameter(14, Types.INTEGER);

        cstmt.executeUpdate();


        idPersonaGenerado = cstmt.getInt(12);
        idUsuarioGenerado = cstmt.getInt(13);
        idAlumnoGenerado = cstmt.getInt(14);

        a.getIdPersona().setId_persona(idPersonaGenerado);
        a.getIdUsuario().setId_usuario(idUsuarioGenerado);
        a.setId_alumno(idAlumnoGenerado);

        cstmt.close();
        connMySQL.close();

        return idAlumnoGenerado;

    }

    public void update(Alumno a) throws Exception {

        String sql = "{call actualizarAlumno(?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,? )}";

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        CallableStatement cstmt = conn.prepareCall(sql);

        cstmt.setString(1, a.getIdPersona().getNombre());
        cstmt.setString(2, a.getIdPersona().getPrimer_apellido());
        cstmt.setString(3, a.getIdPersona().getSegundo_apellido());
        cstmt.setString(4, a.getIdPersona().getEmail());
        cstmt.setString(5, a.getIdPersona().getTelefono());
        cstmt.setInt(6, a.getIdPersona().getEdad());
        cstmt.setString(7, a.getIdPersona().getFecha_nacimiento());
        cstmt.setString(8, a.getIdUsuario().getNombre_usuario());
        cstmt.setString(9, a.getIdUsuario().getContrasenia());
        cstmt.setString(10, a.getIdUsuario().getRol());
        cstmt.setString(11, a.getMatricula());

        cstmt.setInt(12, a.getIdPersona().getId_persona());
        cstmt.setInt(13, a.getIdUsuario().getId_usuario());
        cstmt.setInt(14, a.getId_alumno());

        cstmt.executeUpdate();

        cstmt.close();
        connMySQL.close();
    }
    
    
    public void delete(int id) throws Exception{
    
        String sql = "UPDATE alumno SET estatus= 0 WHERE id_alumno ="+ id;
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        Connection conn = connMySQL.open();
        
        
        Statement cstmt = conn.createStatement();
        cstmt.executeUpdate(sql);
        cstmt.close();
        connMySQL.close(); 
    }
    
    public List<Alumno> getAll(String filtro) throws Exception{
    
        String sql = "SELECT * FROM v_alumnos";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        

        Connection conn = connMySQL.open();
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        ResultSet rs = pstmt.executeQuery(); 
        
        List<Alumno> alumnos = new ArrayList<>();
     
        while (rs.next())
            alumnos.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return alumnos;
    }
    
    
    private Alumno fill(ResultSet rs) throws Exception{
        
        Alumno a = new Alumno();
        Persona p = new Persona();
        Usuario u = new Usuario();
        
        p.setId_persona(rs.getInt("id_persona"));
        p.setNombre(rs.getString("nombre"));
        p.setPrimer_apellido(rs.getString("primer_apellido"));
        p.setSegundo_apellido(rs.getString("segundo_apellido"));
        a.setMatricula(rs.getString("matricula"));
        a.setEstatus(rs.getInt("estatus"));
        
        a.setIdPersona(p);
        return a;
        
    }
    
}

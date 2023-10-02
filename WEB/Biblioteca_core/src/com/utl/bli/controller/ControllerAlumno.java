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
        String sql = "{call insertarAlumno(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        int idPersonaGenerado = -1;
        int idAlumnoGenerado = -1;
        int idUsuarioGenerado = -1;

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        CallableStatement cs = conn.prepareCall(sql);

        cs.setString(1, a.getPersona().getNombre());
        cs.setString(2, a.getPersona().getPrimer_apellido());
        cs.setString(3, a.getPersona().getSegundo_apellido());
        cs.setString(4, a.getPersona().getEmail());
        cs.setString(5, a.getPersona().getTelefono());
        cs.setInt(6, a.getPersona().getEdad());
        cs.setString(7, a.getPersona().getFecha_nacimiento());
        cs.setString(8, a.getUsuario().getNombre_usuario());
        cs.setString(9, a.getUsuario().getContrasenia());
        cs.setString(10, a.getUsuario().getRol());
        cs.setString(11, a.getMatricula());

        cs.registerOutParameter(12, Types.INTEGER);
        cs.registerOutParameter(13, Types.INTEGER);
        cs.registerOutParameter(14, Types.INTEGER);

        cs.execute(); // Cambié cs.executeUpdate() a cs.execute()

        idPersonaGenerado = cs.getInt(12);
        idAlumnoGenerado = cs.getInt(13);
        idUsuarioGenerado = cs.getInt(14);

        a.setId_alumno(idAlumnoGenerado);
        a.getPersona().setId_persona(idPersonaGenerado);
        a.getUsuario().setId_usuario(idUsuarioGenerado);

        cs.close();
        connMySQL.close();

        return idAlumnoGenerado;
    }

    public void update(Alumno a) throws Exception {
        // Define la consulta SQL que invoca al Stored Procedure:
        String sql = "{ call actualizarAlumno(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();

        CallableStatement cs = conn.prepareCall(sql);

        cs.setString(1, a.getPersona().getNombre());
        cs.setString(2, a.getPersona().getPrimer_apellido());
        cs.setString(3, a.getPersona().getSegundo_apellido());
        cs.setString(4, a.getPersona().getEmail());
        cs.setString(5, a.getPersona().getTelefono());
        cs.setInt(6, a.getPersona().getEdad());
        cs.setString(7, a.getPersona().getFecha_nacimiento());
        cs.setString(8, a.getUsuario().getNombre_usuario());
        cs.setString(9, a.getUsuario().getContrasenia());
        cs.setString(10, a.getUsuario().getRol());
        cs.setString(11, a.getMatricula());
        
        cs.setInt(12, a.getPersona().getId_persona());
        cs.setInt(13, a.getUsuario().getId_usuario());
        cs.setInt(14, a.getId_alumno());

        cs.execute();

        cs.close();
        connMySQL.close();
    }

    public void delete(int id) throws Exception {

        String sql = "UPDATE usuario SET estatus= 0 WHERE id_usuario =" + id;

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        Statement cstmt = conn.createStatement();
        cstmt.execute(sql);
        cstmt.close();
        connMySQL.close();
    }

    public List<Alumno> getAll(String filtro) throws Exception {

        String sql = "SELECT * FROM v_alumnos";

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<Alumno> alumnos = new ArrayList<>();

        while (rs.next()) {
            alumnos.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return alumnos;
    }
    
    public List<Alumno> buscar(String filtro) throws Exception {
        //aqui se ejecuta la consulta sql
        String sql = "SELECT * FROM persona WHERE nombre LIKE '%" + filtro + "%'";


        //Con este objeto se conecta a la Base de Datos
        ConexionMySQL connMySQL = new ConexionMySQL();

        //aqui se abre la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //con esto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí se guardan los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();

        List<Alumno> alumnos = new ArrayList<>();

        while (rs.next()) {
            alumnos.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return alumnos;
    }

    private Alumno fill(ResultSet rs) throws Exception {

        Alumno a = new Alumno();
        Persona p = new Persona();
        Usuario u = new Usuario();

        p.setId_persona(rs.getInt("id_persona"));
        p.setNombre(rs.getString("nombre"));
        p.setPrimer_apellido(rs.getString("primer_apellido"));
        p.setSegundo_apellido(rs.getString("segundo_apellido"));
        p.setEdad(rs.getInt("edad"));
        p.setEmail(rs.getString("email"));
        p.setTelefono(rs.getString("telefono"));
        u.setId_usuario(rs.getInt("id_usuario"));
        u.setNombre_usuario(rs.getString("nombre_usuario"));
        u.setRol(rs.getString("rol"));
        u.setEstatus(rs.getBoolean("estatus"));
        a.setId_alumno(rs.getInt("id_alumno"));
        a.setMatricula(rs.getString("matricula"));

        a.setUsuario(u);
        a.setPersona(p);
        return a;

    }

}

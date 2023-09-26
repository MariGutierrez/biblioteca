package com.prossofteam.oq.controller;

import com.prossofteam.oq.model.Empleado;
import com.prossofteam.oq.model.Persona;
import com.prossofteam.oq.model.Usuario;
import com.prossofteam.oq.bd.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ControllerLogin {
    
    public Empleado login(String usuario, String contrasenia) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados VE WHERE VE.nombreUsuario = ? AND VE.contrasenia = ?";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // consultas para eso funciona el PreparedStattement 
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = null; // ejecuta instruccion de tipo SELECT 
        
        pstmt.setString(1, usuario);
        pstmt.setString(2, contrasenia);
        
        rs = pstmt.executeQuery();
        
        Empleado emp= null;
        
        if (rs.next())
        {
            emp= fill(rs);
        }
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return emp;
    }
    
    public void guardarToken(int idUsuario, String token) throws Exception
    {
        String sql ="{call guardarToken(?,?)}";
        
      ConexionMySQL connMySQL = new ConexionMySQL();
        
        // creeamos una instancia de tipo ConexxionMySQL para hacer la conexion 
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //con lo anterior se abre la coneccion 
        
        //Con este objeto invocaremos al StoredProcedure:
        PreparedStatement cstm = conn.prepareCall(sql);
        
        cstm.setString(1, token);
        cstm.setInt(2, idUsuario);
        cstm.executeUpdate();
        
        conn.close();
        connMySQL.close();
    }
        
    
    public boolean validarToken(String t) throws SQLException{
    boolean r = false;
    String query ="SELECT * FROM v_empleados WHERE lastToken='"+t+"'";
    
     ConexionMySQL conexionMySQL = new ConexionMySQL();
     
     Connection connection = conexionMySQL.open();
     Statement stmt = connection.createStatement();
     ResultSet rs = stmt.executeQuery(query);
     
     
     if(rs.next())
         r = true;
     
     stmt.close();
     connection.close();
     conexionMySQL.close();
     
        return r;
     
    }
    
    public boolean eliminarToken(Empleado e) throws SQLException{
    boolean r = false;
    String query ="UPDATE usuario SET lastToken='' WHERE idUsuario =?";
    
     ConexionMySQL conexionMySQL = new ConexionMySQL();
     
     Connection connection = conexionMySQL.open();
     
     PreparedStatement preparedStatement = connection.prepareCall(query);
     
     preparedStatement.setInt(1, e.getUsuario().getIdUsuario());
     preparedStatement.execute();
     
     r = true;
     
     preparedStatement.close();
     connection.close();
     conexionMySQL.close();
     
        return r;
    
    }
    
    private Empleado fill(ResultSet rs) throws Exception
    {
        Empleado e = new Empleado();
        Persona p = new Persona();
        
        p.setApellidoMaterno(rs.getString("apellidoMaterno"));
        p.setApellidoPaterno(rs.getString("apellidoPaterno"));
        p.setCalle(rs.getString("calle"));
        p.setCiudad(rs.getString("ciudad"));
        p.setColonia(rs.getString("colonia"));
        p.setCp(rs.getString("cp"));
        p.setEmail(rs.getString("email"));
        p.setEstado(rs.getString("estado"));
        p.setFechaNacimiento(rs.getString("fechaNacimiento"));
        p.setGenero(rs.getString("genero"));
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setNumero(rs.getString("numero"));
        p.setTelCasa(rs.getString("telcasa"));
        p.setTelMovil(rs.getString("telmovil"));
        
        e.setIdEmpleado(rs.getInt("idEmpleado"));        
        e.setNumeroUnico(rs.getString("numeroUnico"));               
        e.setUsuario(new Usuario());
        e.getUsuario().setContrasenia(rs.getString("contrasenia"));
        e.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        e.getUsuario().setNombre(rs.getString("nombreUsuario"));
        e.getUsuario().setRol(rs.getString("rol"));
        e.getUsuario().setLastToken(rs.getString("lastToken"));
        e.getUsuario().setDateLastToken(rs.getString("dateLastToken"));
        e.setNumeroUnico(rs.getString("numeroUnico"));
        
        e.setPersona(p);
        
        return e;
    }
   
}

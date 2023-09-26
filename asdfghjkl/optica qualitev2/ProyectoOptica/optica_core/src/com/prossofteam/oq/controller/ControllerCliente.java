package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.Persona;
import com.prossofteam.oq.model.Cliente;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ximer
 */
public class ControllerCliente {
    
    public int insert (Cliente c) throws Exception{
        
        
     //aqui se define la consulta que se jala de la BD
     //es muy importante que se cuente con las llaves pq 
     //si no no funciona, se define la consulta y cada 
     //signo de interrogacion es un valor

 String sql = "{call insertarCliente(?, ?, ?, ?, ?, ?, ?,"+//Datos personales
                                    "?, ?, ?, ?, ?, ?, ?,"+//Datos personales
                                    "?, ?, ?)}";  // Valores de Retorno   ? 17 ?
 
 // Aqui se define el bloque de variables para guardar los id
 //se crean variables para guardar los id que se van a generar(no son forzosas)
 
        int idPersonaGenerado = -1;
        int idClienteGenerado = -1;
        String numeroUnicoGenerado = "";
        
        
        
        
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
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoPaterno());
        cstmt.setString(3, c.getPersona().getApellidoMaterno());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getFechaNacimiento());
        cstmt.setString(6, c.getPersona().getCalle());
        cstmt.setString(7, c.getPersona().getNumero());
        cstmt.setString(8, c.getPersona().getColonia());
        cstmt.setString(9, c.getPersona().getCp());
        cstmt.setString(10, c.getPersona().getCiudad());
        cstmt.setString(11, c.getPersona().getEstado());
        cstmt.setString(12, c.getPersona().getTelCasa());
        cstmt.setString(13, c.getPersona().getTelMovil());
        cstmt.setString(14, c.getPersona().getEmail());
        
        
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.VARCHAR);
        

        cstmt.executeUpdate();
        
        
        
        idPersonaGenerado = cstmt.getInt(15);
        idClienteGenerado = cstmt.getInt(16);        
        numeroUnicoGenerado = cstmt.getString(17);
        
        c.setIdCliente(idClienteGenerado);
        c.getPersona().setIdPersona(idPersonaGenerado);
        c.setNumeroUnico(numeroUnicoGenerado);
        cstmt.close();
        connMySQL.close();
        
        
 return idClienteGenerado;
    }
    
    public void update(Cliente c) throws Exception{
    String sql = "{call actualizarCliente(?, ?, ?, ?, ?, ?, ?, "+//Datos personales
                                         "?, ?, ?, ?, ?, ?, ?, "+//Datos de seguridad
                                         "?)}";  //aidis             ? 15 ?
 
    
    
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
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoPaterno());
        cstmt.setString(3, c.getPersona().getApellidoMaterno());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getFechaNacimiento());
        cstmt.setString(6, c.getPersona().getCalle());
        cstmt.setString(7, c.getPersona().getNumero());
        cstmt.setString(8, c.getPersona().getColonia());
        cstmt.setString(9, c.getPersona().getCp());
        cstmt.setString(10, c.getPersona().getCiudad());
        cstmt.setString(11, c.getPersona().getEstado());
        cstmt.setString(12, c.getPersona().getTelCasa());
        cstmt.setString(13, c.getPersona().getTelMovil());
        cstmt.setString(14, c.getPersona().getEmail());
        
        
        cstmt.setInt(15, c.getPersona().getIdPersona());
        
        cstmt.executeUpdate();
        
        cstmt.close();
        connMySQL.close(); 
    }
    
    public void delete(int id) throws Exception{

        String sql = "UPDATE cliente SET estatus = 0 WHERE idCliente= "+ id;

        ConexionMySQL connMySQL = new ConexionMySQL();
        
        Connection conn = connMySQL.open();
        
        Statement cstmt = conn.createStatement();
        cstmt.executeUpdate(sql);
        cstmt.close();
        connMySQL.close();



    }
    
    public List<Cliente> getAll(String filtro) throws Exception
    {
        //aqui se ejecuta la consulta sql
        String sql = "SELECT * FROM v_clientes";
        
        //Con este objeto se conecta a la Base de Datos
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //aqui se abre la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //con esto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí se guardan los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();  
        
        List<Cliente> clientes = new ArrayList<>();
        
        while (rs.next())
            clientes.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return clientes;
    }
    
     private Cliente fill(ResultSet rs) throws Exception
    {
        Cliente c = new Cliente();
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
        
        p.setIdPersona(rs.getInt("idPersona"));
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNumeroUnico(rs.getString("numeroUnico"));  
        c.setEstatus(rs.getInt("estatus"));
        
        c.setPersona(p);
        return c;  
    }
}
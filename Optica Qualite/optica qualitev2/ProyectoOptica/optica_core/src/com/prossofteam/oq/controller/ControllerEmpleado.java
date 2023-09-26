package com.prossofteam.oq.controller;
import com.prossofteam.oq.model.Empleado;
import com.prossofteam.oq.model.Persona;
import com.prossofteam.oq.model.Usuario;
import com.prossofteam.oq.bd.ConexionMySQL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;


public class ControllerEmpleado
{
    public static boolean isAdmin(Empleado e)
    {
        if (e == null || e.getUsuario() == null || e.getUsuario().getNombre() == null){
            return false;
        }
        else{
            return e.getUsuario().getRol().trim().toLowerCase().equals("administrador");
         }
    }
    public int insert(Empleado e) throws Exception
    {
        // 1 
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, " + // Datos Personales
                                               "?, ?, ?, ?, ?, ?, ?, " +
                                               "?, ?, ?, " + // Datos de Seguridad
                                               "?, ?, ?, ?, ?)}";  // Valores de Retorno ? 22 ?
        
        // 2 definir el bloque de variables para guardar los id y valores que se generan 
        //Aquí guardaremos los ID's que se generarán:
        int idPersonaGenerado = -1;
        int idEmpleadoGenerado = -1;
        int idUsuarioGenerado = -1;
        String numeroUnicoGenerado = "";
        
        // se puede interpretar como que todavia no tengo valor 
        
        // 3 abir la conexion
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        // creeamos una instancia de tipo ConexxionMySQL para hacer la conexion 
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //con lo anterior se abre la coneccion 
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        
        // 4 llenar los parametros del CallableStatement
        //Establecemos los valores de los parametros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoPaterno());
        cstmt.setString(3, e.getPersona().getApellidoMaterno());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getFechaNacimiento());
        cstmt.setString(6, e.getPersona().getCalle());
        cstmt.setString(7, e.getPersona().getNumero());
        cstmt.setString(8, e.getPersona().getColonia());
        cstmt.setString(9, e.getPersona().getCp());
        cstmt.setString(10, e.getPersona().getCiudad());
        cstmt.setString(11, e.getPersona().getEstado());
        cstmt.setString(12, e.getPersona().getTelCasa());
        cstmt.setString(13, e.getPersona().getTelMovil());
        cstmt.setString(14, e.getPersona().getEmail());
        
        // te paso una valor de algun tipo en la posicion que este en la 
        // base de datos , llamo al odjeto lo que tiene y le pido el valor que necesito 
        // 17 de entrada y 5 de salida
        
        // Registramos parámetros de datos de seguridad:
        cstmt.setString(15, e.getUsuario().getNombre());
        cstmt.setString(16, e.getUsuario().getContrasenia());
        cstmt.setString(17, e.getUsuario().getRol());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(18, Types.INTEGER);
        cstmt.registerOutParameter(19, Types.INTEGER);
        cstmt.registerOutParameter(20, Types.INTEGER);
        cstmt.registerOutParameter(21, Types.VARCHAR);
        cstmt.registerOutParameter(22, Types.VARCHAR);
        
        // para parametros de salida 
        // tipo de parametro y tipo en sql 
        
        // 6 Ejecutar la consulta
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        
        //atrave del Callabel Statemt invoco al executeUpdate, a este punto se
        // guardar los datos en base de datos 
        
        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(18);
        idUsuarioGenerado = cstmt.getInt(19);
        idEmpleadoGenerado = cstmt.getInt(20);        
        numeroUnicoGenerado = cstmt.getString(21);
        
        e.setIdEmpleado(idEmpleadoGenerado);
        e.getPersona().setIdPersona(idPersonaGenerado);
        e.getUsuario().setIdUsuario(idUsuarioGenerado);
        e.setNumeroUnico(numeroUnicoGenerado);
        
        // cerrar la conexion 
        
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Empleado generado:
        return idEmpleadoGenerado;
    }
    
    public void update(Empleado e) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarEmpleado(  ?, ?, ?, ?, ?, ?, ?, " + //Datos Personales
                                                   "?, ?, ?, ?, ?, ?, ?, " +
                                                   "?, ?, ?, " + // Datos de Seguridad
                                                   "?, ?, ?)}"; // IDs    ? 20 ?
        
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        // solo para StordProsedure

        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString( 1, e.getPersona().getNombre());
        cstmt.setString( 2, e.getPersona().getApellidoPaterno());
        cstmt.setString( 3, e.getPersona().getApellidoMaterno());
        cstmt.setString( 4, e.getPersona().getGenero());
        cstmt.setString( 5, e.getPersona().getFechaNacimiento());
        cstmt.setString( 6, e.getPersona().getCalle());
        cstmt.setString( 7, e.getPersona().getNumero());
        cstmt.setString( 8, e.getPersona().getColonia());
        cstmt.setString( 9, e.getPersona().getCp());
        cstmt.setString(10, e.getPersona().getCiudad());
        cstmt.setString(11, e.getPersona().getEstado());
        cstmt.setString(12, e.getPersona().getTelCasa());
        cstmt.setString(13, e.getPersona().getTelMovil());
        cstmt.setString(14, e.getPersona().getEmail());
        cstmt.setString(15, e.getUsuario().getNombre());
        cstmt.setString(16, e.getUsuario().getContrasenia());
        cstmt.setString(17, e.getUsuario().getRol());
        
        cstmt.setInt(18, e.getPersona().getIdPersona());
        cstmt.setInt(19, e.getUsuario().getIdUsuario());
        cstmt.setInt(20, e.getIdEmpleado());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        cstmt.close();
        connMySQL.close();        
    }
    
    public void delete(int id) throws Exception
    {
        String sql = "UPDATE empleado SET estatus= 0 WHERE idEmpleado ="+ id;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        
        Statement cstmt = conn.createStatement();
        cstmt.executeUpdate(sql);
        cstmt.close();
        connMySQL.close();       
    }
     
    
    public List<Empleado> getAll(String filtro) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // consultas para eso funciona el PreparedStattement 
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery(); // ejecutra instruccion de tipo SELECT 
        
        List<Empleado> empleados = new ArrayList<>();
        
        while (rs.next())
            empleados.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return empleados;
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

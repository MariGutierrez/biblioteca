package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.Cliente;
import com.prossofteam.oq.model.DetalleVentaPre;
import com.prossofteam.oq.model.Persona;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ximer
 */
public class ControllerDetalleVPL {
    
     public List<Cliente> buscar(String filtro) throws Exception {
        
        String sql = "SELECT * FROM cliente WHERE nombre like '%" + filtro + "%'"
                + "OR telmovil = '" + filtro + "'";
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Aquí guardaremos los resultados de la consulta:
        //ResultSet rs = null;
        ResultSet rs = pstmt.executeQuery();

        List<Cliente> clientes = new ArrayList<>();

        while (rs.next()) {
            clientes.add(fill(rs));
        }

       
        
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
    
   public boolean generarVentaLentes(DetalleVentaPre dvp) {
        boolean r = false;
//
//        //Creamos un objeto conexión con MySQL
//        ConexionMySQL connMySQL = new ConexionMySQL();
//        //objeto de tipo Connection que abre la conexión
//        Connection conn = connMySQL.open();
//        //objeto de Statement
//        Statement stmt = null;
//        //objeto de ResultSet
//        
//        ResultSet rs = null;
//        
//        /*NOTA: Los objetos quedan afuera del try catch, para poder utilizarlos en todo el método
//         */
//        try {
//            //el autocommit se prepara para no ejecutar en automatico las sentencias
//            //sino esperar a que termine la transaccion 
//            conn.setAutoCommit(false);
//            stmt = conn.createStatement();
//
//            //esta query se utiliza varias veces
//            String query = "SELECT LAST_INSERT_ID()";
//
//            //Se genera la venta
//            String query0 = "INSERT INTO venta(idEmpleado, clave) VALUES ("
//                    + dvp.getVenta().getEmpleado().getIdEmpleado() + ","
//                    + dvp.getVenta().getClave() + ");";
//            stmt.execute(query0);
//            //Se obtiene el id genrado con la insercion de venta
//            rs = stmt.executeQuery(query);
//            if (rs.next()) {
//                dvp.getVenta().setIdVenta(rs.getInt(1));
//            }
//
//            //Se insertan varios presupuestos, por lo tanto se Cicla
//            for (int i = 0; i < dvp.getVentaPresupuesto().size(); i++) {
//
//                //Se insertan los presupuestos
//                String query1 = "INSERT INTO presupuesto(idExamenVista, clave)"
//                        + "VALUES (" + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getExamenVista().getIdExamenVista() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getClave()+ ");";
//                stmt.execute(query1);
//                rs = stmt.executeQuery(query);
//                //Obtenemos el id de presupuesto y lo guardamos
//                if (rs.next()) {
//                    dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().setIdPresupuesto(rs.getInt(1));
//                }
//
//                //Se insertan los presupuestos de lentes
//                String query2 = "INSERT INTO presupuesto_Lentes(idPresupuesto, alturaOblea, idTipoMica, idMaterial, idArmazon)"
//                        + "VALUES (" + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getIdPresupuesto() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getLenteContacto().getAlturaOblea()+ ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getIdTipoMica().getIdTipoMica() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getIdMaterial().getIdMaterial() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getIdArmazon().getIdArmazon() + ");";
//                stmt.execute(query2);
//                //Obtenermos el id de presupuesto_Lentes  y se guarda en su objeto
//                rs = stmt.executeQuery(query);                
//                if (rs.next()) {
//                    dvp.getVentaPresupuesto().get(i).getPresupuestoLC().setIdPresupuestoLentes(rs.getInt(1));
//                }
//                
//                //Se almacenana los tratamientos que tiene ese lente presupuestado
//                //Va en un ciclo por que se tiene la posibilidad de elegir varios tratamientos
//                for (int j = 0; j < dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getTratamientos().size(); j++) {
//                    String query3 = "INSERT INTO presupuesto_lentes_tratamientos VALUES("
//                            + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getIdPresupuestoLentes() + ","
//                            + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getTratamientos().get(j).getIdTratamiento()+ ");";
//                    stmt.execute(query3);
//                    
//                }
//                //Query numero 4 para almacenar la relacion de venta_presupuesto
//                String query4 = "INSERT INTO venta_presupuesto VALUES("
//                        + dvp.getVenta().getIdVenta() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getIdPresupuesto().getIdPresupuesto() + ","
//                        + dvp.getVentaPresupuesto().get(i).getCantidad() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPrecioUnitario() + ","
//                        + dvp.getVentaPresupuesto().get(i).getDescuento() + ");";
//                stmt.execute(query4);
//            }
//            
//            //Ya con todas las sentencias ejecutadas, se envia la conformación de ejecutar la transaccion
//            conn.commit();
//            //Se cierran los objetos de BD
//            conn.setAutoCommit(true);
//            rs.close();
//            stmt.close();
//            r = true;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ControllerVentaLC.class.getName()).log(Level.SEVERE, null, ex);
//            try {
//                //En caso de error se indica un rollback a la transaccion. 
//                conn.rollback();
//                conn.setAutoCommit(true);
//                r = false;
//
//            } catch (SQLException ex1) {
//                Logger.getLogger(ControllerVentaLC.class.getName()).log(Level.SEVERE, null, ex);
//                r = false;
//            }
//        }
        return r;
    }
    
}

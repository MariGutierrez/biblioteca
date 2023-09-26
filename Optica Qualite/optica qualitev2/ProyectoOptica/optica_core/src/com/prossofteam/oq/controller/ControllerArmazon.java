package com.prossofteam.oq.controller;

import com.prossofteam.oq.bd.ConexionMySQL;
import com.prossofteam.oq.model.Armazon;
import com.prossofteam.oq.model.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ControllerArmazon {

    public int insert(Armazon a) throws Exception {

        //Definimos la consulta SQL que invoca al StoredProcedure
        String sql = "{call insertarArmazon(?, ?, ?, ?, ?, "
                + // Datos del producto
                "?, ?, ?, ?, ?, "
                + // Datos del amrazon
                "?, ?, ? )}";       // Valores de retorno

        // Definir el bloque de variables para guardar los id's y valores que se generan 
        int idProductoGenerado = -1;  // aun no cuenta con un valor por eso se le asigan un valor fuera de rango
        int idArmazonGenerado = -1;   // aun no cuenta con un valor por eso se le asigan un valor fuera de rango
        String codigoBarrasGenerado = "";

        // Abrir la conexion con la base de datos
        // Este objeto nos permitira conectar con la BD
        ConexionMySQL connMySQL = new ConexionMySQL();

        // Creacion de una instania de tipo ConexionMySQL para poder realizar la conexion
        // Se abre la conexion con la BD
        Connection conn = connMySQL.open();

        // Invocar al StoredProcedure con un objeto de tipo CallableStatement
        CallableStatement cstmt = conn.prepareCall(sql);

        // Llenar los parametros del CallableStatement
        // Es establecen los valores en el orden en le cual se encuentran en el StoredProcedure
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getMarca());
        cstmt.setDouble(3, a.getProducto().getPrecioCompra());
        cstmt.setDouble(4, a.getProducto().getPrecioVenta());
        cstmt.setInt(5, a.getProducto().getExistencias());
        cstmt.setString(6, a.getModelo());
        cstmt.setString(7, a.getColor());
        cstmt.setString(8, a.getDimesiones());
        cstmt.setString(9, a.getDescripcion());
        cstmt.setString(10, a.getFotografia());

        // Se pasa un valor a cada uno de culaquier tipo que este en la BD
        // Se llama al objeto y se le pide el valor necesitado
        // 10 de entrada y 3 de salida
        cstmt.registerOutParameter(11, Types.INTEGER);
        cstmt.registerOutParameter(12, Types.INTEGER);
        cstmt.registerOutParameter(13, Types.VARCHAR);

        // Ejecutra la consulta 
        // Ejecutar el StoredProcedure
        cstmt.executeUpdate();

        // Atraves de CallableStatement invocar el executeUpdate, se guardan los datos en base de datos
        // Se recuperan los ID's generados 
        idProductoGenerado = cstmt.getInt(11);
        idArmazonGenerado = cstmt.getInt(12);
        codigoBarrasGenerado = cstmt.getString(13);

        a.getProducto().setIdProducto(idProductoGenerado);
        a.setIdArmazon(idArmazonGenerado);
        a.getProducto().setCodigoBarras(codigoBarrasGenerado);

        // Cerrar la conexion 
        cstmt.close();
        connMySQL.close();

        // Devolver el Id de Armazon generado
        return idArmazonGenerado;
    }

    public void update(Armazon a) throws SQLException {

        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql = "{call actualizarArmazon( ?, ?, ?, ?, ?, "
                + // Datos Producto
                " ?, ?, ?, ?, ?, "
                + // Datos Armazon
                " ?, ? )}";          // IDs

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        // solo para StordProsedure

        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getMarca());
        cstmt.setDouble(3, a.getProducto().getPrecioCompra());
        cstmt.setDouble(4, a.getProducto().getPrecioVenta());
        cstmt.setInt(5, a.getProducto().getExistencias());
        cstmt.setString(6, a.getModelo());
        cstmt.setString(7, a.getColor());
        cstmt.setString(8, a.getDimesiones());
        cstmt.setString(9, a.getDescripcion());
        cstmt.setString(10, a.getFotografia());

        cstmt.setInt(11, a.getProducto().getIdProducto());
        cstmt.setInt(12, a.getIdArmazon());

        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();

        cstmt.close();
        connMySQL.close();

    }

    public void delete(int id) throws Exception {

        String sql = "UPDATE armazon SET estatus = 0 WHERE idArmazon=" + id;

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareCall(sql);

        pstmt.executeUpdate();

        // ejecutra instruccion de tipo UPDATE 
        pstmt.close();
        connMySQL.close();

    }

    public List<Armazon> getAll() throws Exception {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_armazones";

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // consultas para eso funciona el PreparedStattement 
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery(); // ejecutra instruccion de tipo SELECT 

        List<Armazon> armazones = new ArrayList<>();

        while (rs.next()) {
            armazones.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return armazones;
    }

    private Armazon fill(ResultSet rs) throws Exception {
        Armazon a = new Armazon();
        Producto p = new Producto();

        a.setIdArmazon(rs.getInt("idArmazon"));
        a.setModelo(rs.getString("modelo"));
        a.setColor(rs.getString("color"));
        a.setDimesiones(rs.getString("dimensiones"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setFotografia(rs.getString("fotografia"));

        p.setIdProducto(rs.getInt("idProducto"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setExistencias(rs.getInt("existencias"));
        p.setMarca(rs.getString("marca"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecioCompra(rs.getDouble("precioCompra"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));
        p.setEstatus(rs.getInt("estatus"));

        a.setProducto(p);

        return a;
    }
}

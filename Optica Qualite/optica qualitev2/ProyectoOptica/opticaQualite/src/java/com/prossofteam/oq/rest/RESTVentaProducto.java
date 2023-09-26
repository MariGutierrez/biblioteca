
package com.prossofteam.oq.rest;


import com.google.gson.Gson;
import com.prossofteam.oq.controller.ControllerArmazon;
import com.prossofteam.oq.controller.ControllerVentaProducto;
import com.prossofteam.oq.model.DetalleVentaProducto;
import com.prossofteam.oq.model.Producto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


@Path("ventapro")
public class RESTVentaProducto {
    @POST
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON) //Los parametros se anotan como QueryParam ya que es un Get
    public Response buscar(@FormParam("filtro") @DefaultValue("") String filtro) throws SQLException {
        
        String out = null;
        ControllerVentaProducto cp = null;
        List<Producto> productos = null;

            try {
                cp = new ControllerVentaProducto();
                productos = cp.getAll(filtro);
                out = new Gson().toJson(productos);
            } catch (Exception e) {
                e.printStackTrace();
                out = "{\"exception\":\"Error interno del servidor.\"}";
            }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON) //Los parametros se anotan como QueryParam ya que es un Get
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) throws SQLException {
        String out = null;
        ControllerArmazon ca = new ControllerArmazon();
        ControllerVentaProducto cp = new ControllerVentaProducto();
        List<Producto> productos = null;

            try {
                productos = cp.getAll(filtro);
                out = new Gson().toJson(productos);
            } catch (Exception e) {
                e.printStackTrace();
                out = "{\"exception\":\"Error interno del servidor.\"}";
            }
       
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    //venta de productos
    @POST
    @Path("TransaccionVenta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearVentaP(@FormParam("datosVP") @DefaultValue("") String datosVP)
    {
        String out ="";
        DetalleVentaProducto detalleVP = null;
        Gson gson = new Gson();
        detalleVP = gson.fromJson(datosVP, DetalleVentaProducto.class);
        ControllerVentaProducto ctrVenta = new ControllerVentaProducto();
        boolean r = ctrVenta.transaccionarVenta(detalleVP);
        if(r)
        {
            out="""
                    {"result":"Exito"}
                """;
        }
        else
        {
            out="""
                    {"error":"Error"}
                """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}

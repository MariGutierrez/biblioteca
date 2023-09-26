
package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.prossofteam.oq.controller.ControllerArmazon;
import com.prossofteam.oq.controller.ControllerVentaLC;
import com.prossofteam.oq.controller.ControllerVentaProducto;
import com.prossofteam.oq.model.DetalleVentaPre;
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

@Path("ventaLC")
public class RESTVentaLC {
    
     //venta de productos
    @POST
    @Path("TransaccionVenta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearVentaP(@FormParam("datosVP") @DefaultValue("") String datosVP)
    {
        System.out.println(datosVP);
        String out ="";
        DetalleVentaPre detalleVP = null;
        Gson gson = new Gson();
        detalleVP = gson.fromJson(datosVP, DetalleVentaPre.class);
        ControllerVentaLC ctrVenta = new ControllerVentaLC();
        boolean r = ctrVenta.generarVentaLC(detalleVP);
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

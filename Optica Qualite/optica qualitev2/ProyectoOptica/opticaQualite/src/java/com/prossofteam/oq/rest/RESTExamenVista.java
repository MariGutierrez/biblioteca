package com.prossofteam.oq.rest;


import com.google.gson.Gson;
import com.prossofteam.oq.controller.ControllerExamenVista;
import com.prossofteam.oq.model.ExamenVista;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("examenV")
public class RESTExamenVista extends Application {
    
    @POST
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEV(@FormParam("idCliente") @DefaultValue("") String idCliente) {
        String out = null;
        ControllerExamenVista cx = null;
        List<ExamenVista> exList = null;

        try {
            cx = new ControllerExamenVista();
            exList = cx.obtenerEV(idCliente);
            out = new Gson().toJson(exList);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exeption\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
    
        
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        ControllerExamenVista cx = null;
        List<ExamenVista> exList = null;

        try {
            cx = new ControllerExamenVista();
            exList = cx.getAll(filtro);
            out = new Gson().toJson(exList);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exeption\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
   

}

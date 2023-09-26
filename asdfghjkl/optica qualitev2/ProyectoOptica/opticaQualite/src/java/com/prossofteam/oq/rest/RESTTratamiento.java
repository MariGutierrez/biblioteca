package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.prossofteam.oq.controller.ControllerTratamiento;
import com.prossofteam.oq.model.Tratamiento;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author ximer
 */
    @Path("tratamiento")
public class RESTTratamiento {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        ControllerTratamiento ct = null;
        List<Tratamiento> tratamientos = null;

        try {
            ct = new ControllerTratamiento();
            tratamientos = ct.obtenerTratamiento(filtro);
            out = new Gson().toJson(tratamientos);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exeption\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
}
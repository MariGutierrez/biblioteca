package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.prossofteam.oq.controller.ControllerTipoMica;
import com.prossofteam.oq.model.TipoMica;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("tipoMica")   
public class RESTTipoMica {
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public Response getAll() {
        String out = null;
        ControllerTipoMica ctm = null;
        List<TipoMica> tiposMica = null;

        try {
            ctm = new ControllerTipoMica();
            tiposMica = ctm.getAll();
            out = new Gson().toJson(tiposMica);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exeption\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.prossofteam.oq.controller.ControllerCliente;
import com.prossofteam.oq.controller.ControllerLentesContacto;
import com.prossofteam.oq.model.Cliente;
import com.prossofteam.oq.model.LenteContacto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
@Path("lentesContacto")
public class RESTLentesContacto {
    @POST
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerLentesContacto cl = null;
        List<LenteContacto> lenteContactos = null;

        try {
            cl = new ControllerLentesContacto();
            lenteContactos = cl.getAll(filtro);
            out = new Gson().toJson(lenteContactos);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
}

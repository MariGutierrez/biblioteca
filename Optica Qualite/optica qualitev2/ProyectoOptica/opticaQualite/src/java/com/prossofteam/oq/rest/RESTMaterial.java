/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prossofteam.oq.rest;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.prossofteam.oq.controller.ControllerMaterial;
import com.prossofteam.oq.model.Material;
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


@Path("material")   
public class RESTMaterial {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public Response getAll() {
        String out = null;
        ControllerMaterial cm = null;
        List<Material> materiales = null;

        try {
            cm = new ControllerMaterial();
            materiales = cm.getAll();
            out = new Gson().toJson(materiales);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exeption\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}

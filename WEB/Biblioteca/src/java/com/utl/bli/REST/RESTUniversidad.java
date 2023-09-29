package com.utl.bli.REST;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.utl.bli.controller.ControllerUniversidad;
import com.utl.bli.model.Universidad;
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

@Path("Uni")
public class RESTUniversidad {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerUniversidad cc = null;
        List<Universidad> universidades = null;

        try {
            cc = new ControllerUniversidad();
            universidades = cc.getAll(filtro);
            out = new Gson().toJson(universidades);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerUniversidad cc = null;
        List<Universidad> universidades = null;

        try {
            cc = new ControllerUniversidad();
            universidades = cc.buscar(filtro);
            out = new Gson().toJson(universidades);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("save")
    @POST  // por que solo se ingresan los datos 
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosUni") @DefaultValue("") String datosUni) {

        String out = null;
        Gson gson = new Gson();
        Universidad uni = null;
        ControllerUniversidad cc = new ControllerUniversidad();

        try {
            uni = gson.fromJson(datosUni, Universidad.class);
            int id = uni.getId_universidad();
            System.out.println(id);
            if (id<=0) {

                cc.insert(uni);

            } else {
                cc.update(uni);
            }
            out = gson.toJson(uni);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"error"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("datosUni") @DefaultValue("") String datosUni) {

        String out = null;
        Gson gson = new Gson();
        Universidad uni = null;
        ControllerUniversidad cliente = new ControllerUniversidad();

        try {

            uni = gson.fromJson(datosUni, Universidad.class);

            cliente.delete(uni.getId_universidad());

            out = gson.toJson(uni);

        } catch (JsonParseException jpe) {

            jpe.printStackTrace();
            out = """
                  {"exception":"Formato JSON de Datos incorrecto."}
                  """;
        } catch (Exception e) {

            e.printStackTrace();
            out = """
                  {"exception":"%s"}
                  """;
            out = String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }
}

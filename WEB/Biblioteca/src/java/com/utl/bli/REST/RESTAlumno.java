package com.utl.bli.REST;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.utl.bli.controller.ControllerAlumno;
import com.utl.bli.model.Alumno;
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

/**
 *
 * @author ximer
 */
@Path("alumno")
public class RESTAlumno {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerAlumno ca = null;
        List<Alumno> alumnos = null;

        try {
            ca = new ControllerAlumno();
            alumnos = ca.getAll(filtro);
            out = new Gson().toJson(alumnos);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosAlumno") @DefaultValue("") String datosAlumno) {

        String out = null;
        Gson gson = new Gson();
        Alumno al = null;
        ControllerAlumno ca = new ControllerAlumno();

        try {
            al = gson.fromJson(datosAlumno, Alumno.class);
            if (al.getId_alumno() == 0) {
                ca.insert(al);

            } else {

                ca.update(al);
            }
            out = gson.toJson(al);

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
    public Response delete(@FormParam("datosAlumno") @DefaultValue("") String datosAlumno) {

        String out = null;
        Gson gson = new Gson();
        Alumno al = null;
        ControllerAlumno ca = new ControllerAlumno();

        try {
            
            al = gson.fromJson(datosAlumno, Alumno.class);

            ca.delete(al.getId_alumno());
            
            out = gson.toJson(al);

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
    
    @GET
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerAlumno ca = null;
        List<Alumno> alumnos = null;

        try {
            ca = new ControllerAlumno();
            alumnos = ca.buscar(filtro);
            out = new Gson().toJson(alumnos);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

}
